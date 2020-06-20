package com.hackathon.pCloudy.utils;

import com.hackathon.pCloudy.enums.DeviceProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * DeviceConfiguration - this class contains methods to start adb server, to get connected devices and their information.
 */
public class DeviceConfiguration {

    CommandPrompt cmd = new CommandPrompt();
    LinkedHashMap<String,Map<String, String>> devices = new LinkedHashMap<>();
    private static final Logger logger = LogManager.getLogger(DeviceConfiguration.class.getName());

    /**
     * This method start adb server
     */
    private void startADB() {
        String output = cmd.runCommand("adb start-server");
        String[] lines = output.split("\n");
        if(lines.length==1)
            logger.info("adb service already started");
        else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
            logger.info("adb service started");
        else if(lines[0].contains("internal or external command")){
            logger.info("adb path not set in system variable");
            System.exit(0);
        }
    }

    /**
     * This method stop adb server
     */
    public void stopADB() {
        cmd.runCommand("adb kill-server");
    }

    /**
     * This method return connected devices
     * @return hashmap of connected devices information
     */
    public LinkedHashMap<String,Map<String, String>> getDevices() {

        try {
            startADB(); // start adb service
            String output = cmd.runCommand("adb devices");
            String[] lines = output.split("\n");

            if(lines.length<=1){
                logger.info("No Device Connected");
                //stopADB();
                System.exit(0);	// exit if no connected devices found
            }

            for(int i=1;i<lines.length;i++){
                lines[i]=lines[i].replaceAll("\\s+", "");

                if(lines[i].contains("device")){
                    lines[i]=lines[i].replaceAll("device", "");
                    String deviceID = lines[i];
                    String deviceModel = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").trim();
                    //String brand = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
                    String osVersion = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");

                    Map<String, String> deviceMap = new LinkedHashMap<>();
                    deviceMap.put(DeviceProperty.DEVICE_ID.toString(), deviceID);
                    deviceMap.put(DeviceProperty.DEVICE_MODEL.toString(), deviceModel);
                    deviceMap.put(DeviceProperty.PLATFORM_VERSION.toString(), osVersion);

                    devices.put("DEVICE_" + i, deviceMap);
                    /*devices.add(Map.of("DEVICE_ID", deviceID));
                    devices.add(Map.of("DEVICE_NAME", deviceModel));
                    devices.add(Map.of("PLATFORM_VERSION", osVersion));*/
                    /*
                    devices.put("DEVICE_ID"+i, deviceID);
                    devices.put("DEVICE_NAME"+i, deviceModel);
                    devices.put("PLATFORM_VERSION"+i, osVersion);*/

                    logger.info("Following device is connected");
                    logger.info(deviceID+" "+deviceModel+" "+osVersion+"\n");
                }else if(lines[i].contains("unauthorized")){
                    lines[i]=lines[i].replaceAll("unauthorized", "");
                    String deviceID = lines[i];

                    logger.warn("Following device is unauthorized");
                    logger.warn(deviceID+"\n");
                }else if(lines[i].contains("offline")){
                    lines[i]=lines[i].replaceAll("offline", "");
                    String deviceID = lines[i];

                    logger.error("Following device is offline");
                    logger.error(deviceID+"\n");
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return devices;
    }

    public List<String> getAllDeviceUDID() {
        List<String> deviceIDs = new ArrayList<>();

        getDevices().values().forEach(deviceMap -> {
            deviceIDs.add(deviceMap.get(DeviceProperty.DEVICE_ID.toString()));
        });
        return deviceIDs;
    }
}