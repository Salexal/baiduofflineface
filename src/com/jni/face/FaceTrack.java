package com.jni.face;

import java.awt.Dimension;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import com.jni.struct.TrackFaceInfo;

/**
 * 
 * @ 人脸跟踪示例demo类
 *
 */
public class FaceTrack {
    // usb摄像头视频人脸跟踪示例
    public int usbVideoTrack() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 打开摄像头或者视频文件
        // device为0默认打开笔记本电脑自带摄像头，若为0打不开外置usb摄像头
        // 请把device修改为1或2再重试，1，或2为usb插上电脑后，电脑认可的usb设备id
        VideoCapture capture = new VideoCapture();
        capture.open(0);
        if (!capture.isOpened()) {
            System.out.println("could not open camera...");
            return -1;
        }
        // type 0： 表示rgb 人脸检测 1：表示nir人脸检测
        int type = 0;
        int frameWidth = (int) capture.get(3);
        int frameHeight = (int) capture.get(4);
        ImageGUI gui = new ImageGUI();
        gui.createWin("video", new Dimension(frameWidth, frameHeight));
        Mat frame = new Mat();
        while (true) {
            boolean have = capture.read(frame);
           // Core.flip(frame, frame, 1); // Win上摄像头
            if (!have) {
                continue;
            }
            if (!frame.empty()) {
                long matAddr = frame.getNativeObjAddr();
               
                TrackFaceInfo[] infos = Face.track(matAddr, type);
                // 检测到人脸
                if (infos != null && infos.length > 0) {  
                    // 画人脸框
                    FaceDraw.drawRects(frame, infos);  
                    // 画人脸关键点
                    FaceDraw.drawLandmark(frame, infos);
                }
                gui.imshow(ShowVideo.conver2Image(frame));               
                gui.repaint();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
   
    // 人脸跟踪示例（图片跟踪)
    public int imageTrack() {
        Mat mat = Imgcodecs.imread("images/2.jpg");
        long matAddr = mat.getNativeObjAddr();
        // type 0： 表示rgb 人脸检测 1：表示nir人脸检测
        int type = 0;
        TrackFaceInfo[] trackList = Face.track(matAddr, type);
        if (trackList == null || trackList.length <= 0) {
            System.out.println("detect no face");
            return -1;
        }
        for (int i = 0; i < trackList.length; i++) {
            // 第几个人脸
            System.out.println("face index is:" + i);
            // 人脸框宽度
            System.out.println("face width is:" + trackList[i].box.width);
            // 人脸框高度
            System.out.println("face height is:" + trackList[i].box.height);
            // 人脸角度
            System.out.println("face angle is:" + trackList[i].box.angle);
            // 人脸框中心x坐标
            System.out.println("face center x is:" + trackList[i].box.centerx);
            // 人脸框中心y坐标
            System.out.println("face center y is:" + trackList[i].box.centery);
            // 人脸置信度
            System.out.println("face score is:" + trackList[i].box.score);
        }
        
        return 0;
    }
}
