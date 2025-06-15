package com.ticketer.ticketing.util;

import java.security.SecureRandom;

public class RandomNumber {

    private static final SecureRandom random = new SecureRandom();

    /**
     * 랜덤 숫자 조합 생성
     * @param length 생성할 자릿수
     * @return 랜덤 숫자 조합
     */
    public static String generateNumber(int length) {
        StringBuilder code = new StringBuilder();

        for(int i = 0 ; i < length ; i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
