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
            default -> throw new IllegalArgumentException("Invalid folder size.");
        };
    }
}
