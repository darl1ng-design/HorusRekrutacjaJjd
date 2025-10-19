package utils;

/**
 * Utility class providing common helper methods for string normalization
 * and input validation used across the application.
 *
 * <p>This class is stateless and thread-safe. It offers basic operations
 * for standardizing input and verifying allowed folder size values.</p>
 *
 * <p>All methods are static and can be used without creating an instance.</p>
 *
 * @author Adam Gyurjyan
 * @version 1.0
 * @since 1.0
 */


public class Util {

    /**
     * Normalizes a string by trimming whitespace and converting it to lowercase.
     *
     * <p>If the input is {@code null} or contains only whitespace,
     * the method returns {@code null}.</p>
     *
     * @param input the input string to normalize
     * @return the normalized string in lowercase, or {@code null} if input is {@code null} or blank
     * @implNote This method is commonly used to make string comparisons case-insensitive.
     */
    public static String normalize(String input){
        if(input == null) return null;
        String trimmed = input.trim();
        return trimmed.isEmpty() ? null : trimmed.toLowerCase();
    }

    /**
     * Validates whether the provided folder size value is allowed.
     *
     * <p>Permitted values are {@code "small"}, {@code "medium"}, and {@code "large"}.
     * If the value is invalid or {@code null}, the method throws an {@link IllegalArgumentException}.</p>
     *
     * @param input the folder size value to validate
     * @return the same value if it is valid
     * @throws IllegalArgumentException if the input is {@code null} or not one of the allowed values
     * @implSpec This method uses a {@code switch} expression for compactness and strict validation.
     */
    public static String assertAllowedSize(String input){
        return switch(input) {
            case "small", "medium", "large" -> input;
            case null, default -> throw new IllegalArgumentException("Invalid folder size.");
        };
    }
}
