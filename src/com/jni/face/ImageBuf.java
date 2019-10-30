package com.jni.face;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBuf {
    
    public static byte[] getImageBuffer(String imgPath) {
        File f = new File(imgPath);
        BufferedImage bi;      
        try {      
            bi = ImageIO.read(f);      
            ByteArrayOutputStream baos = new ByteArrayOutputStream();      
            ImageIO.write(bi, "jpg", baos);      
            byte[] bytes = baos.toByteArray();      
            return bytes;      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        return null; 
    }
}