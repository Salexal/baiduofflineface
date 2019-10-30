package com.jni.face;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import fi.iki.elonen.NanoHTTPD;

public class HttpFileServer extends NanoHTTPD {
 
	private static String facecodeRoot = "d:\\app\\face\\facecode\\";
	private static String tagfaceRoot = "d:\\app\\face\\tagface\\";
	private static String compareDBfaceRoot = "d:\\app\\face\\compareDBface\\";
	private static String comparefaceRoot = "d:\\app\\face\\compareface\\";
	
	static Face face;
    public HttpFileServer() throws IOException {
        super(9079);

    }
    public static void main(String[] args) {
        try {
        	face = new Face();
    		face.sdkInit(false);
    		face.loadDbFace();
    		new File(facecodeRoot).mkdirs();
    		new File(tagfaceRoot).mkdirs();
    		new File(compareDBfaceRoot).mkdirs();
    		new File(comparefaceRoot).mkdirs();
    		HttpFileServer f = new HttpFileServer();
            f.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            System.out.println("\nRunning! Point your browsers to http://localhost:9079/ \n");
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }
    

	/**
	 * 发送get请求,  下载图片
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static void httpGetImg(CloseableHttpClient client,String imgUrl,String savePath) {
		
		 
		// 发送get请求
		HttpGet request = new HttpGet(imgUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(50000).setConnectTimeout(50000).build();
		
		//设置请求头
		request.setHeader( "User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1" );
		
		request.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = client.execute(request);
			
			 if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) { 
				  HttpEntity entity = response.getEntity();  
				  
				  InputStream in = entity.getContent();  
				  
				  FileUtils.copyInputStreamToFile(in, new File(savePath));
				  System.out.println("下载图片成功:"+imgUrl);
				 
			 }
			 
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			request.releaseConnection();
			
		}
	}
 
	public boolean isSameFace(Map<String, String> parms) {
		String imgurl = parms.get("imgurl");
		String imgurl2 = parms.get("imgurl2");
		String scoreparam = parms.get("score");
		String file1 = comparefaceRoot + UUID.randomUUID().toString() + ".jpg";
		String file2 = comparefaceRoot + UUID.randomUUID().toString() + ".jpg";

		CloseableHttpClient client = null;

		try {
			client = HttpClients.createDefault();
			httpGetImg(client, imgurl, file1);
			httpGetImg(client, imgurl2, file2);
			rotate(face,file1);
			rotate(face,file2);
			String res = face.match(file1, file2);
			JSONObject json = JSON.parseObject(res);
			if (json != null && "success".equals(json.get("msg").toString())) {
				String score = json.getJSONObject("data").getString("score");
				if (scoreparam == null || scoreparam.equals("")) {
					scoreparam = "50";
				}
				if (Double.parseDouble(score) > Double.parseDouble(scoreparam)) {
					return true;
				}
			}
			System.out.println("path match is:" + res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public String getFaceCode(Map<String, String> parms) {
		
		String md5 = parms.get("md5");
		String imgurl = parms.get("imgurl");
		CloseableHttpClient client = null;
		String file1 = facecodeRoot + md5 + ".jpg";
		boolean isdownload = false;
		try {
			if(!new File(file1).exists()) {
				client = HttpClients.createDefault();
				httpGetImg(client, imgurl, file1);
				isdownload = true;
			}
			face.clearTrackedFaces();
			Mat mat = Imgcodecs.imread(file1);
			face.setNotFaceThr(0.5f);
			face.setMinFaceSize(30);
			face.setTrackByDetectionInterval(1000);
			TrackFaceInfo[] ti2 = face.trackByMat(mat.getNativeObjAddr(), 10);
			String json = JSON.toJSONString(ti2);
			if(!isdownload) {
				return json;
			}
			int index = 1;
			for (TrackFaceInfo tf : ti2) {
				byte[] feature = face.getFaceFeatureByFace(mat.getNativeObjAddr(), tf);
				String userId = md5 + "_" + index + "";
				String groupId = "public";
				String userInfo = md5;
				face.userAddByFeature(userId, groupId, feature, feature.length, userInfo);
				  Mat mat2 = Imgcodecs.imread(file1);
				  Imgproc.rectangle(mat2, new Point(tf.centerX-0.5*tf.width, tf.centerY-0.5*tf.width), new Point(tf.centerX+0.5*tf.width, tf.centerY+0.5*tf.width),
		                  new Scalar(0, 255, 0));
				  Imgcodecs.imwrite(tagfaceRoot + md5+"_tag_"+index + ".jpg", mat2);
				index++;
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	public String compareDB(Map<String, String> parms) {

		String imgurl = parms.get("imgurl");
		String md5 = parms.get("md5");

		CloseableHttpClient client = null;
		String file1 = compareDBfaceRoot + md5 + ".jpg";
		try {
			if (!new File(file1).exists()) {
				client = HttpClients.createDefault();
				httpGetImg(client, imgurl, file1);
			}
			face.clearTrackedFaces();
			face.loadDbFace();
			String a = face.identifyDB(file1, 10000);
			System.out.println("compareDB:" + a);
			JSONObject jo = JSON.parseObject(a);
			List list = new ArrayList();
			if (jo != null && "success".equals(jo.getString("msg"))) {
				JSONArray array = jo.getJSONObject("data").getJSONArray("result");
				for (Object obj : array) {
					Double d = ((JSONObject) obj).getDoubleValue("score");
					if (d > 50) {
						list.add(obj);
					}
				}
			}
			String json = JSON.toJSONString(list);
			System.out.println(" return compareDB:" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(" return compareDB: null");
		return "";
	}
	
	public void rotate(Face face,String filepath) {
	      face.clearTrackedFaces();
	      TrackFaceInfo ti = face.trackMax(filepath);
	      TrackFaceInfo timax = ti;
	      	
	        Mat src=Imgcodecs.imread(filepath);
	        try{
	            Mat dst90=src.clone();
	            Mat dst180=src.clone();
	            Mat dst270=src.clone();
	            Mat matmax = src;
	            //复制矩阵进入dst
	            Point center =new Point(src.width()/2.0,src.height()/2.0);
	            Mat affineTrans90=Imgproc.getRotationMatrix2D(center, 90, 1.0);
	            Imgproc.warpAffine(src, dst90, affineTrans90, dst90.size(),Imgproc.INTER_NEAREST);
	            face.clearTrackedFaces();
	            TrackFaceInfo ti90 = face.trackMaxByMat(dst90.getNativeObjAddr());
	            if(ti90!=null) {
	            	System.out.println("ti90:"+ti90.score);
	            }
	            
	            Mat affineTrans180=Imgproc.getRotationMatrix2D(center,180, 1.0);
	            Imgproc.warpAffine(src, dst180, affineTrans180, dst180.size(),Imgproc.INTER_NEAREST);
	            face.clearTrackedFaces();
	            TrackFaceInfo ti180 = face.trackMaxByMat(dst180.getNativeObjAddr());
	            if(ti180!=null) {
	            	System.out.println("ti180:"+ti180.score);
	            }
	            
	            Mat affineTrans270=Imgproc.getRotationMatrix2D(center,270, 1.0);
	            Imgproc.warpAffine(src, dst270, affineTrans270, dst270.size(),Imgproc.INTER_NEAREST);
	            face.clearTrackedFaces();
	            TrackFaceInfo ti270 = face.trackMaxByMat(dst270.getNativeObjAddr());
	            if(ti270!=null) {
	            	System.out.println("ti270:"+ti270.score);
	            }
	            
	            
	            if(timax==null && ti90!=null) {
	            	matmax = dst90;
	            	timax = ti90;
	            }
	            if(timax==null && ti180!=null) {
	            	matmax = dst180;
	            	timax = ti180;
	            }
	            if(timax==null && ti270!=null) {
	            	matmax = dst270;
	            	timax = ti270;
	            }
	            if(timax==null) {
	            	return ;
	            }
	            if(ti90!=null && timax.score< ti90.score ) {
	            	matmax = dst90;
	            }
	            if(ti180!=null &&timax.score< ti180.score ) {
	            	matmax = dst180;
	            }
	            if(ti270!=null &&timax.score< ti270.score ) {
	            	matmax = dst270;
	            }
	            Imgcodecs.imwrite(filepath,matmax);
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	}
     

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        
        //session.getUri().contains("/fileview/?imgid")
        if (session.getUri().contains("isSameFace")) {
        	boolean b = isSameFace(parms) ;
        	return newFixedLengthResponse(b+"");
        }else if (session.getUri().contains("getFaceCode")) {
        	String json = getFaceCode(parms) ;
        	return newFixedLengthResponse(json);
        }else if (session.getUri().contains("compare")) {
        	String json = compareDB(parms) ;
        	return newFixedLengthResponse(json);
        }else if (session.getUri().contains("viewImage")) {
            String filename = parms.get("filename");
            InputStream data = null;
            try {
                data = FileUtils.openInputStream(new File(tagfaceRoot+filename));
                return newChunkedResponse(Response.Status.OK,"image/jpg", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
           
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
}

