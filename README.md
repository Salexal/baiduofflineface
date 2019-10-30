使用百度人脸识别包的时候，报如上错误，解决方法如下：

通过dependencies,找相关的dll包，发现缺msvcp100.dll，从其他电脑上拷贝过来，或者从网上下这个dll也不管用。
https://github.com/lucasg/Dependencies，

手工加载依赖的dll(在SDK中face的类中修改)

问题依旧。

下载 微软常用运行库合集MSVBCRT AIO 2019.09.25 x86+x64，问题得到解决。以下网盘可以下载 ，下载完安装即可。
https://pan.baidu.com/s/15huy5gQeD_IsgTvZ6_Dqnw

如果还有错误，那么就安装一下opencv，以下也给出了链接地址。
Exception in thread “'main” java. lang . NoClas sDef FoundError: org/opencv/core/Core

https://pan.baidu.com/s/1rYylU2EbGOothwLXlkg8LQ

bat启动文件：
java -classpath F:\FaceOfflineTest\bin;C:\opencv32\opencv\build\java\opencv-320.jar;F:\FaceOfflineTest\nanohttpd-2.2.0.jar;F:\FaceOfflineTest\lib\commons-codec-1.10.jar;F:\FaceOfflineTest\lib\commons-logging-1.0.4.jar;F:\FaceOfflineTest\lib\httpclient-4.3.1.jar;F:\FaceOfflineTest\lib\httpcore-4.4.3.jar;F:\FaceOfflineTest\lib\commons-io-2.6.jar;F:\FaceOfflineTest\lib\fastjson-1.2.49.jar -Djava.library.path=F:\FaceOfflineTest;C:\opencv32\opencv\build\java\x64 -Dfile.encoding=UTF-8 com.jni.face.HttpFileServer
————————————————
版权声明：本文为CSDN博主「xwm1000」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/xwm1000/article/details/102812790
