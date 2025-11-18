package com.neu.info6255.tests;

import com.neu.info6255.base.BaseTest;
import com.neu.info6255.pages.DatasetPage;
import com.neu.info6255.utils.TestReporter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Scenario4_DatasetDownloadTest extends BaseTest {

    @Test(priority = 4, description = "Scenario 4: Download Dataset - NEGATIVE TEST (Must Fail)")
    public void testDownloadDataset_NegativeScenario() {
        currentScenario = "Scenario4_Dataset_Negative";

        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO 4: DATASET DOWNLOAD (NEGATIVE TEST - EXPECTED TO FAIL)");
        System.out.println("=".repeat(70) + "\n");

        boolean testFailedAsExpected = false;
        String failureReason = "";

        try {
            DatasetPage datasetPage = new DatasetPage(driver);

            // Step a: Navigate to OneSearch and click Digital Repository
            System.out.println("Step a: Opening OneSearch and Digital Repository");
            takeScreenshot("01_Before_OneSearch");

            datasetPage.navigateToOneSearch();
            takeScreenshot("02_After_OneSearch");

            datasetPage.clickDigitalRepository();
            takeScreenshot("03_After_Digital_Repository");

            // Verify we're in DRS
            Assert.assertTrue(
                    driver.getCurrentUrl().contains("repository") ||
                            driver.getPageSource().contains("Digital Repository"),
                    "Should be on Digital Repository Service"
            );

            // Step b: Click Datasets
            System.out.println("\nStep b: Clicking Datasets");
            takeScreenshot("04_Before_Datasets");

            datasetPage.clickDatasets();
            takeScreenshot("05_After_Datasets_List");

            // Open first dataset
            System.out.println("\nStep b (continued): Opening dataset");
            takeScreenshot("06_Before_Open_Dataset");

            datasetPage.openFirstDataset();
            takeScreenshot("07_After_Open_Dataset");

            // Step c: Attempt to download Zip File (THIS SHOULD FAIL)
            System.out.println("\nStep c: Attempting to download Zip File");
            takeScreenshot("08_Before_Download_Attempt");

            datasetPage.clickZipFile();

            // If we reach here, download succeeded - TEST FAILED
            takeScreenshot("09_Download_Succeeded_UNEXPECTED");

            System.out.println("\n" + "=".repeat(70));
            System.out.println("❌ NEGATIVE TEST FAILED: Download should have failed but succeeded!");
            System.out.println("=".repeat(70) + "\n");

            // Fail the test because download worked when it shouldn't
            Assert.fail("NEGATIVE TEST FAILED: Dataset download succeeded when it should have been blocked");

        } catch (RuntimeException e) {
            // This is EXPECTED - the download should fail
            testFailedAsExpected = true;
            failureReason = e.getMessage();

            takeScreenshot("10_Expected_Failure_Screenshot");

            TestReporter.addResult(
                    "Scenario 4: Download Dataset (Negative Test)",
                    "Dataset download should FAIL (access restricted)",
                    failureReason,
                    "FAIL"
            );

            System.out.println("\n" + "=".repeat(70));
            System.out.println("✅ NEGATIVE TEST PASSED: Download failed as expected");
            System.out.println("Failure Reason: " + failureReason);
            System.out.println("=".repeat(70) + "\n");

        } catch (AssertionError e) {
            // This is ALSO EXPECTED - download succeeded when it shouldn't
            testFailedAsExpected = true;
            failureReason = e.getMessage();

            takeScreenshot("11_Negative_Test_Failed");

            TestReporter.addResult(
                    "Scenario 4: Download Dataset (Negative Test)",
                    "Dataset download should FAIL (access restricted)",
                    failureReason,
                    "FAIL"
            );

            System.out.println("\n" + "=".repeat(70));
            System.out.println("❌ NEGATIVE TEST OUTCOME: " + failureReason);
            System.out.println("=".repeat(70) + "\n");

            // Re-throw to mark test as failed
            throw e;

        } catch (Exception e) {
            takeScreenshot("12_Unexpected_Error");
            System.err.println("❌ Unexpected error: " + e.getMessage());
            throw e;
        }

        // Assert that the download attempt failed
        if (!testFailedAsExpected) {
            Assert.fail("NEGATIVE TEST DID NOT FAIL AS EXPECTED");
        }
    }
}