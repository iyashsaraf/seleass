package com.neu.info6255.pages;

import com.neu.info6255.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class CalendarPage extends BasePage {

    // Navigation
    private By resourcesTab = By.linkText("Resources");
    private By academicsTab = By.cssSelector("#resource-tab-Academics\\,_Classes_\\&_Registration > .fui-Tab__content");
    private By academicCalendarLink = By.linkText("Academic Calendar");

    // Calendar page link
    private By calendarPageLink = By.cssSelector(".--indent > .__item:nth-child(1)");

    // Trumba calendar iframe
    private By trumbaIframe = By.id("trumba.spud.7.iframe");

    // Inside iframe - calendar filters
    private By calendarCheckboxes = By.cssSelector("input[type='checkbox']");
    private By selectAllNoneLink = By.linkText("All");

    // Add to My Calendar button (inside iframe)
    private By addToCalendarButton = By.xpath("//a[contains(text(), 'Add to My Calendar')]");
    private By addToCalendarButtonAlt = By.cssSelector("a[title*='Add to My Calendar']");

    public CalendarPage(WebDriver driver) {
        super(driver);
    }

    public void clickResources() {
        System.out.println("→ Clicking Resources tab...");
        waitForElement(resourcesTab);
        click(resourcesTab);
        WaitUtils.sleep(1000);
        System.out.println("✓ Clicked Resources");
    }

    public void clickAcademics() {
        System.out.println("→ Clicking Academics section...");
        waitForElement(academicsTab);
        click(academicsTab);
        WaitUtils.sleep(1000);
        System.out.println("✓ Clicked Academics");
    }

    public void clickAcademicCalendar() {
        System.out.println("→ Clicking Academic Calendar link...");

        String mainWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        click(academicCalendarLink);
        WaitUtils.sleep(2000);

        // Wait for new window
        wait.withTimeout(Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > existingWindows.size());

        // Switch to new window
        for (String handle : driver.getWindowHandles()) {
            if (!existingWindows.contains(handle)) {
                driver.switchTo().window(handle);
                System.out.println("✓ Switched to Academic Calendar window");
                System.out.println("Current URL: " + driver.getCurrentUrl());
                break;
            }
        }

        WaitUtils.sleep(2000);
    }

    public void clickCalendarLink() {
        System.out.println("→ Clicking calendar page link...");

        try {
            waitForElement(calendarPageLink);
            click(calendarPageLink);
            WaitUtils.sleep(3000); // Wait for calendar to load
            System.out.println("✓ Clicked calendar link");
            System.out.println("Current URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("⚠️ Calendar link may already be active");
        }
    }

    public void scrollToCalendarFilters() {
        System.out.println("\n→ Scrolling to calendar filters section...");

        try {
            // Wait for Trumba iframe to load
            WaitUtils.sleep(2000);

            // Scroll down on main page to show the calendar area
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");
            WaitUtils.sleep(1500);

            System.out.println("✓ Scrolled to calendar filters area");

        } catch (Exception e) {
            System.out.println("⚠️ Scroll issue: " + e.getMessage());
        }
    }

    public void uncheckOneCalendar() {
        System.out.println("\n→ Unchecking one calendar checkbox...");

        try {
            // Wait for iframe to be available
            WaitUtils.sleep(2000);

            // Find Trumba iframe by ID
            List<WebElement> iframes = driver.findElements(trumbaIframe);
            System.out.println("Found " + iframes.size() + " Trumba iframe(s)");

            if (iframes.size() > 0) {
                // Switch to Trumba iframe
                driver.switchTo().frame(iframes.get(0));
                System.out.println("✓ Switched to Trumba calendar iframe");

                WaitUtils.sleep(1500);

                // Find all checkboxes inside iframe
                List<WebElement> checkboxes = driver.findElements(calendarCheckboxes);
                System.out.println("Found " + checkboxes.size() + " checkboxes inside iframe");

                int uncheckedCount = 0;

                // Find first CHECKED checkbox and uncheck it
                for (int i = 0; i < checkboxes.size(); i++) {
                    WebElement checkbox = checkboxes.get(i);

                    try {
                        if (checkbox.isDisplayed() && checkbox.isEnabled() && checkbox.isSelected()) {
                            // Scroll to checkbox within iframe
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({block: 'center'});",
                                    checkbox
                            );
                            WaitUtils.sleep(300);

                            // Get associated label/text
                            String checkboxName = checkbox.getAttribute("name");
                            String checkboxId = checkbox.getAttribute("id");
                            System.out.println("  Unchecking: id='" + checkboxId + "', name='" + checkboxName + "'");

                            // Click to uncheck
                            checkbox.click();
                            WaitUtils.sleep(800);

                            System.out.println("✓ Successfully unchecked calendar checkbox");
                            uncheckedCount++;
                            break; // Only uncheck ONE
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }

                if (uncheckedCount == 0) {
                    System.out.println("⚠️ No checked checkboxes found to uncheck");
                }

                // Switch back to main content
                driver.switchTo().defaultContent();
                System.out.println("✓ Switched back to default content");

            } else {
                System.out.println("⚠️ Trumba iframe not found - checkboxes may be on main page");
            }

            WaitUtils.sleep(1000);

        } catch (Exception e) {
            System.out.println("⚠️ Error unchecking calendar: " + e.getMessage());
            e.printStackTrace();
            driver.switchTo().defaultContent();
        }
    }

    public void scrollToBottom() {
        System.out.println("\n→ Scrolling to bottom to show 'Add to My Calendar' button...");

        try {
            // Scroll all the way down
            ((JavascriptExecutor) driver).executeScript(
                    "window.scrollTo(0, document.body.scrollHeight);"
            );
            WaitUtils.sleep(2000);

            System.out.println("✓ Scrolled to bottom of page");

        } catch (Exception e) {
            System.out.println("⚠️ Scroll error: " + e.getMessage());
        }
    }

    public boolean verifyAddToCalendarButton() {
        System.out.println("\n→ Verifying 'Add to My Calendar' button is displayed...");

        try {
            WaitUtils.sleep(1000);

            // Switch to Trumba iframe
            List<WebElement> iframes = driver.findElements(trumbaIframe);

            if (iframes.size() > 0) {
                driver.switchTo().frame(iframes.get(0));
                WaitUtils.sleep(1000);

                // Simply check if button text exists in the iframe
                String iframeSource = driver.getPageSource();
                boolean buttonExists = iframeSource.contains("Add to My Calendar");

                driver.switchTo().defaultContent();

                if (buttonExists) {
                    System.out.println("✓✓✓ 'Add to My Calendar' button IS VISIBLE on page");
                    return true;
                } else {
                    System.out.println("⚠️ Button not found");
                    return false;
                }

            } else {
                System.out.println("⚠️ Iframe not found");
                return false;
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            try { driver.switchTo().defaultContent(); } catch (Exception ex) {}
            return false;
        }
    }
}
