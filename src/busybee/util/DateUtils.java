package busybee.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    // Common date formatters
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    public static final DateTimeFormatter DISPLAY_DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    /**
     * Formats a LocalDate to string using the standard format (yyyy-MM-dd).
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : "";
    }

    /**
     * Formats a LocalDateTime to string using the standard format (yyyy-MM-dd HH:mm).
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMAT) : "";
    }

    /**
     * Formats a LocalDate for display (MMM dd, yyyy).
     */
    public static String formatDateForDisplay(LocalDate date) {
        return date != null ? date.format(DISPLAY_DATE_FORMAT) : "";
    }

    /**
     * Formats a LocalDateTime for display (MMM dd, yyyy HH:mm).
     */
    public static String formatDateTimeForDisplay(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DISPLAY_DATETIME_FORMAT) : "";
    }

    /**
     * Parses a string to LocalDate using the standard format.
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Parses a string to LocalDateTime using the standard format.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Gets the current date as a formatted string.
     */
    public static String getCurrentDateString() {
        return formatDate(LocalDate.now());
    }

    /**
     * Gets the current date and time as a formatted string.
     */
    public static String getCurrentDateTimeString() {
        return formatDateTime(LocalDateTime.now());
    }

    /**
     * Checks if a date string is valid.
     */
    public static boolean isValidDate(String dateString) {
        return parseDate(dateString) != null;
    }

    /**
     * Checks if a date-time string is valid.
     */
    public static boolean isValidDateTime(String dateTimeString) {
        return parseDateTime(dateTimeString) != null;
    }
}