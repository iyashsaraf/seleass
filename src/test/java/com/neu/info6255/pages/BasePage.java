package com.neu.info6255.pages;

import com.neu.info6255.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Reduced from 30
    }

    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    protected void jsClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
                element
        );
        WaitUtils.sleep(300); // Reduced from 500
    }

    protected void selectDropdown(By locator, String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
        dropdown.click();
        WaitUtils.sleep(300); // Reduced from 500

        By optionLocator = By.xpath("//option[text()='" + visibleText + "']");
        click(optionLocator);
    }

    protected void handleDuoAuth() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("⚠️  DUO AUTHENTICATION REQUIRED");
        System.out.println("=".repeat(60));
        System.out.println("Please approve Duo push notification");
        System.out.println("Waiting 25 seconds..."); // Reduced from 30
        System.out.println("=".repeat(60) + "\n");

        WaitUtils.sleep(25000); // Reduced from 30000
    }

    // Helper method to switch frames safely
    protected void switchToFrameSafely(int frameIndex) {
        try {
            wait.withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
        } catch (Exception e) {
            throw new RuntimeException("Could not switch to frame " + frameIndex, e);
        }
    }
}