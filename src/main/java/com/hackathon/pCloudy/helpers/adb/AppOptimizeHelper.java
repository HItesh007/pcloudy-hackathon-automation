package com.hackathon.pCloudy.helpers.adb;

import com.hackathon.pCloudy.utils.CommandPrompt;
import com.hackathon.pCloudy.utils.DeviceConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AppOptimizeHelper {
    private static final Logger logger = LogManager.getLogger(AppOptimizeHelper.class.getName());

    public void optimizeAppsForAllDevices(List<String> packagesToOptimize) {
        DeviceConfiguration dConf = new DeviceConfiguration();
        CommandPrompt cmd = new CommandPrompt();

        dConf.getAllDeviceUDID().forEach(deviceId -> {
            packagesToOptimize.forEach(appPackage -> {
                logger.info(cmd.runCommand("adb -s " + deviceId.trim() +" shell dumpsys deviceidle whitelist -" + appPackage));
                logger.info( deviceId + " [" +appPackage + "] added for optimization");
            });
        });
    }

    public void disableAppOptimizationForAllDevices(List<String> packagesToOptimize) {
        DeviceConfiguration dConf = new DeviceConfiguration();
        CommandPrompt cmd = new CommandPrompt();

        dConf.getAllDeviceUDID().forEach(deviceId -> {
            packagesToOptimize.forEach(appPackage -> {
                logger.info(cmd.runCommand("adb -s " + deviceId.trim() +" shell dumpsys deviceidle whitelist +" + appPackage));
                logger.info( deviceId + " [" +appPackage + "] removed from optimization");
            });
        });
    }

    public void disableAppOptimizationForBattery(List<String> packagesToOptimize, String deviceUdid) {
        CommandPrompt cmd = new CommandPrompt();

        packagesToOptimize.forEach(appPackage -> {
            String cmdOutput = cmd.runCommand("adb -s " + deviceUdid.trim() +" shell dumpsys deviceidle whitelist +" + appPackage);
            String logMessage = "ADB Output : " + cmdOutput + ". [" + appPackage + "] removed from optimization.";

            logger.info(logMessage);
        });
    }

    public void enableAppOptimizationForBattery(List<String> packagesToOptimize, String deviceUdid) {
        CommandPrompt cmd = new CommandPrompt();

        packagesToOptimize.forEach(appPackage -> {
            String cmdOutput = cmd.runCommand("adb -s " + deviceUdid.trim() +" shell dumpsys deviceidle whitelist -" + appPackage);
            String logMessage = "ADB Output : " + cmdOutput + ". [" + appPackage + "] added for optimization for device";

            logger.info(logMessage);
        });
    }
}
