package com.jni.face;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class TestZF {
	

	
	public static void main(String[] args) {
		Face face = new Face();
		face.sdkInit(false);
		
      face.clearTrackedFaces();
      face.setNotFaceThr(0.5f);
      face.setMinFaceSize(30);
      face.setTrackByDetectionInterval(1000);
      
//      new TestZF().rotate(face,"d:\\5.jpg");
      
//      TrackFaceInfo ti = face.trackMax("d:\\2.jpg");
//      System.out.println(ti.score+" "+ti.angle);
//      
//      face.clearTrackedFaces();
//      ti = face.trackMax("d:\\3.jpg");
//      System.out.println(ti.score+" "+ti.angle);
//      
//      face.clearTrackedFaces();
//      ti = face.trackMax("d:\\5.jpg");
//      System.out.println(ti.score+" "+ti.angle);
	}
}
