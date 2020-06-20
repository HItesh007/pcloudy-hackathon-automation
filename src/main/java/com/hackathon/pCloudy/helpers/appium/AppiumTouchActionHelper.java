package com.hackathon.pCloudy.helpers.appium;

import com.hackathon.pCloudy.thread.ThreadLocalAppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class AppiumTouchActionHelper {

    private static final Logger logger = LogManager.getLogger(AppiumTouchActionHelper.class.getName());

    public void verticalSwipe(SwipeDirection swipeDirection, long swipeDelayDurationInMillis) {
        try {
            AndroidDriver<?> driver = ThreadLocalAppiumDriver.getAndroidDriver();

            if (driver != null) {
                // Get Height And Width Of Device
                int height = driver.manage().window().getSize().getHeight();
                int width = driver.manage().window().getSize().getWidth();

                int xCord = driver.manage().window().getPosition().getX();
                int yCord = driver.manage().window().getPosition().getY();

                int topXStart = xCord + Math.round(width / 2);
                int topYStart = (int) (yCord + (height * 0.20));

                int topYEnd = (int) (yCord + (height * 0.80));

                TouchAction<?> touchAction = new TouchAction<>(driver);

                if (swipeDirection == SwipeDirection.UP) {

                    touchAction
                            .press(PointOption.point(topXStart, topYEnd))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                            .moveTo(PointOption.point(topXStart, topYStart))
                            .release()
                            .perform();

                } else if (swipeDirection == SwipeDirection.DOWN) {

                    touchAction
                            .press(PointOption.point(topXStart, topYStart))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                            .moveTo(PointOption.point(topXStart, topYEnd))
                            .release()
                            .perform();
                }
            } else {
                throw new NullPointerException("WebDriver Instance is null");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void verticalSwipe(MobileElement element, SwipeDirection swipeDirection, PressElement pressElementAt, long swipeDelayDurationInMillis) {
        try {
            AndroidDriver<?> driver = ThreadLocalAppiumDriver.getAndroidDriver();

            if (driver != null) {
                // Get Height And Width Of Device
                int deviceHeight = driver.manage().window().getSize().getHeight();
                int deviceWidth = driver.manage().window().getSize().getWidth();

                int deviceXCord = driver.manage().window().getPosition().getX();
                int deviceYCord = driver.manage().window().getPosition().getY();

                int eleHeight = element.getSize().getHeight();
                int eleWidth = element.getSize().getWidth();

                int eleXCord = element.getLocation().getX();
                int eleYCord = element.getLocation().getY();


                int topXStart = deviceXCord + deviceWidth / 2;
                int topYStart = (int) (deviceYCord + (deviceHeight * 0.20));
                int deviceYEnd = (int) (deviceYCord + deviceHeight * 0.80);

                int eleCentreYEnd = eleYCord + eleHeight / 2;
                int eleBottomYCord = eleYCord + eleHeight;

                TouchAction<?> touchAction = new TouchAction<>(driver);

                if (swipeDirection == SwipeDirection.UP) {

                    if (pressElementAt == PressElement.TOP) {
                        touchAction
                                .press(PointOption.point(topXStart, eleYCord))
                                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                                .moveTo(PointOption.point(topXStart, topYStart))
                                .release()
                                .perform();
                    } else if (pressElementAt == PressElement.CENTRE) {
                        touchAction
                                .press(PointOption.point(topXStart, eleCentreYEnd))
                                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                                .moveTo(PointOption.point(topXStart, topYStart))
                                .release()
                                .perform();
                    } else if (pressElementAt == PressElement.BOTTOM) {
                        touchAction
                                .press(PointOption.point(topXStart, eleBottomYCord))
                                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                                .moveTo(PointOption.point(topXStart, topYStart))
                                .release()
                                .perform();
                    }
                } else if (swipeDirection == SwipeDirection.DOWN) {
                    if (pressElementAt == PressElement.TOP) {
                        touchAction
                                .press(PointOption.point(topXStart, eleYCord))
                                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                                .moveTo(PointOption.point(topXStart, deviceYEnd))
                                .release()
                                .perform();
                    } else if (pressElementAt == PressElement.CENTRE) {
                        touchAction
                                .press(PointOption.point(topXStart, eleCentreYEnd))
                                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                                .moveTo(PointOption.point(topXStart, deviceYEnd))
                                .release()
                                .perform();
                    } else if (pressElementAt == PressElement.BOTTOM) {
                        touchAction
                                .press(PointOption.point(topXStart, eleBottomYCord))
                                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                                .moveTo(PointOption.point(topXStart, deviceYEnd))
                                .release()
                                .perform();
                    }
                }
            } else {
                throw new NullPointerException("WebDriver Instance is null");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void swipeHorizontal(SwipeDirection swipeDirection, long swipeDelayDurationInMillis) {
        try {
            AndroidDriver<?> driver = ThreadLocalAppiumDriver.getAndroidDriver();

            if (driver != null) {
                // Get Height And Width Of Device
                int height = driver.manage().window().getSize().getHeight();
                int width = driver.manage().window().getSize().getWidth();

                int xCord = driver.manage().window().getPosition().getX();
                int yCord = driver.manage().window().getPosition().getY();

                int lStartX = (int) (xCord + Math.round(width * 0.20));
                int constY = yCord + height / 2;

                int rEndX = (int) (xCord + (width * 0.80));

                TouchAction<?> touchAction = new TouchAction<>(driver);

                if (swipeDirection == SwipeDirection.RIGHT) {

                    touchAction
                            .press(PointOption.point(lStartX, constY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                            .moveTo(PointOption.point(rEndX, constY))
                            .release()
                            .perform();

                } else if (swipeDirection == SwipeDirection.LEFT) {

                    touchAction
                            .press(PointOption.point(rEndX, constY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDelayDurationInMillis)))
                            .moveTo(PointOption.point(lStartX, constY))
                            .release()
                            .perform();
                }
            } else {
                throw new NullPointerException("WebDriver Instance is null");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }


    public enum SwipeDirection {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    public enum PressElement {
        CENTRE,
        TOP,
        BOTTOM
    }
}
