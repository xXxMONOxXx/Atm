package com.mycompany;

public class Attempts {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    private int attemptsLeft;

    public Attempts() {
        attemptsLeft = MAX_NUMBER_OF_ATTEMPTS;
    }

    public boolean decreaseAttempt() {
        attemptsLeft--;
        if (attemptsLeft <= 0) {
            return false;
        } else {
            return true;
        }
    }
    public String get() {
        return String.valueOf(attemptsLeft);
    }

    public boolean set(String value) {
        if (value.length() != 1 || !Character.isDigit(value.charAt(0)) || Integer.parseInt(value) > MAX_NUMBER_OF_ATTEMPTS) {
            return false;
        } else {
            attemptsLeft = Integer.parseInt(value);
            return true;
        }
    }

    public void refresh() {
        attemptsLeft = MAX_NUMBER_OF_ATTEMPTS;
    }
}
