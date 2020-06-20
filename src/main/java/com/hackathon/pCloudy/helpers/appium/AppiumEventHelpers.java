package com.hackathon.pCloudy.helpers.appium;

import com.hackathon.pCloudy.thread.ThreadLocalAppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AppiumEventHelpers {
    private static ThreadLocal<AppiumEventHelpers> instance = new ThreadLocal<>();

    private static final String actionBlockedId = "com.instagram.android:id/default_dialog_title";
    private static final String okButtonOnBlockedId = "com.instagram.android:id/negative_button";
    private static final String actionBlockedText = "Action Blocked";

    public AppiumEventHelpers() {
        // To Do
    }

    public static AppiumEventHelpers getInstance() {
        if(instance.get() == null) {
            instance.set(new AppiumEventHelpers());
        }
        return instance.get();
    }

    public void setImplicitTimeout(long timeOutInMilliseconds) {
        ThreadLocalAppiumDriver
                .getDriver()
                .manage()
                .timeouts()
                .implicitlyWait(timeOutInMilliseconds, TimeUnit.SECONDS);
    }

    public void click(MobileElement element) {

        new AppiumSyncHelper().getWebDriverWait(60)
                .until(ExpectedConditions.visibilityOf(element)).click();
    }

    public void waitAndClickWhenEnabled(MobileElement element) {

        new AppiumSyncHelper().getWebDriverWait(60)
                .until(ExpectedConditions.attributeToBe(element, "enabled", "true"));
        element.click();
    }

    public void performKeyBoardEvent(KeyBoardEvent keyBoardEvent) {

        switch (keyBoardEvent) {
                case ENTER:
                    ThreadLocalAppiumDriver.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.ENTER));
                    break;

                case LEFT_SHIFT:
                    ThreadLocalAppiumDriver.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.SHIFT_LEFT));
                    break;

                case RIGHT_SHIFT:
                    ThreadLocalAppiumDriver.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.SHIFT_RIGHT));
                    break;

                case LEFT_CTRL:
                    ThreadLocalAppiumDriver.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.CTRL_LEFT));
                    break;

                case RIGHT_CTRL:
                    ThreadLocalAppiumDriver.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.CTRL_RIGHT));
                    break;
        }

    }

    public void clear(MobileElement element) {
        WebDriverWait wait = new AppiumSyncHelper().getWebDriverWait(60);
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
    }

    public void sendKeys(MobileElement element, String textToEnter) {
        WebDriverWait wait = new AppiumSyncHelper().getWebDriverWait(60);
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(textToEnter);
    }

    public String getText(MobileElement element) {

        return new AppiumSyncHelper().getWebDriverWait(60)
                .until(ExpectedConditions.visibilityOf(element)).getText();

    }

    public enum KeyBoardEvent {
        SEARCH,
        ENTER,
        LEFT_SHIFT,
        RIGHT_SHIFT,
        LEFT_CTRL,
        RIGHT_CTRL
    }

    public boolean isSessionActive() {
        try {
            String sessionID = ThreadLocalAppiumDriver.getDriver().getSessionId().toString();
            if(sessionID.length() > 0) {
                return true;
            }
        } catch (WebDriverException ex) {
            return false;
        }
        return false;
    }
}
