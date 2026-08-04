package com.nurlansuleymanli.cardservice.util;

import java.security.SecureRandom;

public class CardNumberGenerator {

    private static final String number= "54117388";
    private static final SecureRandom random= new SecureRandom();


    public static String generate(){
        StringBuilder cardNumber= new StringBuilder(number);

        for (int i = 0; i < 8; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }

    public static String generateCvv(){

        return String.format("%03d", random.nextInt(1000));

    }




}
