package com.hackathon.pCloudy.helpers.appium;
import com.hackathon.pCloudy.thread.ThreadLocalAppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppiumSyncHelper {

    public AppiumSyncHelper() {
        // To Do
    }

    public void pauseScript(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (Exception ignored) {
        }
    }

    public void pauseScriptInMillis(long timeInMilliSeconds) {
        try {
            Thread.sleep(timeInMilliSeconds);
        } catch (Exception ignored) {
        }
    }

    public WebDriverWait getWebDriverWait(long timeInSeconds) {
        return new WebDriverWait(ThreadLocalAppiumDriver.getDriver(), timeInSeconds);
    }
}
