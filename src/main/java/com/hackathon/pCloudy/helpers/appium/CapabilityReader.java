package com.hackathon.pCloudy.helpers.appium;


import com.hackathon.pCloudy.thread.ThreadLocalAppiumDriver;

public class CapabilityReader {

    private static ThreadLocal<CapabilityReader> instance = new ThreadLocal<>();

    private CapabilityReader() {
        // To Do
    }

    public static CapabilityReader getInstance() {
        if (instance.get() == null) {
            instance.set(new CapabilityReader());
        }
        return instance.get();
    }

    public String getDeviceModel() {
        return ThreadLocalAppiumDriver
                .getDriver()
                .getCapabilities()
                .getCapability("deviceModel")
                .toString();
    }

    public String getDeviceUDID() {
        return ThreadLocalAppiumDriver
                .getDriver()
                .getCapabilities()
                .getCapability("udid")
                .toString();
    }

    public String getPlatformVersion() {
        return ThreadLocalAppiumDriver
                .getDriver()
                .getCapabilities()
                .getCapability("platformVersion")
                .toString();
    }

    /**
     * To get full device name
     *
     * @return Full Device Name in format DEVICE_MODEL (UDID)
     */
    public String getFullDeviceName() {
        String deviceName = getDeviceModel() + " (" + getDeviceUDID() + ")";
        return deviceName;
    }
}
