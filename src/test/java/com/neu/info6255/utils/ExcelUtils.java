package com.neu.info6255.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtils {

    private static final String EXCEL_PATH = "src/test/resources/TestData.xlsx";

    /**
     * Get login credentials from LoginData sheet
     */
    public static Map<String, String> getLoginCredentials() {
        Map<String, String> credentials = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("LoginData");
            if (sheet == null) {
                throw new RuntimeException("LoginData sheet not found");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found");
            }

            int usernameCol = -1;
            int passwordCol = -1;

            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim();
                if (header.equalsIgnoreCase("Username")) {
                    usernameCol = cell.getColumnIndex();
                } else if (header.equalsIgnoreCase("Password")) {
                    passwordCol = cell.getColumnIndex();
                }
            }

            if (usernameCol == -1 || passwordCol == -1) {
                throw new RuntimeException("Username or Password column not found");
            }

            Row dataRow = sheet.getRow(1);
            if (dataRow == null) {
                throw new RuntimeException("No login data found");
            }

            String username = getCellValueAsString(dataRow.getCell(usernameCol));
            String password = getCellValueAsString(dataRow.getCell(passwordCol));

            credentials.put("Username", username);
            credentials.put("Password", password);

            System.out.println("✓ Loaded credentials for: " + username);

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel: " + e.getMessage(), e);
        }

        return credentials;
    }

    /**
     * Get test data from any sheet (like EventData)
     */
    public static List<Map<String, String>> getTestData(String sheetName) {
        List<Map<String, String>> testDataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException(sheetName + " sheet not found");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in " + sheetName);
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = getCellValueAsString(cell);
                    rowData.put(headers.get(j), value);
                }

                testDataList.add(rowData);
            }

            System.out.println("✓ Loaded " + testDataList.size() + " rows from " + sheetName);

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel: " + e.getMessage(), e);
        }

        return testDataList;
    }

    /**
     * Get cell value as String - HANDLES DATES AND TIMES PROPERLY
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Handle date/time cells
                    Date dateValue = cell.getDateCellValue();

                    // Get the format string from the cell
                    String formatString = cell.getCellStyle().getDataFormatString();

                    // Determine if it's a date or time based on format
                    if (formatString.contains("h") || formatString.contains("m") ||
                            formatString.contains("AM") || formatString.contains("PM")) {
                        // It's a time field
                        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                        return timeFormat.format(dateValue);
                    } else {
                        // It's a date field
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        return dateFormat.format(dateValue);
                    }
                } else {
                    // Regular number
                    double numValue = cell.getNumericCellValue();
                    // Check if it's a whole number
                    if (numValue == (long) numValue) {
                        return String.valueOf((long) numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            case BLANK:
                return "";

            default:
                return "";
        }
    }

    /**
     * Check if a row is empty
     */
    private static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }

        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = getCellValueAsString(cell);
                if (!value.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}