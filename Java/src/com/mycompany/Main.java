package com.mycompany;

import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main extends ParseUnit {

    public static String getNewCardNumber() {
        String newCardNumber;
        Scanner read = new Scanner(System.in);
        newCardNumber = read.nextLine();
        while (!CardNumber.valid(newCardNumber)) {
            System.out.print("Wrong input, please try again: ");
            newCardNumber = read.nextLine();
        }
        return newCardNumber;
    }

    public static boolean wantToContinue() {
        System.out.println("Do you want to continue (y/n)?");
        Scanner read = new Scanner(System.in);
        String answer = read.nextLine();
        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.println("Wrong input, please try again: ");
            answer = read.nextLine();
        }
        return answer.equals("y");
    }

    public static String getNumber() {
        System.out.println("Enter the amount:");
        String number;
        Scanner read = new Scanner(System.in);
        number = read.nextLine();
        while (!tryParseInt(number)) {
            System.out.println("Wrong input, please try again.");
            number = read.nextLine();
        }
        return number;
    }

    public static AtmCommands.COMMANDS getCommand() {
        String command;
        System.out.println("Available operations:");
        System.out.println("Out - out your card's balance,");
        System.out.println("TopUp - put money on the card,");
        System.out.println("Withdraw - withdraw money from your card.");
        System.out.println("Enter operation :");
        Scanner read = new Scanner(System.in);
        command = read.nextLine();
        while (AtmCommands.commandType(command) == AtmCommands.COMMANDS.Invalid) {
            System.out.println("Invalid command, please try again: ");
            command = read.nextLine();
        }
        return AtmCommands.commandType(command);
    }

    public static String getPin() {
        Scanner read = new Scanner(System.in);
        return read.nextLine();
    }

    public static void startingScreen() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        Date dateTimeNow = new Date();
        dateTimeNow.toInstant();
        System.out.println("Welcome to our ATM.");
        System.out.println("Current date and time: " + formatter.format(dateTimeNow));
    }

    public static void endingScreen() {
        System.out.println("Thanks for using our bank. Have a good day.");
    }

    public static void useCommand(Atm atm, AtmCommands.COMMANDS command, String cardNumber) {
        switch (command) {
            case Out: {
                System.out.println(atm.out(cardNumber));
                break;
            }
            case TopUp: {
                if (!atm.topUp(cardNumber, getNumber())) {
                    System.out.println("Operation is not possible (not enough balance on card or ATM is full).");
                } else {
                    System.out.println("Operation was successful.");
                }
                break;
            }
            case Withdraw: {
                if (!atm.withdraw(cardNumber, getNumber())) {
                    System.out.println("Operation is not possible (balance will be over the limits or ATM doesn't have enough money).");
                } else {
                    System.out.println("Operation was successful.");
                }
                break;
            }
        }
    }

    public static Pair getCard(Atm atm) {
        String cardNumber = "";
        boolean enteringCard = true;
        while (enteringCard) {
            System.out.print("Enter your credit card number: ");
            cardNumber = getNewCardNumber();
            while (atm.getIndexOfCard(cardNumber) == -1) {
                System.out.print("There isn't any card with such number in our data base. Please, try again: ");
                cardNumber = getNewCardNumber();
            }
            if (atm.cardIsBlocked(cardNumber)) {
                System.out.println("This credit card is blocked until " + atm.expirationDate(cardNumber) + '.');
                enteringCard = wantToContinue();
                cardNumber = "";
            } else {
                break;
            }
        }
        Pair<String, Boolean> pair = new Pair<String, Boolean>(cardNumber, enteringCard);
        return pair;
    }

    public static void operations(Atm atm, String cardNumber, boolean continueOperations) {
        while (continueOperations) {
            boolean status = false;
            while (!atm.attemptsOfCard(cardNumber).equals("0") && continueOperations) {
                System.out.print("Enter pin code (Attempts left: " + atm.attemptsOfCard(cardNumber) + "): ");
                String pin = getPin();
                if (!atm.correctPin(cardNumber, pin)) {
                    if (!atm.decreaseAttempts(cardNumber)) {
                        System.out.println("You have ran out of attempts. Blocking this card for one day.");
                        atm.blockCard(cardNumber);
                        atm.save();
                        return;
                    } else {
                        atm.save();

                    }
                } else {
                    status = true;
                    break;
                }
                System.out.println("Wrong pin code.");
                continueOperations = wantToContinue();
            }
            if (status) {
                atm.refreshAttempts(cardNumber);
                AtmCommands.COMMANDS command = getCommand();
                useCommand(atm, command, cardNumber);
            }
            if (continueOperations) {
                continueOperations = wantToContinue();
            }
        }
    }

    public static void main(String[] args) {
        startingScreen();
        Atm atm = new Atm();
        if (!atm.status) {
            System.out.println("Sorry, ATM is not available right now (Couldn't reach data base of " + atm.Error() + "). Try again later.");
        } else {
            System.out.println("This atm has: " + atm.getBalance());
            Pair<String, Boolean> numberAndBool = getCard(atm);
            operations(atm, numberAndBool.getKey(), numberAndBool.getValue());
            atm.save();
        }
        endingScreen();
        try {
            TimeUnit.SECONDS.sleep(10);
        }
        catch (InterruptedException e){

        }
        }
}