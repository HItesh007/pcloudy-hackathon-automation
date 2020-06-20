package com.hackathon.pCloudy.tests.challengeFive;

import com.hackathon.pCloudy.thread.ThreadLocalSEDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class ParallelSetup {

    @BeforeTest(alwaysRun = true)
    @Parameters({"browser-name"})
    public void initBrowsers(String browserName) {

            switch (browserName.trim().toUpperCase()) {
                case "FIREFOX":
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
    }

    @AfterTest
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
            ex.printStackTrace();
        }
    }
}
