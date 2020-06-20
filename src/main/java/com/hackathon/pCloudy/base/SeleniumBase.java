package com.hackathon.pCloudy.base;

import com.hackathon.pCloudy.enums.Browser;
import com.hackathon.pCloudy.thread.ThreadLocalSEDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class SeleniumBase {
    private static final Logger logger = LogManager.getLogger(SeleniumBase.class.getName());


    public SeleniumBase() {
        // To Do
    }


    /**
     * To get Object of <code>WebDriver</code>
     * @return <code>WebDriver</code>
     */
    public WebDriver getDriver() {
        if(ThreadLocalSEDriver.getDriver() != null) {
            return ThreadLocalSEDriver.getDriver();
        } else {
            throw new NullPointerException("Driver session is not created. Please call initBrowser() before calling getDriver()");
        }
    }

    /**
     * To set Implicit wait for <code>WebDriver</code> instance.
     *
     * @param time Time value in <code>long</code>
     * @param timeUnit <code>TimeUnit</code>
     * @return <code>DriverFactory</code>
     */
    public SeleniumBase setImplicitTimeoutAs(long time, TimeUnit timeUnit) {
        if(ThreadLocalSEDriver.getDriver() != null) {
            ThreadLocalSEDriver.getDriver().manage().timeouts().implicitlyWait(time, timeUnit);
        } else {
            throw new NullPointerException("WebDriver instance is not created. Please call initBrowser() before setImplicitTimeout method");
        }
        return this;
    }

    /**
     * To set Asynchronous script to time out after specified time
     *
     * @param time Timeout value in <code>long</code>
     * @param timeUnit <code>TimeUnit</code> in Seconds, Milliseconds, minutes, hours etc.
     * @return <code>DriverFactory</code>
     */
    public SeleniumBase setScriptTimeoutAs(long time, TimeUnit timeUnit) {
        if(ThreadLocalSEDriver.getDriver() != null) {
            ThreadLocalSEDriver.getDriver().manage().timeouts().setScriptTimeout(time, timeUnit);
        } else {
            throw new NullPointerException("WebDriver instance is not created. Please call initBrowser() before setScriptTimeout method");
        }
        return this;
    }

    /**
     * To set page load timeout
     *
     * @param time Timeout value in <code>long</code>
     * @param timeUnit <code>TimeUnit</code> in Seconds, Milliseconds, minutes, hours etc.
     * @return <code>DriverFactory</code>
     */
    public SeleniumBase setPageLoadTimeoutAs(long time, TimeUnit timeUnit) {
        if(ThreadLocalSEDriver.getDriver() != null) {
            ThreadLocalSEDriver.getDriver().manage().timeouts().pageLoadTimeout(time, timeUnit);
        } else {
            throw new NullPointerException("WebDriver instance is not created. Please call initBrowser() before setPageLoadTimeout method");
        }
        return this;
    }

    /**
     * To download binary for the browser specified as parameter
     * and create instance of it
     *
     * @param browser Browser to initialize of type <code>Browser</code>
     * @return <code>DriverFactory</code>
     */
    public SeleniumBase initBrowser(Browser browser){
        switch (browser) {
            case IE:
                WebDriverManager.iedriver().setup();
                break;
            case Edge:
                WebDriverManager.edgedriver().setup();
                break;
            case Firefox:
                WebDriverManager.firefoxdriver().setup();
                ThreadLocalSEDriver
                        .setDriver(new FirefoxDriver());
                break;
            default:
                WebDriverManager.chromedriver().setup();
                ThreadLocalSEDriver
                        .setDriver(new ChromeDriver());
                break;
        }

        // Maximize browser
        ThreadLocalSEDriver.getDriver().manage().window().maximize();
        return this;
    }


    /**
     * To set the Web Url.
     *
     * @param webUrl Web Url of Application
     */
    public void setUrl(String webUrl) {
        ThreadLocalSEDriver.getDriver().get(webUrl);
    }

    /**
     * To close the browser and clean the driver service.
     *
     */
    public void closeBrowser() {
        try {
            if(ThreadLocalSEDriver.getDriver() != null) {
                ThreadLocalSEDriver
                        .getDriver()
                        .close();

                if(ThreadLocalSEDriver.getDriver() instanceof ChromeDriver) {
                    ThreadLocalSEDriver
                            .getDriver()
                            .quit();
                }
            }
        } catch (WebDriverException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
