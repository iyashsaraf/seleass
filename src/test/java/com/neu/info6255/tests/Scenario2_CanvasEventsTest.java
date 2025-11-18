package com.neu.info6255.tests;

import com.neu.info6255.base.BaseTest;
import com.neu.info6255.pages.CanvasPage;
import com.neu.info6255.pages.LoginPage;
import com.neu.info6255.utils.ExcelUtils;
import com.neu.info6255.utils.TestReporter;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class Scenario2_CanvasEventsTest extends BaseTest {

    @Test(priority = 2, description = "Add two Event tasks in Canvas Calendar")
    public void testAddCanvasEvents() {
        currentScenario = "Scenario2_CanvasEvents";

        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO 2: ADD CANVAS CALENDAR EVENTS");
        System.out.println("=".repeat(70) + "\n");

        try {
            // Get login credentials
            Map<String, String> credentials = ExcelUtils.getLoginCredentials();
            String username = credentials.get("Username");
            String password = credentials.get("Password");

            // Get event data from Excel
            List<Map<String, String>> eventDataList = ExcelUtils.getTestData("EventData");
            System.out.println("✓ Loaded " + eventDataList.size() + " events from Excel");

            // Step a: Log in to Canvas
            CanvasPage canvasPage = new CanvasPage(driver);
            canvasPage.navigateToCanvas();
            takeScreenshot("01_Navigate_to_Canvas");

            // Login to Canvas (it will redirect to NEU login)
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(username, password);
            takeScreenshot("02_After_Login");

            // Open Calendar
            canvasPage.openCalendar();
            takeScreenshot("03_Calendar_Page");

            // Step b: Create 2 events iteratively from Excel data
            int eventNumber = 1;
            for (Map<String, String> eventData : eventDataList) {
                System.out.println("\nCreating Event " + eventNumber + ": " + eventData.get("Title"));

                canvasPage.clickAddEvent();
                takeScreenshot("04_Add_Event_Dialog_" + eventNumber);

                canvasPage.createEvent(eventData);
                takeScreenshot("05_Fill_Event_Details_" + eventNumber);

                canvasPage.submitEvent();
                takeScreenshot("06_Event_Created_" + eventNumber);

                // Verify event was created
                Assert.assertTrue(driver.getPageSource().contains(eventData.get("Title")),
                        "Event '" + eventData.get("Title") + "' should be visible in calendar");

                System.out.println("✓ Event " + eventNumber + " created successfully");
                eventNumber++;
            }

            takeScreenshot("07_Both_Events_Created");

            System.out.println("\n" + "=".repeat(70));
            System.out.println("✓ SCENARIO 2: CANVAS EVENTS - PASSED");
            System.out.println("=".repeat(70) + "\n");

            TestReporter.addResult(
                    "Scenario 2: Add Canvas Calendar Events",
                    "Two events should be created successfully in Canvas calendar",
                    "Both events created and visible in calendar",
                    "PASS"
            );

        } catch (Exception e) {
            System.err.println("\n❌ SCENARIO 2 FAILED: " + e.getMessage());

            TestReporter.addResult(
                    "Scenario 2: Add Canvas Calendar Events",
                    "Two events should be created successfully in Canvas calendar",
                    "Error: " + e.getMessage(),
                    "FAIL"
            );
            takeScreenshot("ERROR_Scenario2");
            throw e;
        }
    }
}