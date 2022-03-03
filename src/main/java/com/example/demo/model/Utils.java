package com.example.demo.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Utils {
    public static Color[] removeItem(Color[] array, int index) {
        if (index < 0 || index >= array.length)
            throw new IndexOutOfBoundsException();

        Color[] result = new Color[array.length - 1];
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
            if (i == index) {
                flag = true;
                continue;
            }
            result[flag ? i - 1 : i] = array[i];
        }

        return result;
    }

    public static Color[][] transposeMatrix(Color[][] matrix) {
        Color[][] temp = new Color[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }

    public static BufferedImage base64ToImage(String base64) throws IOException {
        String base64Str = base64.split(",")[1];
        byte[] imageAsBytes = Base64.getDecoder().decode(base64Str);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageAsBytes)) {
            return ImageIO.read(bis);
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        Image resultingImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static String imageToBase64(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpeg", bos);
            byte[] imageBytes = bos.toByteArray();

            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);
        }
    }

    public static BufferedImage matrixToImg(Color[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        BufferedImage image = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                image.setRGB(y, x, matrix[x][y].getRGB());

        return image;
    }
}