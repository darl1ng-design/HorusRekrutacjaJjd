/**
 * Utility class providing normalization and validation methods
 * for string input values used across the application.
 * <p>
 * This class is stateless and cannot be instantiated.
 * All methods are {@code static} and designed for safe,
 * lightweight preprocessing of user-supplied or
 * domain-related strings.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Normalize string input by trimming and lowercasing.</li>
 *     <li>Validate and enforce allowed folder size categories.</li>
 * </ul>
 */

package utils;


public class Util {

    public static String normalize(String input){
        if(input == null) return null;
        String trimmed = input.trim();
        return trimmed.isEmpty() ? null : trimmed.toLowerCase();
    }

    public static String assertAllowedSize(String input){
        return switch(input) {
            case "small", "medium", "large" -> input;
            case null, default -> throw new IllegalArgumentException("Invalid folder size.");
        };
    }
}
