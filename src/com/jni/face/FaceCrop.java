package com.jni.face;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
/**
 * 
 * @ 人脸抠图
 *
 */
public class FaceCrop {
    // 人脸抠图示例
    public int imageFaceCrop() {
        Mat mat = Imgcodecs.imread("images/1.jpg");
        long matAddr = mat.getNativeObjAddr();
        long outAddr = Face.faceCrop(matAddr);
        Mat outMat = new Mat(outAddr);
        if (outMat.empty()) {
            System.out.println("face crop fail");
            return -1;
        }
        // 抠图完毕可保存到本地
        Imgcodecs.imwrite("crop.jpg", outMat); 
        return 0;
    }
}
