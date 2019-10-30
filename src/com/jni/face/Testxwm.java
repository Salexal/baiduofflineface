package com.jni.face;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Testxwm {
public static void main(String[] args) {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		Mat m = Mat.eye(3, 3,CvType.CV_8UC1);
//		System.out.println(m.dump());
		Face face = new Face();
		face.sdkInit(false);
//        String res = face.match("D:\\py_workspace\\facerecognize\\pysrc\\errorimage\\8aba6db6-ee35-11e9-be80-080027e89906-1.jpg",
//        		"D:\\py_workspace\\facerecognize\\pysrc\\errorimage\\303733970904809472.jpg");
//        System.out.println("path match is:" + res);
//        face.clearTrackedFaces();
        
//      face.setNotFaceThr(0.5f);
//      face.setMinFaceSize(30);
//      face.setTrackByDetectionInterval(1000);
//        TrackFaceInfo[] ti = face.track("d:\\timg.jpg",10);
//        System.out.println("size:"+ti.length);
//        for(TrackFaceInfo tf:ti) {
//        	System.out.println(JSON.toJSONString(tf));
//        }
//       
//	      face.setNotFaceThr(0.5f);
//	      face.setMinFaceSize(30);
//	      face.setTrackByDetectionInterval(1000);
//		String attr = face.faceAttr("d:\\timg.jpg");
//        System.out.println(attr);
//
//        face.clearTrackedFaces();
//        byte[] v = face.getFaceFeature("d:\\timg.jpg");
//        System.out.println(v.length);
        // 人脸对比（传入opencv视频帧）
//        Mat mat1 = Imgcodecs.imread("E:/me1.jpg");
//        Mat mat2 = Imgcodecs.imread("E:/me2.jpg");
//
//        long matAddr1 = mat1.getNativeObjAddr();
//        long matAddr2 = mat2.getNativeObjAddr();
//        res = face.matchByMat(matAddr1, matAddr2);
//        System.out.println("mat match is:" + res);
        
     // 获取特征值（传入opencv视频帧和需要获取特征值的人脸信息，适应于多人脸）
//        public static native byte[] getFaceFeatureByFace(long mat, TrackFaceInfo info);
     // 人脸注册(传特征值）
//        public static native String userAddByFeature(String userId, String groupId, byte[] feas, int feaLen,
//                String userInfo);
        
      face.clearTrackedFaces();
      Mat mat = Imgcodecs.imread("d:\\duoren.jpg");
      face.setNotFaceThr(0.5f);
      face.setMinFaceSize(30);
      face.setTrackByDetectionInterval(1000);
      TrackFaceInfo[] ti2 = face.trackByMat(mat.getNativeObjAddr(),10);
      int index = 1;
	  for(TrackFaceInfo tf:ti2) {
		  byte[] feature = face.getFaceFeatureByFace(mat.getNativeObjAddr(), tf);
		  String userId = "face_"+index+"";
		  String groupId = "public";
		  String userInfo = "";
		  face.userAddByFeature(userId, groupId, feature, feature.length,userInfo);
//		  Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
//                  new Scalar(0, 255, 0));
		  Mat mat2 = Imgcodecs.imread("d:\\duoren.jpg");
		  Imgproc.rectangle(mat2, new Point(tf.centerX-0.5*tf.width, tf.centerY-0.5*tf.width), new Point(tf.centerX+0.5*tf.width, tf.centerY+0.5*tf.width),
                  new Scalar(0, 255, 0));
		  Imgcodecs.imwrite("d:\\duoren_"+index+".jpg", mat2);
		  index++;
	  }
	  
	    // 1:N人脸识别(和整个库比较,需要提前调loadDbFace（）)
//	    public static native String identifyDB(String image, int userTopNum);
	  face.clearTrackedFaces();
	  face.loadDbFace();
	  String a = face.identifyDB("d:\\me.jpg",10000);
	  System.out.println(a);
	  JSONObject jo = JSON.parseObject(a);
	  List list = new ArrayList();
	  if(jo!=null && "success".equals(jo.getString("msg"))) {
		  JSONArray array = jo.getJSONObject("data").getJSONArray("result");
		  for(Object obj:array) {
			 Double d =  ((JSONObject)obj).getDoubleValue("score");
			 if(d>50) {
				 list.add(obj);
			 }
		  }
	  }
	  System.out.println(list);
	}
	

}
