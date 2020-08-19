package com.shop.shoppify.util;

import java.util.UUID;

public class TokenUtil {

    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }
}
