package com.gmail.ykomarnytskyi2022.CartaLingua.util;

public class TextNormalizer {
    private TextNormalizer() {}

    public static String normalize(String str) {
        String result;
        if (isNullOrEmptyOrBlank(str) || isNullOrEmptyOrBlank((result = removeDisallowedSpecChar(str)))) {
            throw new IllegalArgumentException("Argument str cannot be null or blank");
        }
        return result;
    }

    public static String removeDisallowedSpecChar(String str) {
        return str.replaceAll("[^\\p{L}\\s'’\\-·]", "")
                .replaceAll("\\s{2,}", " ")
                .strip();
    }

    public static boolean isNullOrEmptyOrBlank(String str) {
        return (str == null || str.isEmpty() || str.isBlank());
    }
}
