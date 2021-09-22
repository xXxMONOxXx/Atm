package com.mycompany;

public class CardPin {

    private static final int SIZE_OF_PIN = 4;

    private final char[] pin = new char[SIZE_OF_PIN];

    public boolean setPin(String value) {
        if (!validPin(value)) {
            return false;
        } else {
            for (int i = 0; i < SIZE_OF_PIN; i++) {
                pin[i] = value.charAt(i);
            }
            return true;
        }
    }

    public static boolean validPin(String value) {
        if (value.length() != SIZE_OF_PIN) {
            return false;
        } else {
            for (int i = 0; i < SIZE_OF_PIN; i++) {
                if (!Character.isDigit(value.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public String getPin() {
        return String.valueOf(pin);
    }


}
