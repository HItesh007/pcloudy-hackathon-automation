package com.hackathon.pCloudy.pages;

import com.hackathon.pCloudy.thread.ThreadLocalSEDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CPSatExamHomePage {
    private WebDriverWait wait;

    @FindBy(xpath = "//li[contains(@class,'slideout-toggle')]")
    private WebElement hamburgerIconElement;

    @FindBy(linkText = "Challenge 1")
    private WebElement challengeLinkElement;

    @FindBy(className = "eicon-close")
    private WebElement closeIconOnPopup;


    public CPSatExamHomePage() {
        PageFactory.initElements(ThreadLocalSEDriver.getDriver(), this);
        this.wait = new WebDriverWait(ThreadLocalSEDriver.getDriver(), 60);
    }

    private CPSatExamHomePage loadHomePage() {
        ThreadLocalSEDriver.getDriver().get("https://cpsatexam.org/");
        return this;
    }

    public String navigateAndConvertTo(String convertFrom) {
        String translatedText = null;
        try {

            loadHomePage();

            wait.until(ExpectedConditions.visibilityOf(hamburgerIconElement)).click();

            wait.until(ExpectedConditions.visibilityOf(challengeLinkElement)).click();

            // Wait until x is visible
            wait.until(ExpectedConditions.visibilityOf(closeIconOnPopup)).click();

            /// Get List of Element
            List<WebElement> accordianElements = ThreadLocalSEDriver.getDriver().findElements(By.className("eael-accordion-list"));

            for (WebElement element : accordianElements) {
                // get to the title
                // WebElement titleElement = element.findElement(By.xpath("//div[contains(@id,'elementor-tab-title-')]"));

                if (element.getText().trim().toUpperCase().contains(convertFrom.trim().toUpperCase())) {

                    // Click on title element
                    element.click();

                    String[] allContent = element.getText().split("\\n");

                    // Open Google Translate
                    ThreadLocalSEDriver.getDriver().get("https://translate.google.com/");

                    // Enter text into source
                    WebElement sourceElement = ThreadLocalSEDriver
                            .getDriver()
                            .findElement(By.id("source"));

                    sourceElement.clear();
                    sourceElement.sendKeys(allContent[1].trim());

                    Thread.sleep(2000);

                    // Wait until the translation is done
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'result-shield-container')]")));

                    // Get Text
                    translatedText = ThreadLocalSEDriver.getDriver().findElement(By.xpath("//div[contains(@class,'result-shield-container')]")).getText();

                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return translatedText;
    }
}
