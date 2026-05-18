package com.nurlansuleymanli.cardservice.util;

import java.util.Random;

public class CardNumberGenerator {

    private static final String number= "54117388";
    private static final Random random= new Random();


    public static String generate(){
        StringBuilder cardNumber= new StringBuilder(number);

        for (int i = 0; i < 8; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }

    public static String generateCvv(){

        return String.valueOf(random.nextInt(100,999));

    }




}
