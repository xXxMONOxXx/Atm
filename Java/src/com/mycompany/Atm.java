package com.mycompany;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Atm {

    private Balance balance = new Balance();
    private CreditCards creditCards = new CreditCards();
    private BlockedCards blockedCards = new BlockedCards();

    public boolean status = true;

    private int indexOfError = 0;

    private static final String[] ERRORS = {"OK", "Credit cards", "Blocked cards", "ATM"};

    Atm() {
        if (!creditCards.load()) {
            indexOfError = 1;
            status = false;
        }
        if (!blockedCards.load()) {
            indexOfError = 2;
            status = false;
        }
        if (!load()) {
            indexOfError = 3;
            status = false;
        }
    }

    public String Error() {
        return ERRORS[indexOfError];
    }

    private boolean parseToBalance(String value) {
        if (!Balance.isValidBalance(value)) {
            return false;
        } else {
            balance.increaseBalance(value);
            return true;
        }
    }

    public String getBalance() {
        return balance.getBalance();
    }

    private boolean load() {
        try {
            File inputFile = new File("resources\\com\\mycompany\\AtmInfo.txt");
            Scanner reader = new Scanner(inputFile);
            if (!parseToBalance(reader.nextLine().trim())) {
                return false;
            } else {
                reader.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean save() {
        if (!creditCards.save()) {
            indexOfError = 1;
            return false;
        }
        if (!blockedCards.save()) {
            indexOfError = 2;
            return false;
        }
        try {
            FileWriter outputFile = new FileWriter("resources\\com\\mycompany\\AtmInfo.txt");
            outputFile.write(balance.getBalance());
            outputFile.close();
            return true;
        } catch (IOException e) {
            indexOfError = 3;
            return false;
        }
    }

    public String attemptsOfCard(String number) {
        return creditCards.getCardAt(creditCards.getIndexOfCard(number)).getAttemptsLeft();
    }

    public int getIndexOfCard(String number) {
        return creditCards.getIndexOfCard(number);
    }

    public boolean decreaseAttempts(String number) {
        if (creditCards.getIndexOfCard(number) == -1) {
            return false;
        } else {
            return creditCards.getCardAt(creditCards.getIndexOfCard(number)).decreaseAttempts();
        }
    }

    public boolean refreshAttempts(String number) {
        if (creditCards.getIndexOfCard(number) == -1) {
            return false;
        } else {
            creditCards.getCardAt(creditCards.getIndexOfCard(number)).refreshAttempts();
            return true;
        }
    }

    public boolean blockCard(String number) {
        return blockedCards.add(number);
    }

    public boolean correctPin(String number, String pin) {
        return creditCards.getCardAt(getIndexOfCard(number)).getPin().equals(pin);
    }

    public boolean cardIsBlocked(String number) {

        if (blockedCards.findIndexOfBlockCardByNumber(number) != -1) {
            if (!blockedCards.blockExpired(number)) {
                return true;
            } else {
                refreshAttempts(number);
            }
        }
        return false;
    }

    public String expirationDate(String number) {
        return blockedCards.dateAt(blockedCards.findIndexOfBlockCardByNumber(number));
    }

    public String out(String number) {
        return creditCards.getCardAt(creditCards.getIndexOfCard(number)).balance.getBalance();
    }

    public boolean topUp(String number, String value) {
        int amount = Integer.parseInt(value);
        int atmBalance = Integer.parseInt(balance.getBalance());
        int cardBalance = Integer.parseInt(creditCards.getCardAt(creditCards.getIndexOfCard(number)).balance.getBalance());
        if (atmBalance - amount < Balance.LOWER_LIMIT || cardBalance + amount > Balance.UPPER_LIMIT) {
            return false;
        } else {
            balance.decreaseBalance(value);
            creditCards.getCardAt(creditCards.getIndexOfCard(number)).balance.increaseBalance(value);
            return true;
        }
    }

    public boolean withdraw(String number, String value) {
        int amount = Integer.parseInt(value);
        int atmBalance = Integer.parseInt(balance.getBalance());
        int cardBalance = Integer.parseInt(creditCards.getCardAt(creditCards.getIndexOfCard(number)).balance.getBalance());
        if (atmBalance + amount > Balance.UPPER_LIMIT || cardBalance - amount < Balance.LOWER_LIMIT) {
            return false;
        } else {
            balance.increaseBalance(value);
            creditCards.getCardAt(creditCards.getIndexOfCard(number)).balance.decreaseBalance(value);
            return true;
        }
    }

}
