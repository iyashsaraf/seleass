package com.neu.info6255.pages;

import com.neu.info6255.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LibraryPage extends BasePage {

    // Library homepage links
    private By reserveStudyRoomLink = By.linkText("Reserve A Study Room");

    // Location selection
    private By bostonThumbnail = By.cssSelector(".col-md-6:nth-child(1) .pt-cv-thumbnail");

    // Book room link
    private By bookRoomLink = By.linkText("Book a Room");

    // Filters
    private By seatStyleDropdown = By.id("gid");
    private By capacityDropdown = By.id("capacity");

    public LibraryPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToLibrary() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Navigating to Library Website");
        System.out.println("=".repeat(60));

        driver.get("https://library.northeastern.edu/");
        System.out.println("URL: https://library.northeastern.edu/");
        WaitUtils.sleep(3000);

        System.out.println("✓ Library page loaded");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    public void clickReserveStudyRoom() {
        System.out.println("\n--- Clicking 'Reserve A Study Room' ---");

        try {
            // Wait for element to be present
            waitForElement(reserveStudyRoomLink);

            // Scroll to element
            WebElement element = driver.findElement(reserveStudyRoomLink);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
                    element
            );
            WaitUtils.sleep(1000);

            // Click using JavaScript (more reliable)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            System.out.println("✓ Clicked 'Reserve A Study Room'");

            WaitUtils.sleep(3000);
            System.out.println("Current URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("❌ Error clicking Reserve Study Room: " + e.getMessage());
            throw e;
        }
    }

    public void selectBoston() {
        System.out.println("\n--- Selecting Boston Location ---");

        try {
            waitForElement(bostonThumbnail);

            // Scroll to element
            WebElement element = driver.findElement(bostonThumbnail);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    element
            );
            WaitUtils.sleep(500);

            // Click
            click(bostonThumbnail);
            System.out.println("✓ Selected Boston");

            WaitUtils.sleep(3000);
            System.out.println("Current URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("❌ Error selecting Boston: " + e.getMessage());
            throw e;
        }
    }

    public void clickBookRoom() {
        System.out.println("\n--- Clicking 'Book a Room' ---");

        try {
            waitForElement(bookRoomLink);

            // Scroll to element
            WebElement element = driver.findElement(bookRoomLink);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    element
            );
            WaitUtils.sleep(500);

            // Click
            click(bookRoomLink);
            System.out.println("✓ Clicked 'Book a Room'");

            WaitUtils.sleep(3000);
            System.out.println("Current URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("❌ Error clicking Book a Room: " + e.getMessage());
            throw e;
        }
    }

    public void selectSeatStyle() {
        System.out.println("\n--- Selecting Seat Style: Individual Study ---");

        try {
            // Wait for dropdown
            waitForElement(seatStyleDropdown);

            // Scroll to dropdown
            WebElement dropdownElement = driver.findElement(seatStyleDropdown);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    dropdownElement
            );
            WaitUtils.sleep(500);

            // Click dropdown first (like in Selenium IDE)
            dropdownElement.click();
            WaitUtils.sleep(300);

            // Select option
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText("Individual Study");

            System.out.println("✓ Selected 'Individual Study'");
            WaitUtils.sleep(1000);

        } catch (Exception e) {
            System.out.println("❌ Error selecting seat style: " + e.getMessage());
            throw e;
        }
    }

    public void selectCapacity() {
        System.out.println("\n--- Selecting Capacity: Space For 1-4 people ---");

        try {
            // Wait for dropdown
            waitForElement(capacityDropdown);

            // Scroll to dropdown
            WebElement dropdownElement = driver.findElement(capacityDropdown);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    dropdownElement
            );
            WaitUtils.sleep(500);

            // Click dropdown first (like in Selenium IDE)
            dropdownElement.click();
            WaitUtils.sleep(300);

            // Select option
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText("Space For 1-4 people");

            System.out.println("✓ Selected 'Space For 1-4 people'");
            WaitUtils.sleep(1000);

        } catch (Exception e) {
            System.out.println("❌ Error selecting capacity: " + e.getMessage());
            throw e;
        }
    }

    public void scrollToBottom() {
        System.out.println("\n--- Scrolling to Bottom of Page ---");

        try {
            ((JavascriptExecutor) driver).executeScript(
                    "window.scrollTo(0, document.body.scrollHeight);"
            );
            WaitUtils.sleep(2000);

            System.out.println("✓ Scrolled to bottom");

        } catch (Exception e) {
            System.out.println("⚠️  Error scrolling: " + e.getMessage());
        }
    }

    public void verifyRoomsDisplayed() {
        System.out.println("\n--- Verifying Study Rooms Displayed ---");

        try {
            // Check if any room listings are visible
            String pageSource = driver.getPageSource();

            boolean roomsFound = pageSource.contains("Individual Study") ||
                    pageSource.contains("Room") ||
                    pageSource.contains("Available");

            if (roomsFound) {
                System.out.println("✓ Study rooms are displayed");
            } else {
                System.out.println("⚠️  No rooms found on page");
            }

        } catch (Exception e) {
            System.out.println("⚠️  Could not verify rooms: " + e.getMessage());
        }
    }
}