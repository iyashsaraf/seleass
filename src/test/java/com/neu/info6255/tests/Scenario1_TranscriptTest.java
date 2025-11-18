package com.neu.info6255.tests;

import com.neu.info6255.base.BaseTest;
import com.neu.info6255.pages.LoginPage;
import com.neu.info6255.pages.TranscriptPage;
import com.neu.info6255.utils.ExcelUtils;
import com.neu.info6255.utils.TestReporter;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class Scenario1_TranscriptTest extends BaseTest {

    @Test(priority = 1, description = "Scenario 1: Download the latest transcript")
    public void testDownloadTranscript() {
        currentScenario = "Scenario1_Transcript";

        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO 1: DOWNLOAD TRANSCRIPT");
        System.out.println("=".repeat(70) + "\n");

        try {
            // Get login credentials from Excel
            Map<String, String> credentials = ExcelUtils.getLoginCredentials();
            String username = credentials.get("Username");
            String password = credentials.get("Password");

            // Step a: Log in to My NEU portal
            System.out.println("Step a: Logging in to My NEU portal");
            takeScreenshot("01_Before_Navigate_to_NEU");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToNEU();
            takeScreenshot("02_After_Navigate_to_NEU");

            loginPage.login(username, password);
            takeScreenshot("03_After_Login_Complete");

            // Verify login successful
            Assert.assertTrue(
                    driver.getCurrentUrl().contains("me.northeastern.edu"),
                    "Should be on NEU portal after login"
            );

            // Step b: Launch the Student Hub portal
            System.out.println("\nStep b: Navigating to Student Hub");
            takeScreenshot("04_Before_Student_Hub");

            TranscriptPage transcriptPage = new TranscriptPage(driver);
            transcriptPage.navigateToStudentHub();
            takeScreenshot("05_After_Student_Hub");

            // Step c: Hit the Resources tab
            System.out.println("\nStep c: Clicking Resources tab");
            takeScreenshot("06_Before_Resources_Tab");

            transcriptPage.clickResources();
            takeScreenshot("07_After_Resources_Tab");

            // Step d: Go to 'Academics, Classes & Registration'
            System.out.println("\nStep d: Clicking Academics section");
            takeScreenshot("08_Before_Academics_Section");

            transcriptPage.clickAcademics();
            takeScreenshot("09_After_Academics_Section");

            // Step e: Go to 'My Transcripts'
            System.out.println("\nStep e: Clicking My Transcript link");
            takeScreenshot("10_Before_My_Transcripts");

            transcriptPage.clickMyTranscripts();
            takeScreenshot("11_After_My_Transcripts_Click");

            // Login to transcript portal (new window)
            System.out.println("\nLogging into transcript portal");
            takeScreenshot("12_Before_Transcript_Login");

            transcriptPage.loginToTranscriptPortal(username, password);
            takeScreenshot("13_After_Transcript_Login");

            // Step f: Select 'Graduate' and 'Audit Transcript'
            System.out.println("\nStep f: Selecting transcript options");
            takeScreenshot("14_Before_Select_Options");

            transcriptPage.selectTranscriptOptions();
            takeScreenshot("15_After_Select_Options");

            // Verify selections
            Assert.assertTrue(
                    driver.getPageSource().contains("Graduate") ||
                            driver.getPageSource().contains("Audit"),
                    "Graduate and Audit options should be selected"
            );

            takeScreenshot("16_Before_Submit");
            transcriptPage.clickSubmit();
            takeScreenshot("17_After_Submit_Transcript_Displayed");

            // Verify transcript is displayed
            Assert.assertTrue(
                    driver.getPageSource().contains("Academic Transcript") ||
                            driver.getPageSource().contains("Northeastern University"),
                    "Transcript should be displayed"
            );

            // Step g: Right-click → Print Page → Save as PDF
            System.out.println("\nStep g: Right-click → Print Page → Save as PDF");
            takeScreenshot("18_Before_Print");

            transcriptPage.printTranscript();
            takeScreenshot("19_After_PDF_Saved");

            TestReporter.addResult(
                    "Scenario 1: Download Transcript",
                    "Transcript should be displayed and automatically saved as PDF",
                    "Transcript displayed successfully and PDF saved automatically",
                    "PASS"
            );

            System.out.println("\n" + "=".repeat(70));
            System.out.println("✓ SCENARIO 1: DOWNLOAD TRANSCRIPT - PASSED");
            System.out.println("=".repeat(70) + "\n");

        } catch (Exception e) {
            System.err.println("\n❌ SCENARIO 1 FAILED: " + e.getMessage());
            takeScreenshot("ERROR_Scenario1");
            TestReporter.addResult(
                    "Scenario 1: Download Transcript",
                    "Transcript should be displayed and ready for download",
                    "Error occurred: " + e.getMessage(),
                    "FAIL"
            );
            throw e;
        }
    }
}