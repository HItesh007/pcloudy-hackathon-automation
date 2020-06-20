package com.hackathon.pCloudy.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class ScreenshotUtility {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtility.class.getName());

    /**
     * Take screenshot as Base64 format
     *
     * @param _driver AppiumDriver object
     * @return String in BASE64 Format
     */
    public String takeScreenshotAsBase64(AppiumDriver<?> _driver) {
        return ((TakesScreenshot) _driver).getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Take screenshot as ByteArrayInputStream
     *
     * @param driverObj <code>AppiumDriver</code>
     * @return  <code>ByteArrayInputStream</code>
     */
    public ByteArrayInputStream takeScreenshotAsBase64AsInputByteArray(AppiumDriver<?> driverObj) {
        return new ByteArrayInputStream(((TakesScreenshot) driverObj).getScreenshotAs(OutputType.BASE64).getBytes());
    }

    /**
     * Take screenshot as ByteArrayInputStream
     *
     * @param driverObj <code>AppiumDriver</code>
     * @return  <code>ByteArrayInputStream</code>
     */
    public byte[] takeScreenshotAsBase64ByteArray(AppiumDriver<?> driverObj) {
        return ((TakesScreenshot) driverObj).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Takes Screenshot of an element as Base64 String
     *
     * @param _driver AppiumDriver object
     * @param element MobileElement object
     * @return Base64 String
     * @throws IOException
     */
    public String takeScreenshotAsBase64(AppiumDriver<?> _driver, MobileElement element) throws IOException {
        return convertToBase64Image(getScreenshotOfElement(_driver, element));
    }

    /**
     * Takes Screenshot of an element as File type
     *
     * @param _driver AppiumDriver object
     * @param element MobileElement object
     * @return Screenshot as file
     * @throws IOException
     */
    public String takeScreenshotAsFile(AppiumDriver<?> _driver, MobileElement element) throws IOException {
        return convertToBase64Image(getScreenshotOfElement(_driver, element));
    }

    /**
     * Take screenshot at File
     *
     * @param _driver
     * @return
     */
    public File takeScreenshotAsFile(AppiumDriver<?> _driver) {
        return ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
    }


    /**
     * Converts the image of type File to Base64 String
     *
     * @param imageAsFileObj Image object of type File
     * @return Base64 String
     */
    private String convertToBase64Image(File imageAsFileObj) {
        String base64Image = "";

        try (FileInputStream imageInFile = new FileInputStream(imageAsFileObj)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) imageAsFileObj.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    /**
     * Takes the screenshot of element
     *
     * @param _driver AppiumDriver object
     * @param element MobileElement Object
     * @return Screenshot object of type File
     * @throws IOException
     */
    private File getScreenshotOfElement(AppiumDriver<?> _driver, MobileElement element) throws IOException {

        // Get the dimensions of the element
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();

        // Take Screenshot
        File screenShotAsFile = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullScreenshot = ImageIO.read(screenShotAsFile);

        // Get the location of element on the page
        Point point = element.getLocation();

        // Crop the entire page screenshot to get only the element screenshot
        BufferedImage elementScreenshot = fullScreenshot.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);

        ImageIO.write(elementScreenshot, "png", screenShotAsFile);

        return screenShotAsFile;
    }

    /**
     * To store File Object of screenshot on disk
     *
     * @param screenshotAsFile <code>File</code> <b>File type object of Screenshot</b>
     * @param filePath <code>String</code> <b>Absolute file path with extension</b>
     * @return <code>boolean</code> <b>True if file created, false otherwise.</b>
     */
    public boolean saveScreenShotFile(File screenshotAsFile,String filePath) {
        boolean isFileCreated = false;
        try {
            File file = new File(filePath);

            // If directory structure does not exist, create one
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // Store screenshot
            FileUtils.copyFile(screenshotAsFile, file);

            if(file.exists()) {
                isFileCreated = true;
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return isFileCreated;
    }
}