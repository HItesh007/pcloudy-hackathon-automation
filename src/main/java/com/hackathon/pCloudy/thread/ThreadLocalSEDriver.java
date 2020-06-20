package com.hackathon.pCloudy.thread;

import org.openqa.selenium.WebDriver;

public class ThreadLocalSEDriver {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        tlDriver.set(driver);
    }
}
