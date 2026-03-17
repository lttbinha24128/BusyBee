package busybee.util;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Email regex pattern
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Phone number regex pattern (US format)
    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$");

    // Zip code regex pattern (US)
    private static final Pattern ZIP_PATTERN =
        Pattern.compile("^\\d{5}(-\\d{4})?$");

    /**
     * Validates if a string is not null and not empty after trimming.
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validates if a string has a minimum length.
     */
    public static boolean hasMinimumLength(String value, int minLength) {
        return value != null && value.trim().length() >= minLength;
    }

    /**
     * Validates if a string has a maximum length.
     */
    public static boolean hasMaximumLength(String value, int maxLength) {
        return value != null && value.trim().length() <= maxLength;
    }

    /**
     * Validates if a string length is within a range.
     */
    public static boolean hasLengthInRange(String value, int minLength, int maxLength) {
        return hasMinimumLength(value, minLength) && hasMaximumLength(value, maxLength);
    }

    /**
     * Validates if a string is a valid email address.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates if a string is a valid phone number.
     */
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validates if a string is a valid US zip code.
     */
    public static boolean isValidZipCode(String zipCode) {
        return zipCode != null && ZIP_PATTERN.matcher(zipCode).matches();
    }

    /**
     * Validates if a double value is positive.
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }

    /**
     * Validates if a double value is non-negative (zero or positive).
     */
    public static boolean isNonNegative(double value) {
        return value >= 0;
    }

    /**
     * Validates if an integer value is positive.
     */
    public static boolean isPositive(int value) {
        return value > 0;
    }

    /**
     * Validates if an integer value is non-negative.
     */
    public static boolean isNonNegative(int value) {
        return value >= 0;
    }

    /**
     * Validates if a string contains only alphabetic characters and spaces.
     */
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("[a-zA-Z\\s]+");
    }

    /**
     * Validates if a string contains only alphanumeric characters.
     */
    public static boolean isAlphanumeric(String value) {
        return value != null && value.matches("[a-zA-Z0-9]+");
    }

    /**
     * Validates if a string contains only digits.
     */
    public static boolean isNumeric(String value) {
        return value != null && value.matches("\\d+");
    }

    /**
     * Creates a validation error message for required fields.
     */
    public static String createRequiredFieldError(String fieldName) {
        return fieldName + " is required.";
    }

    /**
     * Creates a validation error message for invalid format.
     */
    public static String createInvalidFormatError(String fieldName) {
        return fieldName + " has an invalid format.";
    }

    /**
     * Creates a validation error message for length constraints.
     */
    public static String createLengthError(String fieldName, int minLength, int maxLength) {
        return fieldName + " must be between " + minLength + " and " + maxLength + " characters.";
    }

    /**
     * Creates a validation error message for numeric constraints.
     */
    public static String createNumericError(String fieldName, String constraint) {
        return fieldName + " must be " + constraint + ".";
    }
}