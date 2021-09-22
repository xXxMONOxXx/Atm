package com.mycompany;

public class CardNumber {

    private static final int SIZE_OF_NUMBER = 19;

    private static final int[] INDEXES_OF_DASH = {4, 9, 14};

    private final char[] number = new char[SIZE_OF_NUMBER];

    public static boolean valid(String value) {
        return isCardNumber(value);
    }

    public String getNumber() {
        return String.valueOf(number);
    }

    public boolean equals(String value) {
        return value.equals(getNumber());
    }

    public static boolean isCardNumber(String value) {
        if (value.length() != SIZE_OF_NUMBER) {
            return false;
        } else {
            int numberOfDash = 0;
            for (int i = 0; i < SIZE_OF_NUMBER; i++) {
                if (!Character.isDigit(value.charAt(i))) {
                    if (value.charAt(i) != '-') {
                        return false;
                    } else if (i != INDEXES_OF_DASH[numberOfDash]) {
                        return false;
                    } else {
                        numberOfDash++;
                    }
                }
            }
            return true;
        }
    }

    public boolean setNumber(String value) {
        if (!isCardNumber(value)) {
            return false;
        } else {
            for (int i = 0; i < SIZE_OF_NUMBER; i++) {
                number[i] = value.charAt(i);
            }
            return true;
        }
    }

}
