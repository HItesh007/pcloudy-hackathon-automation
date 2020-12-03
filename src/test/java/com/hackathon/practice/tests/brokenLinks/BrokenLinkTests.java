package com.hackathon.practice.tests.brokenLinks;

import com.hackathon.pCloudy.base.SeleniumBase;
import com.hackathon.pCloudy.enums.Browser;
import com.hackathon.pCloudy.thread.ThreadLocalSEDriver;
import com.hackathon.practice.helper.BrokenLinkHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BrokenLinkTests {
    private static final Logger logger = LogManager.getLogger(BrokenLinkTests.class.getName());
    SeleniumBase seleniumBase = new SeleniumBase();

    @BeforeClass(alwaysRun = true)
    public void initializeApplication() {
        seleniumBase.initBrowser(Browser.Chrome);
    }

    @Test(priority = 0, description = "Check broken link using RestAssured API Library")
    public void checkBrokenLinksForToursPage() {
        logger.info("\n---------------------- [http://demo.guru99.com/test/newtours/] Broken Link Test Using RestAssured ----------------------");

        // Load URL
        seleniumBase.setUrl("http://demo.guru99.com/test/newtours/");

        // Get All Hyper Links
        List<WebElement> allLinkElements = ThreadLocalSEDriver
                .getDriver()
                .findElements(By.tagName("a"));

        logger.info("Total Links Detected On Page : " + allLinkElements.size());

        // Iterate Through Link Elements and check for it's working
        allLinkElements.forEach(linkElement -> {
            try {
                String link = linkElement.getAttribute("href");

                if (StringUtils.isBlank(link)) {
                    if (!StringUtils.isBlank(linkElement.getText())) {
                        logger.info("URL for link text [" + linkElement.getText() + "] is not yet set. Skipping this.");
                    }
                    throw new RuntimeException();
                }

                boolean isLinkBroken = BrokenLinkHelper.isLinkBroken(link);

                logger.info("[URL | IsBroken] - [" + link + " | " + isLinkBroken + "]");
            } catch (RuntimeException ignored) {
            }
        });
    }

    @Test(priority = 1, description = "Check Broken Links using HttpURLConnection & With Response Message")
    public void checkBrokenLinksByUsingHTTPAndResponseMessage() {
        logger.info("\n---------------------- [https://www.toolsqa.com/automation-practice-switch-windows/] Broken Link Test Using HttpURLConnection & Response Message ----------------------");

        // Load Application
        seleniumBase.setUrl("https://www.toolsqa.com/automation-practice-switch-windows/");

        // Get All Hyper Links
        List<WebElement> allLinkElements = ThreadLocalSEDriver
                .getDriver()
                .findElements(By.tagName("a"));

        logger.info("Total Links Detected On Page : " + allLinkElements.size());

        // Iterate Through Link Elements and check for it's working
        allLinkElements.forEach(linkElement -> {
            try {
                String link = linkElement.getAttribute("href");

                if (StringUtils.isBlank(link)) {
                    if (!StringUtils.isBlank(linkElement.getText())) {
                        logger.info("URL for link text [" + linkElement.getText() + "] is not yet set. Skipping this.");
                    }
                    throw new RuntimeException();
                }

                String isLinkBroken = null;
                try {
                    isLinkBroken = BrokenLinkHelper.isBrokenLink(new URL(link));
                } catch (MalformedURLException ignored) {
                }

                logger.info("URL : " + link + " has returned [" + isLinkBroken + "]");
            } catch (RuntimeException ignored) {
            }
        });
    }

    @Test(priority = 2, description = "Check Broken Links using HttpURLConnection & With Response Code")
    public void checkBrokenLinksByUsingHTTPAndResponseCode() {
        logger.info("\n---------------------- [https://gtr.agiletestingalliance.org/automatahon/] Broken Link Test Using HttpURLConnection & Response Code ----------------------");

        // Load Application
        seleniumBase.setUrl("https://gtr.agiletestingalliance.org/automatahon/");

        // Get All Hyper Links
        List<WebElement> allLinkElements = ThreadLocalSEDriver
                .getDriver()
                .findElements(By.tagName("a"));

        logger.info("Total Links Detected On Page : " + allLinkElements.size());

        // Iterate Through Link Elements and check for it's working
        allLinkElements.forEach(linkElement -> {
            try {
                String link = linkElement.getAttribute("href");

                if (StringUtils.isBlank(link)) {
                    if (!StringUtils.isBlank(linkElement.getText())) {
                        logger.info("URL for link text [" + linkElement.getText() + "] is not yet set. Skipping this.");
                    }
                    throw new RuntimeException();
                }

                boolean isLinkBroken = BrokenLinkHelper.isBrokenLink(link);

                logger.info("[URL | IsBroken] - [" + link + " | " + isLinkBroken + "]");
            } catch (RuntimeException ignored) {
            }
        });
    }

    @Test(priority = 3, description = "Check Broken Links using HttpURLConnection & With Response Code")
    public void checkIfUrlIsWorkingOrNot() {
        logger.info("\n---------------------- [https://www.pmcretail.com/] Broken Link Test Using HttpURLConnection & Response Code ----------------------");

        // Load Application
        seleniumBase.setUrl("https://www.pmcretail.com/");

        // Get All Hyper Links
        List<WebElement> allLinkElements = ThreadLocalSEDriver
                .getDriver()
                .findElements(By.tagName("a"));

        logger.info("Total Links Detected On Page : " + allLinkElements.size());

        // Iterate Through Link Elements and check for it's working
        allLinkElements.forEach(linkElement -> {
            try {
                String link = linkElement.getAttribute("href");

                if (StringUtils.isBlank(link)) {
                    if (!StringUtils.isBlank(linkElement.getText())) {
                        logger.info("URL for link text [" + linkElement.getText() + "] is not yet set. Skipping this.");
                    }
                    throw new RuntimeException();
                }

                boolean isLinkBroken = BrokenLinkHelper.isURLWorking(link);

                logger.info("[URL | IsURLWorking?] - [" + link + " | " + isLinkBroken + "]");
            } catch (RuntimeException ignored) {
            }
        });
    }

    @AfterClass(alwaysRun = true)
    public void tearDownApplication() {
        seleniumBase.closeBrowser();
    }
}
