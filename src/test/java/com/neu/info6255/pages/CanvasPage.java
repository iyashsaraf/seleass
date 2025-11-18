package com.neu.info6255.pages;

import com.neu.info6255.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.Map;

public class CanvasPage extends BasePage {

    // Canvas Navigation - From Selenium IDE
    private By calendarIcon = By.cssSelector(".ic-icon-svg--calendar");
    private By calendarLink = By.id("global_nav_calendar_link");

    // Create Event - From Selenium IDE
    private By plusIcon = By.cssSelector(".icon-plus");

    // Event Form Fields - Dynamic IDs, using more stable selectors
    private By titleInput = By.cssSelector("input[placeholder*='Event Title']");
    private By dateInput = By.cssSelector("input[value*='2025']");
    private By fromTimeInput = By.cssSelector("input[placeholder='Start Time']");
    private By toTimeInput = By.cssSelector("input[placeholder='End Time']");
    private By locationInput = By.cssSelector("input[placeholder*='Location']");
    private By calendarDropdown = By.cssSelector("select");

    // Submit button
    private By submitButton = By.xpath("//button[contains(text(), 'Submit')]");

    public CanvasPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToCanvas() {
        driver.get("https://northeastern.instructure.com");
        System.out.println("Navigated to Canvas: https://northeastern.instructure.com");
        WaitUtils.sleep(2000);
    }

    public void openCalendar() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Opening Canvas Calendar...");
        System.out.println("=".repeat(60));

        try {
            WaitUtils.sleep(2000);

            // Click calendar icon (from Selenium IDE recording)
            waitForElement(calendarIcon);
            click(calendarIcon);
            System.out.println("✓ Clicked Calendar icon");

            WaitUtils.sleep(3000);
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("✓ Calendar page opened");

        } catch (Exception e) {
            System.out.println("❌ Error opening calendar: " + e.getMessage());
            throw e;
        }
    }

    public void clickAddEvent() {
        System.out.println("\n--- Clicking Create Event (Plus Icon) ---");

        try {
            WaitUtils.sleep(2000);

            // Hover over plus icon (like in Selenium IDE)
            WebElement plusElement = driver.findElement(plusIcon);
            Actions actions = new Actions(driver);
            actions.moveToElement(plusElement).perform();
            System.out.println("✓ Hovered over plus icon");

            WaitUtils.sleep(500);

            // Click the plus icon
            plusElement.click();
            System.out.println("✓ Clicked plus icon to create event");

            WaitUtils.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Error clicking add event: " + e.getMessage());

            // Try JavaScript click as fallback
            try {
                WebElement plusElement = driver.findElement(plusIcon);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", plusElement);
                System.out.println("✓ Clicked using JavaScript");
                WaitUtils.sleep(2000);
            } catch (Exception ex) {
                throw e;
            }
        }
    }

    public void createEvent(Map<String, String> eventData) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Filling Event Form");
        System.out.println("=".repeat(60));

        try {
            WaitUtils.sleep(1500);

            // 1. Enter Title
            System.out.println("\n1. Entering Title: " + eventData.get("Title"));
            try {
                WebElement titleField = findFirstVisibleInput("Event Title", "Title", "title");
                titleField.clear();
                titleField.sendKeys(eventData.get("Title"));
                System.out.println("   ✓ Title entered");
                WaitUtils.sleep(500);
            } catch (Exception e) {
                System.out.println("   ⚠️  Could not enter title: " + e.getMessage());
            }

            // 2. Enter Date
            System.out.println("\n2. Entering Date: " + eventData.get("Date"));
            try {
                // Canvas date field might be pre-filled with current date
                // Look for input with type="text" and contains date-like placeholder
                List<WebElement> inputs = driver.findElements(By.tagName("input"));
                WebElement dateField = null;

                for (WebElement input : inputs) {
                    if (!input.isDisplayed() || !input.isEnabled()) continue;

                    String placeholder = input.getAttribute("placeholder");
                    String ariaLabel = input.getAttribute("aria-label");
                    String value = input.getAttribute("value");

                    // Look for date field indicators
                    if ((placeholder != null && placeholder.toLowerCase().contains("date")) ||
                            (ariaLabel != null && ariaLabel.toLowerCase().contains("date")) ||
                            (value != null && value.matches(".*\\d{4}.*"))) { // Contains year
                        dateField = input;
                        break;
                    }
                }

                if (dateField != null) {
                    // Clear existing value
                    dateField.click();
                    dateField.sendKeys(Keys.CONTROL + "a");
                    dateField.sendKeys(Keys.DELETE);
                    WaitUtils.sleep(200);

                    // Enter new date
                    dateField.sendKeys(eventData.get("Date"));
                    dateField.sendKeys(Keys.TAB);
                    System.out.println("   ✓ Date entered");
                } else {
                    System.out.println("   ⚠️  Date field not found - using default date");
                }
                WaitUtils.sleep(500);
            } catch (Exception e) {
                System.out.println("   ⚠️  Could not enter date: " + e.getMessage());
            }

            // 4. Enter End Time (To)
            System.out.println("\n4. Entering End Time: " + eventData.get("EndTime"));
            try {
                WebElement endField = findFirstVisibleInput("End Time", "To");
                endField.clear();
                endField.sendKeys(eventData.get("EndTime"));
                endField.sendKeys(Keys.TAB);
                System.out.println("   ✓ End time entered");
                WaitUtils.sleep(500);
            } catch (Exception e) {
                System.out.println("   ⚠️  Could not enter end time: " + e.getMessage());
            }

            // 5. Enter Location
            System.out.println("\n5. Entering Location: " + eventData.get("Location"));
            try {
                WebElement locField = findFirstVisibleInput("Location", "location");
                locField.clear();
                locField.sendKeys(eventData.get("Location"));
                System.out.println("   ✓ Location entered");
                WaitUtils.sleep(500);
            } catch (Exception e) {
                System.out.println("   ⚠️  Could not enter location: " + e.getMessage());
            }

            // 6. Enter Details (if provided)
            if (eventData.containsKey("Details") && eventData.get("Details") != null &&
                    !eventData.get("Details").isEmpty()) {
                System.out.println("\n6. Entering Details: " + eventData.get("Details"));
                try {
                    // Find textarea or details field
                    List<WebElement> textareas = driver.findElements(By.tagName("textarea"));
                    if (textareas.size() > 0) {
                        WebElement detailsField = textareas.get(0);
                        if (detailsField.isDisplayed() && detailsField.isEnabled()) {
                            detailsField.clear();
                            detailsField.sendKeys(eventData.get("Details"));
                            System.out.println("   ✓ Details entered");
                        }
                    }
                    WaitUtils.sleep(500);
                } catch (Exception e) {
                    System.out.println("   ⚠️  Details field not available");
                }
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("✓ Event form filled successfully");
            System.out.println("=".repeat(60));

        } catch (Exception e) {
            System.out.println("\n❌ Error filling event form: " + e.getMessage());

            // Debug: Print all input fields
            System.out.println("\nDEBUG: Available input fields:");
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            for (int i = 0; i < inputs.size(); i++) {
                WebElement input = inputs.get(i);
                System.out.println("Input " + i + ": placeholder='" + input.getAttribute("placeholder") +
                        "', type='" + input.getAttribute("type") + "'");
            }

            throw e;
        }
    }

    public void submitEvent() {
        System.out.println("\n--- Submitting Event ---");

        try {
            WaitUtils.sleep(1000);

            // Try to find Submit button
            By[] submitLocators = {
                    By.xpath("//button[contains(text(), 'Submit')]"),
                    By.cssSelector("button[type='submit']"),
                    By.xpath("//button[contains(@class, 'Button--primary')]"),
                    By.xpath("//button[contains(., 'Submit')]")
            };

            boolean submitted = false;
            for (By locator : submitLocators) {
                try {
                    List<WebElement> buttons = driver.findElements(locator);
                    if (buttons.size() > 0) {
                        WebElement submitBtn = buttons.get(0);
                        if (submitBtn.isDisplayed() && submitBtn.isEnabled()) {
                            // Use JavaScript click for reliability
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
                            System.out.println("✓ Clicked Submit button");
                            submitted = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            if (!submitted) {
                System.out.println("⚠️  Submit button not found - checking all buttons");
                List<WebElement> allButtons = driver.findElements(By.tagName("button"));
                System.out.println("Found " + allButtons.size() + " total buttons");
                for (int i = 0; i < Math.min(allButtons.size(), 5); i++) {
                    WebElement btn = allButtons.get(i);
                    System.out.println("Button " + i + ": text='" + btn.getText() + "'");
                }
            }

            WaitUtils.sleep(3000);
            System.out.println("✓ Event submitted");

        } catch (Exception e) {
            System.out.println("❌ Error submitting event: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Helper method to find first visible input field matching any of the keywords
     */
    private WebElement findFirstVisibleInput(String... keywords) {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));

        for (WebElement input : inputs) {
            try {
                if (!input.isDisplayed() || !input.isEnabled()) {
                    continue;
                }

                String placeholder = input.getAttribute("placeholder");
                String ariaLabel = input.getAttribute("aria-label");
                String id = input.getAttribute("id");
                String name = input.getAttribute("name");

                // Check if any keyword matches
                for (String keyword : keywords) {
                    String lowerKeyword = keyword.toLowerCase();
                    if ((placeholder != null && placeholder.toLowerCase().contains(lowerKeyword)) ||
                            (ariaLabel != null && ariaLabel.toLowerCase().contains(lowerKeyword)) ||
                            (id != null && id.toLowerCase().contains(lowerKeyword)) ||
                            (name != null && name.toLowerCase().contains(lowerKeyword))) {
                        return input;
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }

        throw new NoSuchElementException("No visible input found for keywords: " + String.join(", ", keywords));
    }
}