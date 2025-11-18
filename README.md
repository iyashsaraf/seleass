# NEU Selenium Automation Framework

##  Project Description

This is a comprehensive Selenium WebDriver automation framework designed to test key student services across Northeastern University platforms. The framework demonstrates end-to-end testing of critical student workflows including transcript access, calendar management, library services, and academic resources.

The framework automates key student services across NEU platforms using:

- **Java 11** with Maven for project management
- **Selenium WebDriver 4.15.0** for browser automation
- **TestNG 7.8.0** as the test runner and assertion framework
- **Apache POI 5.2.5** for Excel-driven test inputs
- **WebDriverManager 5.6.2** for automatic driver management
- **Page Object Model (POM)** design pattern
- **Data-Driven Testing** with Excel files
- **Automated screenshots** for test evidence
- **HTML-style reporting** with TestReporter utility

---

##  Test Scenarios

### Scenario 1 – Download Transcript from Student Hub
**Objective:** Navigate to NEU Student Hub and access the official transcript

**Steps:**
1. Login to NEU portal with credentials
2. Navigate to Student Hub
3. Access Resources tab
4. Navigate to Academics section
5. Click on "My Transcripts"
6. Verify transcript page loads successfully

**Expected Result:** Transcript displayed successfully, ready for print/save as PDF

---

### Scenario 2 – Add Canvas Calendar Events
**Objective:** Create multiple calendar events in Canvas

**Steps:**
1. Login to Canvas LMS
2. Navigate to Calendar
3. Create Event 1: "Project Meeting" with details from Excel
4. Create Event 2: "Code Review" with details from Excel
5. Verify both events are visible in calendar

**Expected Result:** Both events created and visible in Canvas calendar

**Data Source:** `src/test/resources/TestData.xlsx` - Sheet: EventData

---

### Scenario 3 – Reserve Library Study Room
**Objective:** Apply booking filters and view available study rooms

**Steps:**
1. Navigate to Snell Library booking system
2. Apply filter: "Individual Study"
3. Apply filter: "1-4 people" capacity
4. Verify available rooms are displayed

**Expected Result:** Filters applied successfully, available study rooms visible

---

### Scenario 4 – Download Dataset (Negative Test)
**Objective:** Verify dataset download is properly restricted

**Steps:**
1. Navigate to OneSearch Digital Repository
2. Browse to Datasets section
3. Open first available dataset
4. Attempt to download ZIP file

**Expected Result:** Download should FAIL (access restricted) - This is a negative test validating security controls

---

### Scenario 5 – Update Academic Calendar
**Objective:** Manage Academic Calendar filters and display options

**Steps:**
1. Login to NEU portal
2. Navigate to Student Hub
3. Access Academic Calendar
4. Uncheck calendar filter checkbox
5. Verify "Add to My Calendar" button is displayed

**Expected Result:** Calendar filter unchecked successfully, on Academic Calendar page

---

##  Project Architecture

### Framework Structure
```
INFO6255-Selenium-Assignment/
├── src/
│   ├── main/java/
│   └── test/
│       ├── java/
│       │   └── com/neu/info6255/
│       │       ├── base/
│       │       │   └── BaseTest.java          # Test configuration & setup
│       │       ├── pages/
│       │       │   ├── LoginPage.java         # Login functionality
│       │       │   ├── TranscriptPage.java    # Transcript operations
│       │       │   ├── CanvasPage.java        # Canvas calendar
│       │       │   ├── LibraryPage.java       # Library booking
│       │       │   ├── DatasetPage.java       # Dataset access
│       │       │   └── CalendarPage.java      # Academic calendar
│       │       ├── tests/
│       │       │   ├── Scenario1_TranscriptTest.java
│       │       │   ├── Scenario2_CanvasEventsTest.java
│       │       │   ├── Scenario3_LibraryReservationTest.java
│       │       │   ├── Scenario4_DatasetDownloadTest.java
│       │       │   └── Scenario5_AcademicCalendarTest.java
│       │       └── utils/
│       │           ├── ExcelUtils.java        # Excel data handling
│       │           └── TestReporter.java      # Test result reporting
│       └── resources/
│           └── TestData.xlsx                  # Test data (credentials & events)
├── screenshots/                               # Auto-generated screenshots
│   ├── Scenario1_Transcript/
│   ├── Scenario2_CanvasEvents/
│   ├── Scenario3_LibraryRoom/
│   ├── Scenario4_Dataset_Negative/
│   └── Scenario5_AcademicCalendar/
├── target/
│   └── surefire-reports/                      # TestNG reports
├── pom.xml                                     # Maven dependencies
└── ReadMe.md
```

### Design Patterns

**Page Object Model (POM)**
- Each page/component has its own class
- Encapsulates page elements and actions
- Improves maintainability and reusability

**Data-Driven Testing**
- Test data stored in Excel files
- Supports multiple test iterations
- Easy to update test data without code changes

**BaseTest Pattern**
- Centralized test configuration
- WebDriver lifecycle management
- Screenshot utility
- Wait strategies

---

##  Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 11 | Programming Language |
| Maven | 3.x | Build & Dependency Management |
| Selenium WebDriver | 4.15.0 | Browser Automation |
| TestNG | 7.8.0 | Testing Framework |
| Apache POI | 5.2.5 | Excel File Operations |
| WebDriverManager | 5.6.2 | Automatic Driver Management |

---

##  Prerequisites

- **Java Development Kit (JDK) 11** or higher
- **Maven 3.6+** installed and configured
- **Google Chrome** browser (latest version)
- **ChromeDriver** (automatically managed by WebDriverManager)
- **Internet connection** for accessing NEU platforms
- **Valid NEU credentials** for login

---

##  Setup Instructions

### 1. Clone/Download the Project
```bash
cd "\INFO6255-Selenium-Assignment"
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure Test Data
Edit `src/test/resources/TestData.xlsx`:

**Sheet: Credentials**
- Update with valid NEU username and password

**Sheet: EventData**
- Configure event details (Title, Date, Time, Location, etc.)

### 4. Run Tests

**Run All Tests:**
```bash
mvn test
```

**Run Specific Test:**
```bash
mvn test -Dtest=Scenario1_TranscriptTest
mvn test -Dtest=Scenario2_CanvasEventsTest
mvn test -Dtest=Scenario3_LibraryReservationTest
mvn test -Dtest=Scenario4_DatasetDownloadTest
mvn test -Dtest=Scenario5_AcademicCalendarTest
```

**Run from IDE:**
- Right-click on test class → Run as TestNG Test
- Or run `testng.xml` for suite execution

---

##  Test Reports

### Screenshots
- Automatically captured at each test step
- Stored in `screenshots/` folder with timestamps
- Organized by scenario (e.g., `Scenario1_Transcript/`)

### TestNG Reports
- HTML reports generated in `target/surefire-reports/`
- Open `index.html` in browser to view results

### TestReporter Output
- Custom test result tracking
- Consolidated test outcomes with expected vs actual results
- Pass/Fail status for each scenario

---

##  Configuration

### WebDriver Configuration (BaseTest.java)
```java
- Browser: Chrome (configurable)
- Window: Maximized
- Implicit Wait: 10 seconds
- Page Load Timeout: 30 seconds
```

### Excel Test Data (TestData.xlsx)
**Sheet: Credentials**
- Username
- Password

**Sheet: EventData**
- Title
- Date
- Time
- Location
- Calendar (Calendar name)
- Description



---
##  License

This project is created for educational purposes as part of the INFO 6255 course assignment.

---

## 🔄 Future Enhancements

- [ ] Cross-browser testing (Firefox, Edge, Safari)
- [ ] Parallel test execution
- [ ] Integration with CI/CD pipeline
- [ ] Extent Reports for richer reporting
- [ ] API testing integration
- [ ] Database validation
- [ ] Custom test annotations
- [ ] Video recording of test execution
- [ ] Email notifications for test results

---

