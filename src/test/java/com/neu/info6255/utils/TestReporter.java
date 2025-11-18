package com.neu.info6255.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestReporter {

    private static List<TestResult> results = new ArrayList<>();

    public static class TestResult {
        String scenarioName;
        String expected;
        String actual;
        String status;

        public TestResult(String scenarioName, String expected, String actual, String status) {
            this.scenarioName = scenarioName;
            this.expected = expected;
            this.actual = actual;
            this.status = status;
        }
    }

    /**
     * Add test result to report
     */
    public static void addResult(String scenarioName, String expected, String actual, String status) {
        results.add(new TestResult(scenarioName, expected, actual, status));
        System.out.println("📝 Recorded result: " + scenarioName + " - " + status);
    }

    /**
     * Generate HTML report
     */
    public static void generateHTMLReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "test-output/TestReport_" + timestamp + ".html";

        // Create test-output directory if it doesn't exist
        File outputDir = new File("test-output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            // Calculate summary
            int total = results.size();
            int passed = 0;
            int failed = 0;

            for (TestResult result : results) {
                if (result.status.equalsIgnoreCase("PASS")) {
                    passed++;
                } else {
                    failed++;
                }
            }

            double passRate = total > 0 ? (passed * 100.0 / total) : 0;

            // Write HTML
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html lang='en'>\n");
            writer.write("<head>\n");
            writer.write("    <meta charset='UTF-8'>\n");
            writer.write("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n");
            writer.write("    <title>INFO6255 Selenium Test Report</title>\n");
            writer.write("    <style>\n");
            writer.write("        * { margin: 0; padding: 0; box-sizing: border-box; }\n");
            writer.write("        body {\n");
            writer.write("            font-family: 'Segoe UI', Arial, sans-serif;\n");
            writer.write("            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
            writer.write("            padding: 40px 20px;\n");
            writer.write("            min-height: 100vh;\n");
            writer.write("        }\n");
            writer.write("        .container {\n");
            writer.write("            max-width: 1200px;\n");
            writer.write("            margin: 0 auto;\n");
            writer.write("            background: white;\n");
            writer.write("            border-radius: 15px;\n");
            writer.write("            box-shadow: 0 20px 60px rgba(0,0,0,0.3);\n");
            writer.write("            overflow: hidden;\n");
            writer.write("        }\n");
            writer.write("        .header {\n");
            writer.write("            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
            writer.write("            color: white;\n");
            writer.write("            padding: 50px 40px;\n");
            writer.write("            text-align: center;\n");
            writer.write("        }\n");
            writer.write("        .header h1 {\n");
            writer.write("            font-size: 2.8em;\n");
            writer.write("            margin-bottom: 15px;\n");
            writer.write("            font-weight: 600;\n");
            writer.write("        }\n");
            writer.write("        .header p {\n");
            writer.write("            font-size: 1.15em;\n");
            writer.write("            opacity: 0.95;\n");
            writer.write("        }\n");
            writer.write("        .summary {\n");
            writer.write("            display: grid;\n");
            writer.write("            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));\n");
            writer.write("            gap: 25px;\n");
            writer.write("            padding: 40px;\n");
            writer.write("            background: #f8f9fa;\n");
            writer.write("        }\n");
            writer.write("        .summary-card {\n");
            writer.write("            background: white;\n");
            writer.write("            padding: 30px;\n");
            writer.write("            border-radius: 10px;\n");
            writer.write("            box-shadow: 0 4px 15px rgba(0,0,0,0.1);\n");
            writer.write("            text-align: center;\n");
            writer.write("            transition: transform 0.3s;\n");
            writer.write("        }\n");
            writer.write("        .summary-card:hover {\n");
            writer.write("            transform: translateY(-5px);\n");
            writer.write("        }\n");
            writer.write("        .summary-card h3 {\n");
            writer.write("            color: #666;\n");
            writer.write("            font-size: 0.95em;\n");
            writer.write("            text-transform: uppercase;\n");
            writer.write("            margin-bottom: 15px;\n");
            writer.write("            letter-spacing: 1px;\n");
            writer.write("        }\n");
            writer.write("        .summary-card .value {\n");
            writer.write("            font-size: 3em;\n");
            writer.write("            font-weight: bold;\n");
            writer.write("            margin: 15px 0;\n");
            writer.write("        }\n");
            writer.write("        .summary-card.total .value { color: #667eea; }\n");
            writer.write("        .summary-card.passed .value { color: #10b981; }\n");
            writer.write("        .summary-card.failed .value { color: #ef4444; }\n");
            writer.write("        .summary-card.percentage .value { color: #f59e0b; }\n");
            writer.write("        .table-container {\n");
            writer.write("            padding: 40px;\n");
            writer.write("        }\n");
            writer.write("        table {\n");
            writer.write("            width: 100%;\n");
            writer.write("            border-collapse: collapse;\n");
            writer.write("            background: white;\n");
            writer.write("            border-radius: 10px;\n");
            writer.write("            overflow: hidden;\n");
            writer.write("            box-shadow: 0 4px 15px rgba(0,0,0,0.1);\n");
            writer.write("        }\n");
            writer.write("        thead {\n");
            writer.write("            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
            writer.write("            color: white;\n");
            writer.write("        }\n");
            writer.write("        th {\n");
            writer.write("            padding: 20px;\n");
            writer.write("            text-align: left;\n");
            writer.write("            font-weight: 600;\n");
            writer.write("            font-size: 1em;\n");
            writer.write("            text-transform: uppercase;\n");
            writer.write("            letter-spacing: 0.5px;\n");
            writer.write("        }\n");
            writer.write("        td {\n");
            writer.write("            padding: 18px 20px;\n");
            writer.write("            border-bottom: 1px solid #e5e7eb;\n");
            writer.write("        }\n");
            writer.write("        tbody tr:hover {\n");
            writer.write("            background-color: #f9fafb;\n");
            writer.write("        }\n");
            writer.write("        .scenario-name {\n");
            writer.write("            font-weight: 600;\n");
            writer.write("            color: #1f2937;\n");
            writer.write("        }\n");
            writer.write("        .status-badge {\n");
            writer.write("            display: inline-block;\n");
            writer.write("            padding: 8px 20px;\n");
            writer.write("            border-radius: 25px;\n");
            writer.write("            font-weight: 700;\n");
            writer.write("            font-size: 0.9em;\n");
            writer.write("            text-transform: uppercase;\n");
            writer.write("        }\n");
            writer.write("        .pass {\n");
            writer.write("            background-color: #d1fae5;\n");
            writer.write("            color: #065f46;\n");
            writer.write("        }\n");
            writer.write("        .fail {\n");
            writer.write("            background-color: #fee2e2;\n");
            writer.write("            color: #991b1b;\n");
            writer.write("        }\n");
            writer.write("        .footer {\n");
            writer.write("            text-align: center;\n");
            writer.write("            padding: 30px;\n");
            writer.write("            background: #f8f9fa;\n");
            writer.write("            color: #666;\n");
            writer.write("            font-size: 0.95em;\n");
            writer.write("        }\n");
            writer.write("    </style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("    <div class='container'>\n");

            // Header
            writer.write("        <div class='header'>\n");
            writer.write("            <h1>🎯 Selenium Test Automation Report</h1>\n");
            writer.write("            <p>INFO6255 - Software Quality Control | Fall 2025</p>\n");
            writer.write("            <p style='margin-top: 10px;'>" + new SimpleDateFormat("MMMM dd, yyyy - hh:mm:ss a").format(new Date()) + "</p>\n");
            writer.write("        </div>\n");

            // Summary Cards
            writer.write("        <div class='summary'>\n");
            writer.write("            <div class='summary-card total'>\n");
            writer.write("                <h3>Total Tests</h3>\n");
            writer.write("                <div class='value'>" + total + "</div>\n");
            writer.write("            </div>\n");
            writer.write("            <div class='summary-card passed'>\n");
            writer.write("                <h3>Passed</h3>\n");
            writer.write("                <div class='value'>" + passed + "</div>\n");
            writer.write("            </div>\n");
            writer.write("            <div class='summary-card failed'>\n");
            writer.write("                <h3>Failed</h3>\n");
            writer.write("                <div class='value'>" + failed + "</div>\n");
            writer.write("            </div>\n");
            writer.write("            <div class='summary-card percentage'>\n");
            writer.write("                <h3>Pass Rate</h3>\n");
            writer.write("                <div class='value'>" + String.format("%.1f%%", passRate) + "</div>\n");
            writer.write("            </div>\n");
            writer.write("        </div>\n");

            // Test Results Table
            writer.write("        <div class='table-container'>\n");
            writer.write("            <table>\n");
            writer.write("                <thead>\n");
            writer.write("                    <tr>\n");
            writer.write("                        <th>Test Scenario Name</th>\n");
            writer.write("                        <th>Expected</th>\n");
            writer.write("                        <th>Actual</th>\n");
            writer.write("                        <th>Pass/Fail</th>\n");
            writer.write("                    </tr>\n");
            writer.write("                </thead>\n");
            writer.write("                <tbody>\n");

            for (TestResult result : results) {
                String badgeClass = result.status.equalsIgnoreCase("PASS") ? "pass" : "fail";
                writer.write("                    <tr>\n");
                writer.write("                        <td class='scenario-name'>" + result.scenarioName + "</td>\n");
                writer.write("                        <td>" + result.expected + "</td>\n");
                writer.write("                        <td>" + result.actual + "</td>\n");
                writer.write("                        <td><span class='status-badge " + badgeClass + "'>" + result.status + "</span></td>\n");
                writer.write("                    </tr>\n");
            }

            writer.write("                </tbody>\n");
            writer.write("            </table>\n");
            writer.write("        </div>\n");

            // Footer
            writer.write("        <div class='footer'>\n");
            writer.write("            <p><strong>Northeastern University</strong> - INFO6255 Selenium Assignment</p>\n");
            writer.write("            <p style='margin-top: 5px;'>Automated Test Report - Generated by Selenium WebDriver</p>\n");
            writer.write("        </div>\n");

            writer.write("    </div>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");

            System.out.println("\n" + "=".repeat(70));
            System.out.println("✅ HTML REPORT GENERATED SUCCESSFULLY");
            System.out.println("=".repeat(70));
            System.out.println("📄 Location: " + new File(fileName).getAbsolutePath());
            System.out.println("\n📊 Summary:");
            System.out.println("   Total Tests: " + total);
            System.out.println("   Passed: " + passed + " ✅");
            System.out.println("   Failed: " + failed + " ❌");
            System.out.println("   Pass Rate: " + String.format("%.1f%%", passRate));
            System.out.println("=".repeat(70) + "\n");

        } catch (IOException e) {
            System.err.println("❌ Failed to generate HTML report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clear all results (for fresh test runs)
     */
    public static void clearResults() {
        results.clear();
    }

    /**
     * Get total number of results
     */
    public static int getResultCount() {
        return results.size();
    }
}