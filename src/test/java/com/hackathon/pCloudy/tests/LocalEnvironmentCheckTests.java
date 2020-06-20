package com.hackathon.pCloudy.tests;

import com.hackathon.pCloudy.helpers.report.ReportPortalLogHelper;
import com.hackathon.pCloudy.thread.ThreadLocalAppiumDriver;
import com.hackathon.pCloudy.utils.FileUtility;
import com.hackathon.pCloudy.utils.ScreenshotUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class LocalEnvironmentCheckTests {

    private static final Logger logger = LogManager.getLogger(LocalEnvironmentCheckTests.class.getName());

    @Test
    public void checkLocalDeviceSetup() {
        logger.info("If I am seen in log, which means that the local device setup is working properly.");
        // Take Screenshot
        ScreenshotUtility screenshotUtility = new ScreenshotUtility();
        byte[] ssAsBytes = screenshotUtility.takeScreenshotAsBase64ByteArray(ThreadLocalAppiumDriver.getAndroidDriver());
        ReportPortalLogHelper
                .getInstance()
                .logInfoWithSSAsBase64String(logger, new FileUtility().byteToBase64String(ssAsBytes), "screenshotCaptured");
    }
}
