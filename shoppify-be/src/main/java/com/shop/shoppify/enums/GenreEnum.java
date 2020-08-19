package com.shop.shoppify.enums;

public enum GenreEnum {
    MALE, FEMALE;

    public static boolean checkGenre(String str) {
        for (GenreEnum en : GenreEnum.values()) {
            if (en.name().equalsIgnoreCase(str))
                return true;
        }
        return false;
    }
}
