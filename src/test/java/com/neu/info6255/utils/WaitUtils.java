package com.neu.info6255.utils;

public class WaitUtils {

    /**
     * Sleep for specified milliseconds
     * Handles InterruptedException internally - no need to catch
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("⚠️  Sleep interrupted: " + e.getMessage());
        }
    }

    /**
     * Sleep with a custom message
     */
    public static void sleep(long milliseconds, String message) {
        if (message != null && !message.isEmpty()) {
            System.out.println(message);
        }
        sleep(milliseconds);
    }

    /**
     * Sleep in seconds instead of milliseconds
     */
    public static void sleepSeconds(int seconds) {
        sleep(seconds * 1000L);
    }
}