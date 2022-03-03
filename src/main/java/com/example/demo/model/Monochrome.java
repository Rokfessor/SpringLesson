package com.example.demo.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monochrome {
    public static BufferedImage processImage (BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = (int)(c.getRed() * 0.299);
                int green = (int)(c.getGreen() * 0.587);
                int blue = (int)(c.getBlue() *0.114);
                Color newColor = new Color(red+green+blue,
                        red+green+blue,red+green+blue);
                result.setRGB(j,i,newColor.getRGB());
            }
        }

        return result;
    }
}
