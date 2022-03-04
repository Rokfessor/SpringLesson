package com.example.demo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DemoApplicationTests {
    final String filePath = "/home/maxim/Desktop/Урок/image2.png";
    final String savedImage = "/home/maxim/Desktop/Урок/seamCarving.png";
    File file;
    BufferedImage image;

    @Before
    public void init() {
        try {
            file = new File(filePath);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Test
    public void convertToColorMatrix() {
        SeamCarving filter = new SeamCarving(image);
        matrixPrettyPrint(filter.pixels);

    }

    @Test
    public void calcMatr() {
        SeamCarving filter = new SeamCarving(image);
        int[][] matr = {
                {5, 20, 40, 10, 30},
                {0, 20, 90, 15, 120},
                {80, 100, 10, 250, 80},
                {150, 50, 200, 10, 300},
                {35, 70, 40, 5, 80}};
        int[][] matrH = filter.calcMatr(matr);
		matrixPrettyPrint(matrH);
        System.err.println();
        //matrixPrettyPrint(filter.calcMatrWidth(matr));
        int[] path = filter.findShortestPath(matrH);
        System.err.println(Arrays.toString(path));
	}

    @Test
    public void cut() {
        SeamCarving filter = new SeamCarving(image);
        Color[][] pixels = filter.cutByWidth(filter.pixels, 200);
        pixels = filter.cutByHeight(pixels, 200);
        try {
            File output = new File(savedImage);
            ImageIO.write(filter.matrixToImg(pixels), "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void printEnergyMatrix() {
        SeamCarving filter = new SeamCarving(image);
        int[][] matrix = filter.calcEnergyMatrix(filter.pixels);
        File output = new File(savedImage);
        try {
            ImageIO.write(filter.matrixToImg(matrix), "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void matrixPrettyPrint(int[][] matrix) {
        for (int[] obj : matrix)
            System.err.println(Arrays.toString(obj));
    }

    private void matrixPrettyPrint(Object[][] matrix) {
        for (Object[] obj : matrix)
            System.err.println(Arrays.toString(obj));
    }*/
}
