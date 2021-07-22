package com.jni.struct;

/**
 * 
 * @ 动作活体配置conf
 *
 */
public class ActionLiveConf {
    public float eyeOpenThreshold;       // 睁眼的阈值
    public float eyeCloseThreshold;      // 闭眼的阈值
    public float mouthOpenThreshold;     // 张嘴的阈值
    public float mouthCloseThreshold;    // 闭嘴的阈值
    public float lookUpThreshold;        // 抬头的阈值
    public float lookDownThreshold;      // 低头的阈值
    public float turnLeftThreshold;      // 向左转头的阈值
    public float turnRightThreshold;     // 向右转头的阈值
    public float nodThreshold;            // 点头的角度差阈值
    public float shakeThreshold;          // 摇头的角度差阈值
    public int maxCacheNum;                // 缓存的帧数
}
