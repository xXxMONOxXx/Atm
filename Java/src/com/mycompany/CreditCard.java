package com.mycompany;

import com.mycompany.Balance;
import com.mycompany.Attempts;
import com.mycompany.CardNumber;
import com.mycompany.CardPin;

public class CreditCard {

    private CardNumber number = new CardNumber();

    private CardPin pin = new CardPin();

    private Attempts attempts = new Attempts();

    public Balance balance = new Balance();

    public boolean status = true;

    CreditCard(String numberValue, String pinValue, String balanceValue, String attemptsValue) {
        if (!number.setNumber(numberValue)) {
            status = false;
        }
        if (!pin.setPin(pinValue)) {
            status = false;
        }
        if (!Balance.isValidBalance(balanceValue)) {
            status = false;
        } else {
            balance.increaseBalance(balanceValue);
        }
        if (!attempts.set(attemptsValue)) {
            status = false;
        }
    }

    public String getCreditCard() {
        return String.format("%s %s %s %s", number.getNumber(), pin.getPin(),balance.getBalance(),attempts.get());
    }

    public String getAttemptsLeft() {
        return attempts.get();
    }

    public String getPin() {
        return pin.getPin();
    }

    public boolean equalNumbers(String value) {
        return number.equals(value);
    }

    public boolean decreaseAttempts() {
        return attempts.decreaseAttempt();
    }

    public void refreshAttempts() {
        attempts.refresh();
    }

}
