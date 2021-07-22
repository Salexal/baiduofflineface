package com.jni.struct;

/**
 * 
 * @检测配置conf
 *
 */
public class DetectConf {
    public int maxDetectNum; // 需要检测的最大人脸数目
    public float minFaceSize; // 需要检测的最小人脸大小
    public float notFaceThreshold; // 过滤非人脸的阈值 
    public float scaleRatio; // 输入图像的缩放
}
