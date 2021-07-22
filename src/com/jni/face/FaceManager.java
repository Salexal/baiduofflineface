package com.jni.face;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.jni.struct.Feature;
import com.jni.struct.FeatureInfo;

/**
 *  备注（人脸数据库管理说明）：
 *  人脸数据库为采用sqlite3的数据库，会自动生成一个db目录夹，下面有数据库face.db文件保存数据库
 *  可用SQLite Expert之类的可视化工具打开查看,亦可用命令行，方法请自行百度。
 *  该数据库仅仅可适应于5w人左右的人脸库，且设计表格等属于小型通用化。
 *  若不能满足客户个性化需求，客户可自行设计数据库保存数据。宗旨就是每个人脸图片提取一个特征值保存。
 *  人脸1:1,1:N比对及识别实际就是特征值的比对。1:1只要提取2张不同的图片特征值调用compareFeature比对。
 *  1：N是提取一个特征值和数据库中已保存的N个特征值一一比对(比对速度很快，不用担心效率问题)，
 *  最终取分数高的值为最高相似度。
 *  相似度识别的分数可自行测试根据实验结果拟定，一般推荐相似度大于80分为同一人。
 * 
 */

public class FaceManager {
    // 人脸注册
    public void userAdd() {
        // 获取人脸特征值
        Mat mat = Imgcodecs.imread("images/1.jpg");
        long matAddr = mat.getNativeObjAddr();
        // type 0： 表示rgb 人脸检测 1：表示nir人脸检测
        int type = 0;
        FeatureInfo[] feaList = Face.faceFeature(matAddr, type);
        if (feaList == null || feaList.length <= 0) {
            System.out.println("get feature fail");
            return;
        }
        Feature fea = feaList[0].feature;
        // 以下为人脸信息
        // 用户id，可自定义
        String userId = "user";
        // 组id，可自定义
        String groupId = "group";
        // 用户信息，可自定义
        String userInfo = "userinfo";
        // 用人脸特征值注册
        String res =  Face.userAdd(fea, userId, groupId, userInfo);
        System.out.println("user add result is:" + res);
    }

    // 人脸更新
    public void userUpdate() {
        // 获取人脸特征值
        Mat mat = Imgcodecs.imread("images/2.jpg");
        long matAddr = mat.getNativeObjAddr();
        // type 0： 表示rgb 人脸检测 1：表示nir人脸检测
        int type = 0;
        FeatureInfo[] feaList = Face.faceFeature(matAddr, type);
        if (feaList == null || feaList.length <= 0) {
            System.out.println("get feature fail");
            return;
        }
        Feature fea = feaList[0].feature;
        // 以下为人脸信息
        // 用户id，可自定义
        String userId = "user";
        // 组id，可自定义
        String groupId = "group";
        // 用户信息，可自定义
        String userInfo = "userinfo";
        // 用人脸特征值注册
        String res =  Face.userUpdate(fea, userId, groupId, userInfo);
        System.out.println("user update result is:" + res);
    }

    // 删除
    public void faceDelete() {
        // 用户id，可自定义
        String userId = "user";
        // 组id，可自定义
        String groupId = "group";
        // face token
        String token = "b6d8e657b5acd4dbae98efed64ea7c4b";
        // 人脸删除
        String res = Face.userFaceDelete(userId, groupId, token);
        System.out.println("user_face_delete res is:" + res);
        
        // 用户删除
        res = Face.userDelete(userId, groupId);
        System.out.println("userDelete res is:" + res);
    }

    // 组操作
    public void groupManager() {
        String groupId = "group";
        // 组添加
        String res = Face.groupAdd(groupId);
        System.out.println("groupAdd res is:" + res);
        // 组删除
        String res2 = Face.groupDelete(groupId);
        System.out.println("groupDelete res is:" + res2);
    }

    // 查询用户信息
    public void getUserinfo() {
        // 用户id，可自定义
        String userId = "user";
        // 组id，可自定义
        String groupId = "group";
        String res = Face.getUserInfo(userId, groupId);
        System.out.println("getUserInfo res is:" + res);
    }

    // 用户组列表查询
    public void getUserlist() {
        // 组id，可自定义
        String groupId = "group";
        String res = Face.getUserList(groupId, 0, 100);
        System.out.println("getUserList res is:" + res);
    }

    // 组列表查询
    public void getGrouplist() {
        String res = Face.getGroupList(0, 100);
        System.out.println("getGroupList res is:" + res);
    }
}
