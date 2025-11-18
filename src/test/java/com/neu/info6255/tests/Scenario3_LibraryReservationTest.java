package com.neu.info6255.tests;

import com.neu.info6255.base.BaseTest;
import com.neu.info6255.pages.LibraryPage;
import com.neu.info6255.utils.TestReporter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Scenario3_LibraryReservationTest extends BaseTest {

    @Test(priority = 3, description = "Scenario 3: Reserve a spot in Snell Library")
    public void testReserveLibraryRoom() {
        currentScenario = "Scenario3_LibraryRoom";

        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO 3: RESERVE LIBRARY STUDY ROOM");
        System.out.println("=".repeat(70) + "\n");

        try {
            LibraryPage libraryPage = new LibraryPage(driver);

            // Step a: Open library website
            System.out.println("Step a: Opening Library Website");
            takeScreenshot("01_Before_Navigate_Library");

            libraryPage.navigateToLibrary();
            takeScreenshot("02_After_Navigate_Library");

            // Verify on library page
            Assert.assertTrue(
                    driver.getCurrentUrl().contains("library.northeastern.edu"),
                    "Should be on library website"
            );

            // Step b: Click 'Reserve a Study Room'
            System.out.println("\nStep b: Clicking 'Reserve a Study Room'");
            takeScreenshot("03_Before_Reserve_Link");

            libraryPage.clickReserveStudyRoom();
            takeScreenshot("04_After_Reserve_Link");

            // Step c: Select 'Boston'
            System.out.println("\nStep c: Selecting Boston location");
            takeScreenshot("05_Before_Select_Boston");

            libraryPage.selectBoston();
            takeScreenshot("06_After_Select_Boston");

            // Step d: Click 'Book a Room'
            System.out.println("\nStep d: Clicking 'Book a Room'");
            takeScreenshot("07_Before_Book_Room");

            libraryPage.clickBookRoom();
            takeScreenshot("08_After_Book_Room");

            // Verify on booking page
            Assert.assertTrue(
                    driver.getCurrentUrl().contains("libcal") ||
                            driver.getPageSource().contains("Book a Room"),
                    "Should be on room booking page"
            );

            // Step e: Select 'Individual Study'
            System.out.println("\nStep e: Selecting 'Individual Study'");
            takeScreenshot("09_Before_Select_SeatStyle");

            libraryPage.selectSeatStyle();
            takeScreenshot("10_After_Select_SeatStyle");

            // Step e continued: Select 'Space For 1-4 people'
            System.out.println("\nStep e (continued): Selecting capacity");
            takeScreenshot("11_Before_Select_Capacity");

            libraryPage.selectCapacity();
            takeScreenshot("12_After_Select_Capacity");

            // Step f: Scroll down to view available rooms
            System.out.println("\nStep f: Scrolling to view rooms");
            takeScreenshot("13_Before_Scroll");

            libraryPage.scrollToBottom();
            takeScreenshot("14_After_Scroll_Rooms_Visible");

            // Verify rooms are displayed
            libraryPage.verifyRoomsDisplayed();

            TestReporter.addResult(
                    "Scenario 3: Reserve Library Study Room",
                    "Booking filters applied (Individual Study, 1-4 people) and rooms displayed",
                    "Filters applied successfully, available study rooms visible",
                    "PASS"
            );

            System.out.println("\n" + "=".repeat(70));
            System.out.println("✅ SCENARIO 3: LIBRARY ROOM RESERVATION - PASSED");
            System.out.println("=".repeat(70) + "\n");

        } catch (Exception e) {
            System.err.println("\n❌ SCENARIO 3 FAILED: " + e.getMessage());

            TestReporter.addResult(
                    "Scenario 3: Reserve Library Study Room",
                    "Booking filters applied (Individual Study, 1-4 people) and rooms displayed",
                    "Error: " + e.getMessage(),
                    "FAIL"
            );
            takeScreenshot("ERROR_Scenario3");
            throw e;
        }
    }
}