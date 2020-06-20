package com.hackathon.pCloudy.base;

import com.hackathon.pCloudy.constant.EnvironmentConstants;
import com.hackathon.pCloudy.helpers.adb.ADBHelper;
import com.hackathon.pCloudy.helpers.appium.AppiumSyncHelper;
import com.hackathon.pCloudy.thread.ThreadAppiumServer;
import com.hackathon.pCloudy.thread.ThreadLocalAppiumDriver;
import com.hackathon.pCloudy.utils.DateUtility;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppiumBase {
    private static final Logger logger = LogManager.getLogger(AppiumBase.class.getName());
    private static String APPIUM_SERVER_URL = null;
    protected DesiredCapabilities caps = new DesiredCapabilities();


    @BeforeSuite(alwaysRun = true)
    public void startAppiumServer() {


        if(EnvironmentConstants.RUN_LOCALLY) {
            // Build Appium Service
            ThreadAppiumServer
                    .setAppiumServerDriverService
                            (
                                    new AppiumServer()
                                            .buildAppiumServerProcess("127.0.0.1")
                            );

            // Start Appium Service
            ThreadAppiumServer
                    .getAppiumServerDriverService()
                    .start();

            // Set appium server
            APPIUM_SERVER_URL = ThreadAppiumServer.getAppiumServerDriverService().getUrl().toString();

            String logDetails = "Appium Server Started At : " + APPIUM_SERVER_URL;
            logger.info(logDetails);
        }
    }

    @BeforeTest(alwaysRun = true)
    @Parameters({"device-name", "platform-name", "platform-version",
            "server-url", "udid", "app-package", "app-activity", "bundle-id", "automation-name", "wda-port", "wda-system-port"})
    public void initDevice(String _deviceName, String _platformName, String _platformVersion,
                           @Optional String _serverUrl,
                           @Optional("Optional UDID") String _udid,
                           @Optional("Optional appPackage") String _appPackage,
                           @Optional("Optional appActivity") String _appActivity,
                           @Optional("Optional BundleID") String _bundleID,
                           @Optional("Optional AutomationName") String _automationName,
                           @Optional("Optional iOS WDA Port") String _wdaPort,
                           @Optional("Optional Android System Port") String _wdaSysPort) {
        try {

            // Set System Property of Log4j2 Logger's file name to Device's name
            System.setProperty("logFileName", "Automation-script-log - " + DateUtility.getCurrentTimeStamp());

            if (_platformName.trim().toUpperCase().equals("ANDROID")) {

                new AppiumSyncHelper().pauseScript(5);

                androidDeviceSetup(_deviceName, _platformName, _platformVersion, _automationName, APPIUM_SERVER_URL, _udid, _appPackage, _appActivity, _wdaSysPort);

                ThreadContext.put("DEVICE_NAME", _deviceName);
            } else if (_platformName.trim().toUpperCase().equals("IOS")) {

                new AppiumSyncHelper().pauseScript(5);

                iOsDeviceSetup(_deviceName, _platformName, _platformVersion, _automationName, APPIUM_SERVER_URL, _udid, _bundleID, _wdaPort);
                ThreadContext.put("DEVICE_NAME", _deviceName);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private void androidDeviceSetup(String aDeviceName, String aPlatformName, String aPlatformVersion,
                                    String aAutomationName, String aServerUrl, String aUDID, String aAppPackage, String aAppActivity, String aWdaSysPort) {
        try {

            if(EnvironmentConstants.RUN_LOCALLY) {
                // Unlock Device If Locked
                new ADBHelper().unlockDevice(aUDID);

                String logDetails = "Device [" + aDeviceName + "] has Appium Server URL As : " + APPIUM_SERVER_URL;
                logger.info(logDetails);

                caps.setCapability(MobileCapabilityType.PLATFORM_NAME, aPlatformName);
                caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, aPlatformVersion);
                caps.setCapability(MobileCapabilityType.DEVICE_NAME, aDeviceName);
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, aAutomationName);
                caps.setCapability(MobileCapabilityType.UDID, aUDID);


                // Hide keyboard throughout the script execution
                caps.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
                caps.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);

                // When a find operation fails, print the current page source.
                caps.setCapability("appium:printPageSourceOnFindFailure", true);
            } else {
                // Setup Capabilities for pCloudy

                caps.setCapability("pCloudy_Username", EnvironmentConstants.P_CLOUDY_USERNAME);
                caps.setCapability("pCloudy_ApiKey", EnvironmentConstants.P_CLOUDY_ACCESS_KEY);

                //caps.setCapability("pCloudy_ApplicationName", EnvironmentConstants.P_CLOUDY_APP_NAME);

                caps.setCapability("pCloudy_DurationInMinutes", EnvironmentConstants.P_CLOUDY_RUN_DURATION_IN_MIN);

                // Set DeviceManufacturer Name. Uncomment this in case of you want to run it based on manufacturer
                // caps.setCapability("pCloudy_DeviceManafacturer", aDeviceName);

                // Set pCloudy Device Name & Device Platform Version
                caps.setCapability("pCloudy_DeviceFullName", aDeviceName);
                caps.setCapability("pCloudy_DeviceVersion", aPlatformVersion);

                caps.setCapability("pCloudy_WildNet", "false");

            }

            caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, aAppPackage);
            caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, aAppActivity);
            caps.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.parseInt(aWdaSysPort));

            // How long (in seconds) Appium will wait for a new command from the client,
            // before assuming the client quit and ending the session
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,600);

            // Do not stop app, do not clear app data, and do not uninstall apk.
            caps.setCapability(MobileCapabilityType.NO_RESET, false);

            // Timeout in milliseconds used to wait for the appWaitActivity to launch
            caps.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 300000);

            /* Timeout in milliseconds used to wait for an uiAutomator2 server to launch.
             * Defaults to 20000
             */
            if(aAutomationName.equalsIgnoreCase("UiAutomator2")) {
                caps.setCapability("appium:uiautomator2ServerLaunchTimeout", 300000);
            }


            /*
             * Doesn't stop the process of the app under test, before starting the app using adb.
             * If the app under test is created by another anchor app, setting this false,
             * allows the process of the anchor app to be still alive, during the start of the test app using adb.
             * In other words, with dontStopAppOnReset set to true,
             * we will not include the -S flag in the adb shell am start call.
             * With this capability omitted or set to false, we include the -S flag.
             * Default false
             */
            caps.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, true);

            //caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
            caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);


            caps.setCapability(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT,120000);

            // Start Android Driver instance
            if(EnvironmentConstants.RUN_LOCALLY) {
                ThreadLocalAppiumDriver.setDriver(new AndroidDriver<MobileElement>(new URL(aServerUrl), caps));
            } else {
                ThreadLocalAppiumDriver.setDriver(new AndroidDriver<MobileElement>(new URL(EnvironmentConstants.P_CLOUDY_APPIUM_SERVER_URL), caps));
            }

            ThreadLocalAppiumDriver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        } catch (Exception ex) {
            // add exception to list
            logger.error(ex.getMessage(), ex);
        }
    }

    private void iOsDeviceSetup(String iDeviceName, String iPlatformName, String iPlatformVersion, String iAutomationName,
                                String iServerUrl, String iUDID, String iBundleID, String iWdaPort) {
        try {

            logger.info("Device Name : " + iDeviceName + " has Appium Server URL As : " + APPIUM_SERVER_URL);

            caps.setCapability("platformName", iPlatformName);
            caps.setCapability("platformVersion", iPlatformVersion);
            caps.setCapability("deviceName", iDeviceName);
            caps.setCapability("udid", iUDID);
            caps.setCapability("automationName", iAutomationName);
            caps.setCapability("bundleId", iBundleID);
            caps.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, iWdaPort);
            // Do not destroy or shut down sim after test. Start tests running on whichever sim is running, or device is plugged in
            caps.setCapability("noReset", "true");
            // Hide keyboard throughout the script execution
            caps.setCapability("unicodeKeyboard", true);
            caps.setCapability("resetKeyboard", true);


            // Start IOS Driver Instance
            ThreadLocalAppiumDriver.setDriver(new IOSDriver<MobileElement>(new URL(iServerUrl), caps));
            ThreadLocalAppiumDriver.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            ThreadLocalAppiumDriver.getDriver().manage().timeouts().setScriptTimeout(300, TimeUnit.SECONDS);
            ThreadLocalAppiumDriver.getDriver().manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @AfterTest(alwaysRun = true)
    public void tearDownDevice() {
        try {
            // Hard wait for 5 second before closing the app
            Thread.sleep(5000);

            if(ThreadLocalAppiumDriver.getDriver() != null) {

                // Close App Under Test
                logger.info("Closing app under test...!!!");

                ThreadLocalAppiumDriver.getDriver().closeApp();

                // Quit Appium Driver session
                logger.info("Deleting Appium Driver Session...!!!");

                ThreadLocalAppiumDriver.getDriver().quit();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void stopAppiumServer() {
        if(EnvironmentConstants.RUN_LOCALLY) {
            try {

                Thread.sleep(5000);

                if(ThreadAppiumServer.getAppiumServerDriverService().isRunning()) {
                    // Close appium server
                    ThreadAppiumServer.getAppiumServerDriverService().stop();
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
