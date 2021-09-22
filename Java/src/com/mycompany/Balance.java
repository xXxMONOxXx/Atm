package com.mycompany;

import com.mycompany.ParseUnit;

public class Balance extends ParseUnit {

    public static final int UPPER_LIMIT = 1000000;

    public static final int LOWER_LIMIT = 0;

    private int balance;

    public Balance() {
        balance = LOWER_LIMIT;
    }

    public String getBalance() {
        return String.valueOf(balance);
    }

    public boolean decreaseBalance(String value) {
        if (balance - Integer.parseInt(value) < LOWER_LIMIT) {
            return false;
        } else {
            balance -= Integer.parseInt(value);
            return true;
        }
    }

    public static boolean isValidBalance(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        if (!tryParseInt(value)) {
            return false;
        } else {
            int intValue = Integer.parseInt(value);
            if (intValue > UPPER_LIMIT || intValue < LOWER_LIMIT) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean increaseBalance(String value) {
        if (balance + Integer.parseInt(value) > UPPER_LIMIT) {
            return false;
        } else {
            balance += Integer.parseInt(value);
            return true;
        }
    }

}
