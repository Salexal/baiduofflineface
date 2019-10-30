package com.jni.face;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestVideo {
	public static void main(String[] args) {
		Face face = new Face();
		face.sdkInit(false);
		VideoCapture cap2 = new VideoCapture();
		cap2.open("C:\\Users\\xwm\\Desktop\\test.mp4");
		if(!cap2.isOpened()) { // 判断视频文件是否存在
			System.out.println("视频文件路径错误！");
			return;
		}
		int width1 = (int) cap2.get(3);
        int height1 = (int) cap2.get(4);
        ImageGUI gui1 = new ImageGUI();
        gui1.createWin("frame1", new Dimension(width1, height1));
        Mat frame1 = new Mat();
 
        int max_track = 100000; // 假设的结束条件
        int index = 0;
        boolean stop = false;
      face.setNotFaceThr(0.5f);
      face.setMinFaceSize(30);
      face.setTrackByDetectionInterval(300);
        while (!stop) {
            boolean have1 = cap2.read(frame1);
            Core.flip(frame1, frame1, 1); // Win上摄像头
            if (!have1) {
                break;
            }

            if (!frame1.empty()) {
                RotatedRect box;
                System.out.println("get frame ---");
                long matAddr1 = frame1.getNativeObjAddr();
                TrackFaceInfo[] infos = face.trackByMat(matAddr1, 10);

                if (infos != null) {
                	for(TrackFaceInfo info:infos) {
                        box = ShowVideo.boundingBox(info.landmarks, info.landmarks.length);
                        ShowVideo.drawRotatedBox(frame1, box, new Scalar(0, 255, 0));
                	}
                    
                }

                gui1.imshow(ShowVideo.conver2Image(frame1));
                gui1.repaint();
                index++;
                if (index > max_track) {
                    stop = true;
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	

}
