package com.hackathon.pCloudy.helpers.appium;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ElementHelper {

    private static ThreadLocal<ElementHelper> instance = new ThreadLocal<>();

    public static ElementHelper getInstance() {

        if(instance.get() == null) {
            instance.set(new ElementHelper());
        }
        return instance.get();
    }

    public boolean isElementPresent(AppiumDriver<?> _driver, By by) {
        _driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        boolean isPresent = false;
        try {
            isPresent = _driver.findElements(by).size() > 0;
            _driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return isPresent;
        } catch (NoSuchElementException e) {
            _driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return isPresent;
        }
    }
}
