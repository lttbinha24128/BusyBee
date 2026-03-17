package busybee.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberUtils {

    // Currency formatter for USD
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

    // Decimal formatter for general numbers
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    // Percentage formatter
    private static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("#0.00%");

    /**
     * Formats a double as currency (e.g., $1,234.56).
     */
    public static String formatCurrency(double amount) {
        return CURRENCY_FORMAT.format(amount);
    }

    /**
     * Formats a double as currency without the dollar sign (e.g., 1,234.56).
     */
    public static String formatCurrencyAmount(double amount) {
        return DECIMAL_FORMAT.format(amount);
    }

    /**
     * Formats a double as percentage (e.g., 12.34%).
     */
    public static String formatPercentage(double value) {
        return PERCENTAGE_FORMAT.format(value / 100.0);
    }

    /**
     * Formats a double with specified decimal places.
     */
    public static String formatDecimal(double value, int decimalPlaces) {
        String pattern = "#,##0." + "0".repeat(Math.max(0, decimalPlaces));
        DecimalFormat formatter = new DecimalFormat(pattern);
        return formatter.format(value);
    }

    /**
     * Parses a currency string to double.
     * Accepts formats like "$1,234.56" or "1234.56".
     */
    public static Double parseCurrency(String currencyString) {
        if (currencyString == null || currencyString.trim().isEmpty()) {
            return null;
        }

        try {
            // Remove currency symbol and commas
            String cleanString = currencyString.replaceAll("[$,]", "").trim();
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parses a percentage string to double.
     * Accepts formats like "12.34%" or "0.1234".
     */
    public static Double parsePercentage(String percentageString) {
        if (percentageString == null || percentageString.trim().isEmpty()) {
            return null;
        }

        try {
            String cleanString = percentageString.replace("%", "").trim();
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if a string represents a valid number.
     */
    public static boolean isValidNumber(String numberString) {
        if (numberString == null || numberString.trim().isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(numberString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Rounds a double to specified decimal places.
     */
    public static double round(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.round(value * factor) / factor;
    }

    /**
     * Safely adds two Double values, handling nulls.
     */
    public static Double safeAdd(Double a, Double b) {
        if (a == null && b == null) return 0.0;
        if (a == null) return b;
        if (b == null) return a;
        return a + b;
    }

    /**
     * Safely subtracts two Double values, handling nulls.
     */
    public static Double safeSubtract(Double a, Double b) {
        if (a == null && b == null) return 0.0;
        if (a == null) return -b;
        if (b == null) return a;
        return a - b;
    }
}