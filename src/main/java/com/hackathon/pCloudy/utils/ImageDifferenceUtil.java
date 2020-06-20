package com.hackathon.pCloudy.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDifferenceUtil {


    /**
     * To get the percentage difference between two images.
     *
     * @param img1 String <p> 1st Image path </p>
     * @param img2 String <p> 2nd Image Path </p>
     * @return double <p> Returns percentage difference between two images specified. </p>
     */
    public static double getDifferencePercent(String img1, String img2) throws IOException {
        BufferedImage image1 = ImageIO.read(new File(img1));
        BufferedImage image2 = ImageIO.read(new File(img2));

        int width = image1.getWidth();
        int height = image1.getHeight();
        int width2 = image2.getWidth();
        int height2 = image2.getHeight();
        if (width != width2 || height != height2) {
            throw new IllegalArgumentException(String.format("Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height, width2, height2));
        }

        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(image1.getRGB(x, y), image2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;

        return 100.0 * diff / maxDiff;
    }

    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
}
