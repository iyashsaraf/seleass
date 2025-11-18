package com.neu.info6255.tests;

import com.neu.info6255.utils.TestReporter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlClass;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        // Create TestNG suite programmatically
        TestNG testng = new TestNG();

        XmlSuite suite = new XmlSuite();
        suite.setName("INFO6255 Assignment Suite");

        XmlTest test = new XmlTest(suite);
        test.setName("All Scenarios");

        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass("com.neu.info6255.tests.Scenario1_TranscriptTest"));
        classes.add(new XmlClass("com.neu.info6255.tests.Scenario2_CanvasEventsTest"));
        classes.add(new XmlClass("com.neu.info6255.tests.Scenario3_LibraryReservationTest"));
        classes.add(new XmlClass("com.neu.info6255.tests.Scenario4_DatasetDownloadTest"));
        classes.add(new XmlClass("com.neu.info6255.tests.Scenario5_AcademicCalendarTest"));

        test.setXmlClasses(classes);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testng.setXmlSuites(suites);

        // Run tests
        System.out.println("========================================");
        System.out.println("INFO6255 Selenium Assignment Starting...");
        System.out.println("========================================\n");

        testng.run();

        // Generate HTML report
        System.out.println("\n========================================");
        System.out.println("Generating HTML Report...");
        System.out.println("========================================");
        TestReporter.generateHTMLReport();

        System.out.println("\n✓ All tests completed!");
    }
}