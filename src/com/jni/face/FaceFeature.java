package com.jni.face;

import java.io.IOException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import com.jni.struct.FaceBox;
import com.jni.struct.Feature;
import com.jni.struct.FeatureInfo;

/**
 * 
 * @ 特征值提取
 *
 */
public class FaceFeature {
    // 特征值提取示例 (特征值通常为512个byte)
    public void imageFaceFeature() {
        Mat mat = Imgcodecs.imread("images/1.jpg");
        long matAddr = mat.getNativeObjAddr();
        // type 0： 表示rgb 人脸检测 1：表示nir人脸检测
        int type = 0;
        FeatureInfo[] feaList = Face.faceFeature(matAddr, type);

        if (feaList == null || feaList.length <= 0) {
            System.out.println("get feature fail");
            return;
        }
        // save to binary file
        int size = feaList[0].feature.size;
        System.out.println("size is:" + size);
        
        for (int i = 0; i < feaList.length; i++) {
            Feature fea = feaList[i].feature;
            System.out.println("fea size is:" + fea.size);
            for (int j = 0; j < fea.size; j++) {
                System.out.println("fea is:" + fea.data[j]);
            }
            FaceBox box = feaList[i].box;
            // 绘制人脸框
            FaceDraw.drawRects(mat, box);
        }
        // 取完特征值后可把绘制人脸框的图片保存本地
        Imgcodecs.imwrite("feature.jpg", mat);
    }

    // rgb+depth深度特征值提取示例 (rgbd的特征值大小为1024个byte）
    public void rgbdFaceFeature() {
        Mat rgbMat = Imgcodecs.imread("images/rgb.png");
        long rgbAddr = rgbMat.getNativeObjAddr();

        try {
            byte[] depthBufs = ImageBuf.toByteArray("images/depth.png");
            int bufsLen = depthBufs.length;
            System.out.println("bufsLen is:" + bufsLen);
            FeatureInfo[] feaList = Face.rgbdFeature(rgbAddr, depthBufs, bufsLen);
            if (feaList == null || feaList.length <= 0) {
                System.out.println("get feature fail");
                return;
            }
            System.out.println("feaList is:" + feaList.length);
            for (int i = 0; i < feaList.length; i++) {
                Feature fea = feaList[i].feature;
                System.out.println("fea size is:" + fea.size);
                for (int j = 0; j < fea.size; j++) {
                    System.out.println("fea is:" + fea.data[j]);
                }
                FaceBox box = feaList[i].box;
                // 绘制人脸框
                FaceDraw.drawRects(rgbMat, box);
            }
            // 取完特征值后可把绘制人脸框的图片保存本地
            Imgcodecs.imwrite("feature.jpg", rgbMat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
