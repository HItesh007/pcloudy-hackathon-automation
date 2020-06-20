package com.hackathon.pCloudy.base;

import com.hackathon.pCloudy.constant.SystemConstants;
import com.hackathon.pCloudy.utils.DateUtility;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class AppiumServer {
    private static final Logger logger = LogManager.getLogger(AppiumServer.class.getName());
    private AppiumDriverLocalService appiumDriverLocalService;

    /***
     *
     * @param atIPAddress IP Address At Which you want to start
     *                    Appium Server
     * @return AppiumDriverLocalService
     */
    public AppiumDriverLocalService buildAppiumServerProcess(String atIPAddress) {
        try {
            String appiumServerLogFileName = SystemConstants.USER_DIR
                    + SystemConstants.FILE_SEPARATOR
                    + "appium-server-logs"
                    + SystemConstants.FILE_SEPARATOR
                    + DateUtility.getCurrentTimeStampWithFormatAs("dd-MM-yyyy")
                    + SystemConstants.FILE_SEPARATOR
                    + "appium-server-logs-"
                    + DateUtility.getCurrentTimeStamp()
                    + ".log";

            File appiumServerFile = new File(appiumServerLogFileName);

            if(!appiumServerFile.getParentFile().exists()) {
                appiumServerFile.getParentFile().mkdirs();
            }

            AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
            appiumServiceBuilder.withIPAddress(atIPAddress);
            appiumServiceBuilder.usingAnyFreePort();
            appiumServiceBuilder.withLogFile(appiumServerFile);
            appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);

            logger.info("Appium Server Log File Can be found here: " + appiumServerLogFileName);

            appiumDriverLocalService = AppiumDriverLocalService.buildService(appiumServiceBuilder);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return appiumDriverLocalService;
    }
}
