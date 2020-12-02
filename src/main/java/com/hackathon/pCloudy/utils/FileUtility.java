package com.hackathon.pCloudy.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileUtility {

    private static final Logger logger = LogManager.getLogger(FileUtility.class.getName());

    public String byteToBase64String(ByteArrayInputStream byteInputStream) {
        try {
            // Read All Objects As Bytes
            byte[] asByteArray = IOUtils.toByteArray(byteInputStream);

            // Return base64 String
            return Base64Utils.encodeToString(asByteArray);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public String byteToBase64String(byte[] byteArray) {
        try {
            // Return base64 String
            return Base64Utils.encodeToString(byteArray);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public String byteToBase64String(String filePath) {
        try {
            // create object of File
            File ssFile = new File(filePath);

            // Open Input Stream
            FileInputStream fileInputStream = FileUtils.openInputStream(ssFile);

            // Read All Bytes
            byte[] asByteArray = IOUtils.toByteArray(fileInputStream);

            return Base64Utils.encodeToString(asByteArray);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
}
