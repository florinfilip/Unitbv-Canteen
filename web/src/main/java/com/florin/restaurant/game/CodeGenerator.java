package com.florin.restaurant.game;

import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.Random;

@UtilityClass
public class CodeGenerator {

    public String generateRewardCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString().toUpperCase(Locale.ROOT);

    }
}
