package com.hackathon.pCloudy.thread;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class ThreadLocalAppiumDriver {
    private static ThreadLocalAppiumDriver instance = null;
    private static ThreadLocal<AppiumDriver> tlDriver = new ThreadLocal<AppiumDriver>();

    public static AppiumDriver<MobileElement> getDriver() {
        return tlDriver.get();
    }

    public static void setDriver(AppiumDriver<?> driver) {
        tlDriver.set(driver);
    }

    /**
     * To get Android Driver
     *
     * @return Returns object of Android Driver
     */
    public static AndroidDriver<?> getAndroidDriver() {
        return ((AndroidDriver<?>) tlDriver.get());
    }

    /**
     * To Get IOS Driver
     *
     * @return Returns object of IOS Driver
     */
    public static IOSDriver<?> getIOSDriver() {
        return ((IOSDriver<?>) tlDriver.get());
    }
}
