package com.hackathon.pCloudy.helpers.report;

import org.apache.logging.log4j.Logger;
import rp.com.google.common.io.BaseEncoding;

import java.io.ByteArrayInputStream;
import java.io.File;

public class ReportPortalLogHelper {
    private static ThreadLocal<ReportPortalLogHelper> instance = new ThreadLocal<>();

    private ReportPortalLogHelper() {
        // To Do
    }

    public static ReportPortalLogHelper getInstance() {
        if (instance.get() == null) {
            instance.set(new ReportPortalLogHelper());
        }
        return instance.get();
    }

    /**
     * To store Image files as <code>File</code> object
     *
     * @param loggerObject Instance of {@link Logger} object
     * @param file         Image or screenshot as <code>File</code> representation
     * @param message      Message to log with screenshot
     */
    public void logSSAsFile(Logger loggerObject, File file, String message) {
        loggerObject.info("RP_MESSAGE#FILE#{}#{}", file.getAbsolutePath(), message);
    }

    /**
     * To log image or screenshot as <code>byte[]</code>
     *
     * @param loggerObject Instance of {@link Logger} object
     * @param bytes        Image or screenshot as <code>byte[]</code> representation
     * @param message      Message to log with screenshot
     */
    public void logSSAsByte(Logger loggerObject, byte[] bytes, String message) {
        loggerObject.info("RP_MESSAGE#BASE64#{}#{}", BaseEncoding.base64().encode(bytes), message);
    }

    /**
     * To log image or screenshot as Base64 <code>String</code> format
     *
     * @param loggerObject Instance of {@link Logger} object
     * @param base64       Image or screenshot as Base64 <code>String</code>
     * @param message      Message to log with Screenshot
     */
    public void logSSAsBase64String(Logger loggerObject, String base64, String message) {
        loggerObject.info("RP_MESSAGE#BASE64#{}#{}", base64, message);
    }

    /**
     * To log image or screenshot as Base64 <code>String</code> format
     *
     * @param loggerObject Instance of {@link Logger} object
     * @param base64       Image or screenshot as Base64 <code>String</code>
     * @param message      Message to log with Screenshot
     */
    public void logErrorWithSSAsBase64String(Logger loggerObject, String base64, String message) {
        loggerObject.error("RP_MESSAGE#BASE64#{}#{}", base64, message);
    }

    /**
     * To log image or screenshot as Base64 <code>String</code> format
     *
     * @param loggerObject Instance of {@link Logger} object
     * @param base64       Image or screenshot as Base64 <code>String</code>
     * @param message      Message to log with Screenshot
     */
    public void logInfoWithSSAsBase64String(Logger loggerObject, String base64, String message) {
        loggerObject.info("RP_MESSAGE#BASE64#{}#{}", base64, message);
    }

    /**
     * To log error message into report portal with screenshot as <code>ByteArrayInputStream</code>
     *
     * @param loggerObject     Instance of {@link Logger} object
     * @param imageBase64Array Image or Screenshot as <code>ByteArrayInputStream</code>
     * @param message          Message to log with screenshot
     */
    public void logErrorSSAsByteStream(Logger loggerObject, ByteArrayInputStream imageBase64Array, String message) {
        loggerObject.error("RP_MESSAGE#BASE64#{}#{}", imageBase64Array, message);
    }

    /**
     * To log info message into report portal with screenshot as <code>ByteArrayInputStream</code>
     *
     * @param loggerObject     Instance of {@link Logger} object
     * @param imageBase64Array Image or Screenshot as <code>ByteArrayInputStream</code>
     * @param message          Message to log with screenshot
     */
    public void logInfoSSAsByteStream(Logger loggerObject, ByteArrayInputStream imageBase64Array, String message) {
        loggerObject.info("RP_MESSAGE#BASE64#{}#{}", imageBase64Array, message);
    }
}
