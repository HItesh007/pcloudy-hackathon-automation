package com.hackathon.pCloudy.helpers.appium;


import com.hackathon.pCloudy.enums.Month;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AndroidDatePickerHelper {

    private static final Logger logger = LogManager.getLogger(AndroidDatePickerHelper.class.getName());

    /**
     * To Select Date from Wheel Type Date Picker Of Android.
     * This method uses TouchAction to scroll forward or backward to select date.
     *
     * @param _driver AppiumDriver <p> Android Driver </p>
     * @param date    int <p> Date value in Integer </p>
     * @param month   Month <p> Month enum </p>
     * @param year    int <p> Year in Integer </p>
     */
    public static void selectDateFromWheelPicker(AppiumDriver<?> _driver, int date, Month month, int year) {
        WebDriverWait wait = new WebDriverWait(_driver, 60);

        // Update Following XPath values according to what instagram has
        String innerDatePickerElement = "android:id/numberpicker_input";
        String monthXPath = "//android.widget.NumberPicker[@index='0']";
        String dateXPath = "//android.widget.NumberPicker[@index='1']";
        String yearXPath = "//android.widget.NumberPicker[@index='2']";

        // Wait until date popup is available
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.instagram.android:id/birthday_date_picker")));

        // Select Month
        MobileElement monthWheelElement = (MobileElement) _driver.findElementByXPath(monthXPath);
        MobileElement dateWheelElement = (MobileElement) _driver.findElementByXPath(dateXPath);
        MobileElement yearWheelElement = (MobileElement) _driver.findElementByXPath(yearXPath);

        // Get Dimension
        int width = monthWheelElement.getSize().getWidth();
        int height = monthWheelElement.getSize().getHeight();

        // Get Co-Ordinates of all the wheels
        int topXMonth = monthWheelElement.getLocation().getX();
        int topYMonth = monthWheelElement.getLocation().getY();

        int startXMonth = topXMonth + (width / 2);
        int startYMonth = topYMonth + (height / 2);

        int endYMonth = (int) (topYMonth + (height * 0.90));
        int endYMonthNegative = (int) (topYMonth + (height * 0.10));

        logger.info("Month Co-Ordinates <Start> : (" + startXMonth + ", " + startYMonth + ")");
        logger.info("Month Co-Ordinates <End> : (" + startXMonth + ", " + endYMonth + ")");

        int topXDate = dateWheelElement.getLocation().getX();
        int topYDate = dateWheelElement.getLocation().getY();

        int startXDate = topXDate + (width / 2);
        int startYDate = topYDate + (height / 2);

        int endYDate = (int) (topYDate + (height * 0.90));
        int endYDateNegative = (int) (topYDate + (height * 0.10));

        logger.info("Date Co-Ordinates <Start> : (" + startXDate + ", " + startYDate + ")");
        logger.info("Date Co-Ordinates <End> : (" + startXDate + ", " + endYDate + ")");

        int topXYear = yearWheelElement.getLocation().getX();
        int topYYear = yearWheelElement.getLocation().getY();

        int startXYear = topXYear + (width / 2);
        int startYYear = topYYear + (height / 2);

        int endYYear = (int) (topYYear + (height * 0.90));
        int endYYearNegative = (int) (topYYear + (height * 0.10));

        logger.info("Year Co-Ordinates <Start> : (" + startXYear + ", " + startYYear + ")");
        logger.info("Year Co-Ordinates <End> : (" + startXYear + ", " + endYYear + ")");
        int currentYear = Integer.parseInt(yearWheelElement.findElementById(innerDatePickerElement).getText());
        int yearWheelEndCounter = currentYear - year;
        Boolean isYearDiffNegative = isNegative(yearWheelEndCounter);

        // Select Year
        for (int i = 0; i < Math.abs(yearWheelEndCounter); i++) {
            if (isYearDiffNegative) {
                new TouchAction(_driver)
                        .press(PointOption.point(startXYear, startYYear))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(startXYear, endYYearNegative))
                        .release()
                        .perform();
            } else {
                new TouchAction(_driver)
                        .press(PointOption.point(startXYear, startYYear))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(startXYear, endYYear))
                        .release()
                        .perform();
            }

            MobileElement yearLblElement = yearWheelElement.findElementById(innerDatePickerElement);

            // Get Current Month
            int currYear = Integer.parseInt(yearLblElement.getText());

            if (currYear == year) {
                logger.info("Selected Year : " + currYear);
                break;
            }

        }

        String currMonth = monthWheelElement.findElementById(innerDatePickerElement).getText().trim().toUpperCase();
        int monthWheelEndCounter = Month.valueOf(currMonth).getMonthIndex() - month.getMonthIndex();
        Boolean isNegativeMonth = isNegative(monthWheelEndCounter);

        // Select Month
        for (int i = 0; i < Math.abs(monthWheelEndCounter); i++) {

            // If the value is negative then move wheel to positive direction
            if (isNegativeMonth) {
                new TouchAction(_driver)
                        .press(PointOption.point(startXMonth, startYMonth))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(startXMonth, endYMonthNegative))
                        .release()
                        .perform();
            } else {
                new TouchAction(_driver)
                        .press(PointOption.point(startXMonth, startYMonth))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(startXMonth, endYMonth))
                        .release()
                        .perform();
            }
            MobileElement monthLblElement = monthWheelElement.findElementById(innerDatePickerElement);

            // Get Current Month
            String currentMonth = monthLblElement.getText().trim().toUpperCase();

            if (currentMonth.equals(month.toString().toUpperCase())) {
                logger.info("Selected Month : " + currentMonth);
                break;
            }
        }

        // Get Number of days based on month Parameter
        int numberOfDaysInAMonth = month.getNumberOfDays(year);
        int currentDate = Integer.parseInt(dateWheelElement.findElementById(innerDatePickerElement).getText());
        int wheelToMoveUntil = currentDate - date;
        Boolean isDateDiffNegative = isNegative(wheelToMoveUntil);

        // Select Date
        for (int i = 0; i < Math.abs(wheelToMoveUntil); i++) {

            // If the date difference is negative then move wheel in opposite direction
            if (isDateDiffNegative) {
                new TouchAction(_driver)
                        .press(PointOption.point(startXDate, startYDate))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(startXDate, endYDateNegative))
                        .release()
                        .perform();

            } else {
                new TouchAction(_driver)
                        .press(PointOption.point(startXDate, startYDate))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                        .moveTo(PointOption.point(startXDate, endYDate))
                        .release()
                        .perform();
            }
            MobileElement dateLblElement = dateWheelElement.findElementById(innerDatePickerElement);

            // Get Current Date
            int currDate = Integer.parseInt(dateLblElement.getText());

            if (currDate == date) {
                logger.info("Selected Date : " + currDate);
                break;
            }
        }
    }

    /**
     * To check wether the number is negative or not
     *
     * @param numberToCheck int <p> Integer value to check </p>
     * @return Boolean <p> Returns true if Negative, false otherwise </p>
     */
    private static Boolean isNegative(int numberToCheck) {
        Boolean flag = false;

        if (numberToCheck < 0) {
            // negative
            flag = true;
        }
        return flag;
    }

}
