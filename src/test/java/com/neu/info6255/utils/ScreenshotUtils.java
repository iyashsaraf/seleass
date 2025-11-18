package com.neu.info6255.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String scenarioName, String step) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = scenarioName + "_" + step + "_" + timestamp + ".png";

            // Create directory if it doesn't exist
            File screenshotDir = new File("screenshots/" + scenarioName);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotDir, fileName);

            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot saved: " + destination.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}