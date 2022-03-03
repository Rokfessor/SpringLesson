package com.example.demo.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SeamCarving {
    private static final int MAX_INT = Integer.MAX_VALUE;

    public static BufferedImage processImage(BufferedImage image, int minWidth, int minHeight) {
        Color[][] pixels = createPixelMatrix(image);
        pixels = cutByWidth(pixels, minWidth);
        pixels = cutByHeight(pixels, minHeight);
        return Utils.matrixToImg(pixels);
    }

    private static Color[][] cutByWidth(Color[][] pixels, int minWidth) {
        Color[][] result = pixels;
        int width = pixels[0].length;

        while (width > minWidth) {
            int[][] energyMatrix = calcEnergyMatrix(result);
            energyMatrix = calcMatr(energyMatrix);
            int[] path = findShortestPath(energyMatrix);
            --width;
            result = cutSeam(result, path);
        }

        return result;
    }

    private static Color[][] cutByHeight(Color[][] pixels, int minHeight) {
        Color[][] result = Utils.transposeMatrix(pixels);
        result = cutByWidth(result, minHeight);
        return Utils.transposeMatrix(result);
    }

    private static Color[][] createPixelMatrix(BufferedImage image) { //Метод конвертации картинки в матрицу цветов пикселей
        int width = image.getWidth(); //Ширина картинки
        int height = image.getHeight(); //Высота картинки

        Color[][] pixels = new Color[height][width]; //Создаём матрицу цветов пикселей

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                pixels[i][j] = new Color(image.getRGB(j, i)); // Берём из картинки цвет пикселя

        return pixels;
    }

    private static int[][] calcEnergyMatrix(Color[][] pixels) {
        int width = pixels[0].length;
        int height = pixels.length;
        int[][] energy = new int[height][width];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                energy[i][j] = calcEnergyPixel(pixels, i, j);

        return energy;
    }

    private static int calcEnergyPixel(Color[][] pixels, int y, int x) {
        int width = pixels[0].length;
        Color leftPixel = (x - 1 < 0) ? new Color(0) : pixels[y][x - 1];
        /*
        //Аналогично
        Color leftPixel;
        if (x - 1 < 0)
            leftPixel = new Color(0);
        else
            leftPixel = pixels[x - 1][y];
         */
        Color middlePixel = pixels[y][x];
        Color rightPixel = (x + 1 >= width) ? new Color(0) : pixels[y][x + 1];

        double energy = Math.sqrt(
                Math.pow(leftPixel.getRed() - middlePixel.getRed(), 2) +
                        Math.pow(leftPixel.getGreen() - middlePixel.getGreen(), 2) +
                        Math.pow(leftPixel.getGreen() - middlePixel.getGreen(), 2) +
                        Math.pow(leftPixel.getBlue() - middlePixel.getBlue(), 2) +
                        Math.pow(rightPixel.getGreen() - middlePixel.getGreen(), 2) +
                        Math.pow(rightPixel.getGreen() - middlePixel.getGreen(), 2) +
                        Math.pow(rightPixel.getBlue() - middlePixel.getBlue(), 2)
        );

        return ((int) energy);
    }

    private static int[][] calcMatr(int[][] energyMatrix) {
        int h = energyMatrix.length;
        int w = energyMatrix[0].length;
        int[][] result = new int[h][w];

        System.arraycopy(energyMatrix[0], 0, result[0], 0, w);
        /*
        //Аналогично
        for (int i = 0; i < w; i++)
            result[0][i] = energyMatrix[0][i];
        */

        for (int i = 1; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int left = (j - 1 < 0) ? MAX_INT : result[i - 1][j - 1],
                        middle = result[i - 1][j],
                        right = (j + 1 >= w) ? MAX_INT : result[i - 1][j + 1];

                result[i][j] = energyMatrix[i][j] + Math.min(left, Math.min(middle, right));
            }
        }

        return result;
    }

    private static int[] findShortestPath(int[][] matr) {
        int h = matr.length;
        int w = matr[0].length;
        int[] result = new int[h];
        int temp = MAX_INT;
        for (int i = 0; i < w; i++)
            if (temp > matr[h - 1][i]) {
                temp = matr[h - 1][i];
                result[h - 1] = i;
            }

        for (int i = h - 2; i >= 0; i--) {
            int previousPosition = result[i + 1];
            final int left = (previousPosition - 1 < 0) ? MAX_INT : matr[i][previousPosition - 1],
                    middle = matr[i][previousPosition],
                    right = (previousPosition + 1 >= w) ? MAX_INT : matr[i][previousPosition + 1];

            int min = Math.min(left, Math.min(middle, right));
            if (min == left)
                result[i] = previousPosition - 1;
            else if (min == middle)
                result[i] = previousPosition;
            else
                result[i] = previousPosition + 1;
        }

        return result;
    }

    private static Color[][] cutSeam(Color[][] pixels, int[] path) {
        Color[][] result = new Color[pixels.length][pixels[0].length - 1];
        for (int i = 0; i < path.length; i++)
            result[i] = Utils.removeItem(pixels[i], path[i]);

        return result;
    }
}