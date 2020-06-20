package com.hackathon.pCloudy.tests;

import com.hackathon.pCloudy.helpers.appium.AppiumSyncHelper;
import com.hackathon.pCloudy.helpers.report.ReportPortalLogHelper;
import com.hackathon.pCloudy.thread.ThreadLocalAppiumDriver;
import com.hackathon.pCloudy.utils.FileUtility;
import com.hackathon.pCloudy.utils.ScreenshotUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class CloudEnvironmentCheckTests {

    private static final Logger logger = LogManager.getLogger(CloudEnvironmentCheckTests.class.getName());

    @Test
    public void checkCloudDeviceSetup() {
        logger.info("If I am seen in log, which means that the CLOUD device setup is working properly.");
        new AppiumSyncHelper().pauseScript(5);
        // Take Screenshot
        ScreenshotUtility screenshotUtility = new ScreenshotUtility();
        byte[] ssAsBytes = screenshotUtility.takeScreenshotAsBase64ByteArray(ThreadLocalAppiumDriver.getAndroidDriver());
        ReportPortalLogHelper
                .getInstance()
                .logInfoWithSSAsBase64String(logger, new FileUtility().byteToBase64String(ssAsBytes), "screenshotCaptured");

    }
}
