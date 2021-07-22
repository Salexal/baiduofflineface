package com.jni.face;
import com.jni.struct.TrackFaceInfo;
import com.jni.struct.ActionLiveConf;
import com.jni.struct.Attribute;
import com.jni.struct.CropFaceConf;
import com.jni.struct.RgbDepthInfo;
import com.jni.struct.DetectConf;
import com.jni.struct.EyeClose;
import com.jni.struct.FaceBox;
import com.jni.struct.Feature;
import com.jni.struct.FeatureInfo;
import com.jni.struct.GazeInfo;
import com.jni.struct.HeadPose;
import com.jni.struct.Landmarks;
import com.jni.struct.LiveFeatureInfo;
import com.jni.struct.LivenessInfo;
import com.jni.struct.Occlusion;
import com.jni.struct.TrackConf;

public class Face {
    // *******以下为人脸sdk api接口*********
    /* sdk初始化 */
    public native int sdkInit(String modelPath);
    /* sdk销毁（资源释放) */
    public native void sdkDestroy();
    /* 判断是否授权 */
    public native boolean isAuth();       
    /* 人脸检测 */
    public static native FaceBox[] detect(long matAddr, int type);  
    /* 人脸跟踪 */
    public static native TrackFaceInfo[] track(long matAddr, int type);
    /* 清除跟踪历史 */
    public static native int clearTrackHistory();
    /* 获取人脸属性 */
    public static native Attribute[] faceAttr(long matAddr);
    /* 眼睛闭合状态检测 */
    public static native EyeClose[] faceEyeClose(long matAddr);
    /* 注意力检测 */
    public static native GazeInfo[] faceGaze(long matAddr);
    /*  姿态角检测 */
    public static native HeadPose[] faceHeadPose(long matAddr);  
    /*  光照检测 */
    public static native int[] faceIllumination(long matAddr);
    /* 模糊度检测 */
    public static native float[] faceBlur(long matAddr);
    /*  嘴巴闭合检测 */
    public static native float[] faceMouthClose(long matAddr);
    /*  口罩佩戴检测 */
    public static native float[] faceMouthMask(long matAddr);
    /*  遮挡度检测 */
    public static native Occlusion[] faceOcclusion(long matAddr);
    /* 暗光恢复 */
    public static native long darkEnhance(long matAddr);
    /*  人脸抠图 */
    public static native long faceCrop(long matAddr);
    /*  最佳人脸检测 */
    public static native float[] faceBest(long matAddr);
    /*  人脸关键点 */
    public static native Landmarks[] faceLandmark(long matAddr, int type);
    /*  动作活体 */
    public static native FaceBox[] faceActionLive(long matAddr, int actionType, int[] actionResult); 
    /*  清除动作活体历史 */
    public static native int clearActionLiveHistory();
    /*  人脸特征值 (type 0: rgb可见光特征值 1：nir近红外特征值）*/
    public static native FeatureInfo[] faceFeature(long matAddr, int type);
    /*  深度特征值 */
    public static native FeatureInfo[] rgbdFeature(long rgbMatAddr, byte[] depthBufs, int bufsLen);
    /* 人脸特征值 + 活体 */
    public static native LiveFeatureInfo[] livenessFeature(long matAddr, int type);
    /* 特征值比较 */
    public static native float compareFeature(Feature f1, Feature f2, int type);
    /*  rgb可见光静默活体 */
    public static native LivenessInfo[] rgbLiveness(long matAddr);
    /*  nir近红外静默活体 */
    public static native LivenessInfo[] nirLiveness(long matAddr);
    /*  rgb+depth 可见光和深度双目静默活体  */
    public static native RgbDepthInfo[] rgbAndDepthLiveness(long rgbAddr, long depthAddr);
    
    // ********以下为人脸库接口********
    /*  人脸注册(传图片特征值) */
    public static native String userAdd(Feature fea, String userId, String groupId, String userInfo);
    /* 人脸更新(传图片特征值) */
    public static native String userUpdate(Feature fea, String userId, String groupId, String userInfo);
    /*  人脸删除 */
    public static native String userFaceDelete(String userId, String groupId, String faceToken);
    /*  用户删除 */
    public static native String userDelete(String userId, String groupId);
    /*  组添加 */
    public static native String groupAdd(String groupId);
    /*  组删除 */
    public static native String groupDelete(String groupId);
    /*  查询用户信息 */
    public static native String getUserInfo(String userId, String groupId);
    /*  用户组列表查询 */
    public static native String getUserList(String groupId, int start, int length);
    /*  组列表查询 */
    public static native String getGroupList(int start, int length);
    /*  1:N人脸识别 */
    public static native String identify(Feature fea, String groupIdList, 
        String userId, int type);
    /*  1:N人脸识别(和整个库比较,需要提前调loadDbFace（）) */
    public static native String identifyWithAll(Feature fea, int type);
    /*  加载数据库人脸库到内存（数据库数据通过userAdd等注册入库）*/
    public static native boolean loadDbFace();
    /*  ********以下为镜头模组接口********* 
    /*  初始化奥比摄像头 */
    public static native int openOrbe();
    /*  打开奥比摄像头 */
    public static native int cameraOrbe(long rgbAddr, long depthAddr);
    /*  释放奥比摄像头 */
    public static native int closeOrbe();
    /*  初始化华杰艾米摄像头 */
    public static native int openHjimi();
    /*  打开华杰艾米摄像头 */
    public static native int cameraHjimi(long rgbAddr, long depthAddr);
    /*  释放华杰艾米摄像头 */
    public static native int closeHjimi();
    
    // ******** 以下为能力加载*********
    /*  加载rgb人脸检测能力 */
    public static native int loadRgbDetectAbility(DetectConf conf);    
    /*  加载nir人脸检测能力 */
    public static native int loadNirDetectAbility(DetectConf conf);   
    /* 加载fast align人脸关键点能力 */
    public static native int loadFastAlignAbility(float threshold);   
    /*  加载accurate align人脸关键点能力 */
    public static native int loadAccurateAlignAbility(float threshold);   
    /*  加载nir align人脸关键点能力 */
    public static native int loadNirAlignAbility(float threshold);    
    /*  加载rgb人脸跟踪能力 */
    public static native int loadRgbTrackAbility(TrackConf conf);   
    /*  加载nir人脸跟踪能力 */
    public static native int loadNirTrackAbility(TrackConf conf);  
    /*  加载人脸抠图能力 */
    public static native int loadCropFaceAbility(CropFaceConf conf);  
    /*  加载其他人脸能力 */
    public static native int loadOtherAbility();    
    /* 加载动作活体能力 */
    public static native int loadActionLiveAbility(ActionLiveConf conf);   
    // ********* 以下为系统加载库文件及opencv **********
    static {
        /*  加载dll */
        System.loadLibrary("BaiduFaceApi");
        System.loadLibrary("./opencv-dll/opencv_java320");
    }

    public static void main(String[] args) {
        /*  sdk初始化 */
        Face api = new Face();
        // 模型路径，默认为空为sdk自带路径，如sdk中所放置
        String modelPath ="";
        int res = api.sdkInit(modelPath);
        if (res != 0) {
            System.out.printf("sdk init fail and error =%d", res);
            return;
        }
        // 人脸能力加载
        FaceLoadAbility load = new FaceLoadAbility();
        load.loadAllAbility();
        // 人脸示例demo
        long begin = TimeUtil.getTimeStamp();
        FaceDemo demo = new FaceDemo();
        demo.faceSample();
        long end = TimeUtil.getTimeStamp();
        System.out.println("time cost is:" + (end - begin));
        // sdk销毁，释放内存防内存泄漏
        api.sdkDestroy();           
    }
}
