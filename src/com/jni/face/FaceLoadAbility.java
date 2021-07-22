package com.jni.face;

import com.jni.struct.ActionLiveConf;
import com.jni.struct.CropFaceConf;
import com.jni.struct.DetectConf;
import com.jni.struct.TrackConf;

/**
 * 
 * @ 人脸能力加载类
 *
 */
public class FaceLoadAbility {
    // 加载所有人脸能力
    public void loadAllAbility() {
        loadRgbDetect();
        loadNirDetect();
        loadFastAlign();
        loadAccurateAlign();
        loadNirAlign();
        loadRgbTrack();
        loadNirTrack();
        loadCropFace();
        loadOtherAbility();
        loadActionLive();
    }
    
    // 加载rgb检测能力
    public  void loadRgbDetect() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = false;
        if (defaultConf){
            // 默认配置
            int maxDetectNum = 1; // 设置最多检测跟踪人数（多人脸检测），默认为1，推荐最多可设为50
            DetectConf conf = new DetectConf();
            conf.maxDetectNum = maxDetectNum; // 最大人脸个数
            conf.minFaceSize = 0; // 最小人脸大小
            conf.scaleRatio = -1; // 缩放比例
            conf.notFaceThreshold = 0.5f; // 置信度设为0.5 
            Face.loadRgbDetectAbility(conf);
        }
        else{
            int maxDetectNum = 50; // 设置最多检测跟踪人数（多人脸检测），默认为1，推荐最多可设为50
            DetectConf conf = new DetectConf();
            conf.maxDetectNum = maxDetectNum; // 最大人脸个数
            conf.minFaceSize = 0; // 最小人脸大小
            conf.scaleRatio = -1;  // 缩放比例
            conf.notFaceThreshold = 0.5f; // 置信度设为0.5 
            Face.loadRgbDetectAbility(conf);
        }
    }
    // 加载nir检测能力
    public void loadNirDetect() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            int maxDetectNum = 1; // 设置最多检测跟踪人数（多人脸检测），默认为1，推荐最多可设为50
            DetectConf conf = new DetectConf();
            conf.maxDetectNum = maxDetectNum; // 最大人脸个数
            conf.minFaceSize = 0; // 最小人脸大小
            conf.scaleRatio = -1;  // 缩放比例
            conf.notFaceThreshold = 0.5f; // 置信度设为0.5 
            Face.loadRgbDetectAbility(conf);
        }
        else{
            int maxDetectNum = 50; // 设置最多检测跟踪人数（多人脸检测），默认为1，推荐最多可设为50
            DetectConf conf = new DetectConf();
            conf.maxDetectNum = maxDetectNum; // 最大人脸个数
            conf.minFaceSize = 0; // 最小人脸大小
            conf.scaleRatio = -1;  // 缩放比例
            conf.notFaceThreshold = 0.5f; // 置信度设为0.5 
            Face.loadRgbDetectAbility(conf);
        }
    }
    
    // 加载fast align检测能力
    public void loadFastAlign() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            Face.loadFastAlignAbility(-1);
        }
        else{
            float threshold = 0.5f;
            Face.loadFastAlignAbility(threshold);
        }
    }
    
    // 加载accurate align检测能力
    public void loadAccurateAlign() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            Face.loadAccurateAlignAbility(-1);
        }
        else{
            float threshold = 0.5f;
            Face.loadAccurateAlignAbility(threshold);
        }
    }
    
    // 加载nir align检测能力
    public void loadNirAlign() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            Face.loadNirAlignAbility(-1);
        }
        else{
            float threshold = 0.5f;
            Face.loadNirAlignAbility(threshold);
        }
    }
    
    // 加载rgb跟踪能力
    public void loadRgbTrack() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            TrackConf conf = new TrackConf();
            conf.detectIntvBeforeTrack = 0.0f;
            conf.detectIntvDuringTrack = 0.02f;
            Face.loadRgbTrackAbility(conf);
        }
        else{
            TrackConf conf = new TrackConf();
            conf.detectIntvBeforeTrack = 0.02f;
            conf.detectIntvDuringTrack = 0.02f;
            Face.loadRgbTrackAbility(conf);
        }
    }
    
    // 加载nir跟踪能力
    public void loadNirTrack() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            TrackConf conf = new TrackConf();
            conf.detectIntvBeforeTrack = 0.02f;
            conf.detectIntvDuringTrack = 0.02f;
            Face.loadRgbTrackAbility(conf);
        }
        else{
            TrackConf conf = new TrackConf();
            conf.detectIntvBeforeTrack = 0.02f;
            conf.detectIntvDuringTrack = 0.02f;
            Face.loadRgbTrackAbility(conf);
        }
    }
    
    // 加载人脸抠图能力
    public void loadCropFace() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            CropFaceConf conf = new CropFaceConf();
            conf.isFlat = 0;
            conf.cropSize = 400;
            conf.enlargeRatio = 2.0f;
            Face.loadCropFaceAbility(conf);
        }
        else{
            CropFaceConf conf = new CropFaceConf();
            conf.isFlat = 0;
            conf.cropSize = 200;
            conf.enlargeRatio = 0.8f;
            Face.loadCropFaceAbility(conf);
        }
    }
    
    // 加载其他人脸能力
    public void loadOtherAbility() {
        Face.loadOtherAbility();
    }
    
   // 加载动作活体能力
    public void loadActionLive() {
        // 启用默认配置，则不传参，定制配置，传conf，二者二选一
        boolean defaultConf = true;
        if (defaultConf){
            // 默认配置
            ActionLiveConf conf = new ActionLiveConf();
            conf.eyeOpenThreshold = 0.5f;
            conf.eyeCloseThreshold = 0.5f;
            conf.mouthOpenThreshold = 0.5f;
            conf.mouthCloseThreshold = 0.5f;
            conf.lookUpThreshold = 0.5f;
            conf.lookDownThreshold = 0.5f;
            conf.turnLeftThreshold = 0.5f;
            conf.turnRightThreshold = 0.5f;
            conf.nodThreshold = 0.5f;
            conf.shakeThreshold = 0.5f;
            conf.maxCacheNum = 10;
            Face.loadActionLiveAbility(conf);
        }
        else{
            // 定制化配置
            ActionLiveConf conf = new ActionLiveConf();
            conf.eyeOpenThreshold = 0.5f;
            conf.eyeCloseThreshold = 0.5f;
            conf.mouthOpenThreshold = 0.5f;
            conf.mouthCloseThreshold = 0.5f;
            conf.lookUpThreshold = 0.5f;
            conf.lookDownThreshold = 0.5f;
            conf.turnLeftThreshold = 0.5f;
            conf.turnRightThreshold = 0.5f;
            conf.nodThreshold = 0.5f;
            conf.shakeThreshold = 0.5f;
            conf.maxCacheNum = 1;
            Face.loadActionLiveAbility(conf);
        }
    }    
}
