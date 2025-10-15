package utils;

import java.util.Locale;

public class Util {

    public static String normalize(String input){
        if(input == null) return null;
        String trimmed = input.trim();
        return trimmed.isEmpty() ? null : trimmed.toLowerCase(Locale.ROOT);
    }
}
