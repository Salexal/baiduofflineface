package com.jni.face;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 
 * @function: 暗光恢复
 *
 */
public class DarkEnhance {
    // 暗光恢复
    public int imageDarkEnhance() {
        Mat mat = Imgcodecs.imread("images/mask.jpg");
        long matAddr = mat.getNativeObjAddr();
        long outAddr = Face.darkEnhance(matAddr);              
        Mat outMat = new Mat(outAddr);     
        if (outMat.empty()) {
            System.out.println("dark enhance fail");
            return -1;
        }
        // 图片可保存到本地
        Imgcodecs.imwrite("dakenhance.jpg", outMat);      
        return 0;
    }
}
