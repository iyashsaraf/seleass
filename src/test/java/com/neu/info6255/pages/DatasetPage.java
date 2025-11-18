package com.neu.info6255.pages;

import com.neu.info6255.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.List;

public class DatasetPage extends BasePage {

    // OneSearch page
    private By digitalRepositoryLink = By.cssSelector("div:nth-child(5) .item-content");

    // DRS (Digital Repository Service) page
    private By datasetsLink = By.linkText("Datasets");

    // Dataset detail page
    private By firstDatasetLink = By.linkText("3rd metacarpal");
    private By zipFileLink = By.linkText("Zip File");

    // Expected error indicators
    private By errorMessage = By.cssSelector(".error, .alert-danger, .message");
    private By accessDenied = By.xpath("//*[contains(text(), 'Access Denied') or contains(text(), 'Not Available')]");

    public DatasetPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToOneSearch() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Navigating to OneSearch");
        System.out.println("=".repeat(60));

        driver.get("https://onesearch.library.northeastern.edu/discovery/search?vid=01NEU_INST:NU&lang=en");
        System.out.println("URL: " + driver.getCurrentUrl());
        WaitUtils.sleep(3000);

        System.out.println("✓ OneSearch page loaded");
    }

    public void clickDigitalRepository() {
        System.out.println("\n--- Clicking Digital Repository Service ---");

        try {
            String mainWindow = driver.getWindowHandle();

            // Wait and click
            waitForElement(digitalRepositoryLink);
            click(digitalRepositoryLink);
            System.out.println("✓ Clicked Digital Repository link");

            WaitUtils.sleep(3000);

            // Switch to new window
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    System.out.println("✓ Switched to Digital Repository window");
                    System.out.println("Current URL: " + driver.getCurrentUrl());
                    break;
                }
            }

            WaitUtils.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Error clicking Digital Repository: " + e.getMessage());
            throw e;
        }
    }

    public void clickDatasets() {
        System.out.println("\n--- Clicking Datasets Link ---");

        try {
            // Hover over Datasets link (like in Selenium IDE)
            waitForElement(datasetsLink);
            WebElement datasetsElement = driver.findElement(datasetsLink);

            Actions actions = new Actions(driver);
            actions.moveToElement(datasetsElement).perform();
            WaitUtils.sleep(500);

            // Click
            datasetsElement.click();
            System.out.println("✓ Clicked Datasets");

            WaitUtils.sleep(3000);
            System.out.println("Current URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("❌ Error clicking Datasets: " + e.getMessage());
            throw e;
        }
    }

    public void openFirstDataset() {
        System.out.println("\n--- Opening Dataset: 3rd metacarpal ---");

        try {
            waitForElement(firstDatasetLink);
            click(firstDatasetLink);
            System.out.println("✓ Opened dataset");

            WaitUtils.sleep(3000);
            System.out.println("Current URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("❌ Error opening dataset: " + e.getMessage());
            throw e;
        }
    }

    public void clickZipFile() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Attempting to Download Zip File");
        System.out.println("=".repeat(60));

        try {
            String mainWindow = driver.getWindowHandle();

            // Try to click Zip File link
            waitForElement(zipFileLink);

            // Hover over it first (like in Selenium IDE)
            WebElement zipElement = driver.findElement(zipFileLink);
            Actions actions = new Actions(driver);
            actions.moveToElement(zipElement).perform();
            WaitUtils.sleep(500);

            System.out.println("Found 'Zip File' link - attempting click...");

            // Click Zip File
            zipElement.click();
            System.out.println("Clicked 'Zip File' link");

            WaitUtils.sleep(3000);

            // Check if new window opened (indicating download started)
            if (driver.getWindowHandles().size() > 1) {
                // Switch to new window
                for (String handle : driver.getWindowHandles()) {
                    if (!handle.equals(mainWindow)) {
                        driver.switchTo().window(handle);
                        System.out.println("Switched to download window");
                        break;
                    }
                }

                WaitUtils.sleep(2000);

                // Check current URL or page content for errors
                String currentUrl = driver.getCurrentUrl();
                String pageSource = driver.getPageSource();

                System.out.println("Download window URL: " + currentUrl);

                // Check for various error conditions
                if (pageSource.contains("Access Denied") ||
                        pageSource.contains("Not Found") ||
                        pageSource.contains("Error") ||
                        pageSource.contains("403") ||
                        pageSource.contains("404") ||
                        pageSource.contains("Unauthorized") ||
                        currentUrl.contains("error")) {

                    System.out.println("❌ Download failed - Error detected on page");
                    driver.close();
                    driver.switchTo().window(mainWindow);
                    throw new RuntimeException("NEGATIVE TEST PASSED: Dataset download failed with access error");
                }

                // If no error found, but file actually downloaded
                System.out.println("⚠️  Download window opened successfully");

                // Close download window
                driver.close();
                driver.switchTo().window(mainWindow);

                // THIS IS THE FAILURE - download should not succeed
                System.out.println("❌ NEGATIVE TEST FAILED: Download succeeded when it should have failed!");
                throw new AssertionError("NEGATIVE TEST FAILED: Dataset download should not be accessible but it succeeded!");

            } else {
                // No new window - check for error message on same page
                String pageSource = driver.getPageSource();

                if (pageSource.contains("sign in") ||
                        pageSource.contains("login") ||
                        pageSource.contains("authenticate")) {

                    System.out.println("✓ NEGATIVE TEST PASSED: Authentication required");
                    throw new RuntimeException("NEGATIVE TEST PASSED: Dataset download requires authentication");
                }

                // If we get here, something unexpected happened
                System.out.println("⚠️  No new window opened and no error found");
                throw new RuntimeException("NEGATIVE TEST INCONCLUSIVE: Unexpected behavior");
            }

        } catch (org.openqa.selenium.TimeoutException e) {
            // Zip File link not found
            System.out.println("✓ NEGATIVE TEST PASSED: Zip File link not accessible");
            throw new RuntimeException("NEGATIVE TEST PASSED: Zip File link not found - " + e.getMessage());

        } catch (RuntimeException | AssertionError e) {
            // Re-throw our expected failures
            throw e;

        } catch (Exception e) {
            // Unexpected error
            System.out.println("❌ Unexpected error: " + e.getMessage());
            throw new RuntimeException("NEGATIVE TEST ERROR: " + e.getMessage(), e);
        }
    }

    public void verifyDownloadFailed() {
        System.out.println("\n--- Verifying Download Failed ---");

        // Check if we're still on the dataset page (download didn't happen)
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("drs") || currentUrl.contains("dataset")) {
            System.out.println("✓ Still on dataset page - download did not occur");
        } else {
            System.out.println("⚠️  URL changed: " + currentUrl);
        }
    }
}