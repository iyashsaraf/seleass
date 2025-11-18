package com.neu.info6255.tests;

import com.neu.info6255.base.BaseTest;
import com.neu.info6255.pages.LoginPage;
import com.neu.info6255.utils.ExcelUtils;
import com.neu.info6255.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class SimpleLoginTest extends BaseTest {

    @Test(description = "Simple login test with Microsoft Azure AD")
    public void testSimpleLogin() {
        currentScenario = "SimpleLogin_Test";

        // Get credentials from Excel
        System.out.println("\n" + "=".repeat(60));
        System.out.println("READING TEST DATA FROM EXCEL");
        System.out.println("=".repeat(60));

        Map<String, String> credentials = ExcelUtils.getLoginCredentials();
        String username = credentials.get("Username");
        String password = credentials.get("Password");

        System.out.println("✓ Username: " + username);
        System.out.println("✓ Password: " + (password != null ? "****" : "NULL"));
        System.out.println("=".repeat(60) + "\n");

        // Navigate to NEU
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToNEU();
        takeScreenshot("01_NEU_Homepage");

        // Perform login
        loginPage.login(username, password);
        takeScreenshot("02_After_Login");

        // Wait a bit for page to load
        WaitUtils.sleep(5000);

        // Verify login success
        takeScreenshot("03_Final_Page");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        // Assert that we're logged in (URL should contain northeastern.edu)
        Assert.assertTrue(
                driver.getCurrentUrl().contains("northeastern.edu") ||
                        driver.getCurrentUrl().contains("me.northeastern"),
                "Should be on NEU portal after login"
        );

        System.out.println("✅ Login Successful!");
        System.out.println("=".repeat(60) + "\n");
    }
}
