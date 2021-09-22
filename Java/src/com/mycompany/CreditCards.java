package com.mycompany;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CreditCards {

    private static final String SPACE = " ";

    private ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();

    private static final int NUMBER_OF_PARAMETERS = 4;

    private boolean parseToArrayList(String line) {
        String[] arrayOfStrings = line.split(SPACE);
        int sizeOfArray = arrayOfStrings.length;
        if (sizeOfArray % NUMBER_OF_PARAMETERS != 0) {
            return false;
        } else {
            try {
                for (int i = 0; i < sizeOfArray; i += NUMBER_OF_PARAMETERS) {
                    CreditCard newCard = new CreditCard(arrayOfStrings[i], arrayOfStrings[i + 1], arrayOfStrings[i + 2], arrayOfStrings[i + 3]);
                    if (!newCard.status) {
                        return false;
                    }
                    creditCards.add(newCard);
                }
                return true;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
    }

    public boolean load() { // true - success, false - error occurred
        try {
            //ClassLoader input = Main.class.getClassLoader("resources/com/mycompany/resources/CreditCardsInfo.txt");
            File inputFile = new File("resources\\com\\mycompany\\CreditCardsInfo.txt");
            Scanner reader = new Scanner(inputFile);
            try {
                if (!parseToArrayList(reader.nextLine().trim())) {
                    reader.close();
                    return false;
                } else {
                    reader.close();
                    return true;
                }
            }
            catch (NoSuchElementException e){
                return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public int getNumberOfCards() {
        return creditCards.size();
    }

    public int getIndexOfCard(String value) { // -1 - invalid card number
        for (int i = 0; i < getNumberOfCards(); i++) {
            if (creditCards.get(i).equalNumbers(value)) {
                return i;
            }
        }
        return -1;
    }

    public CreditCard getCardAt(int index) {
        return creditCards.get(index);
    }

    public boolean save() { // true - success, false - error occurred
        try {
            FileWriter outputFile = new FileWriter("resources\\com\\mycompany\\CreditCardsInfo.txt");
            for (int i = 0; i < getNumberOfCards(); i++) {
                outputFile.write(creditCards.get(i).getCreditCard());
                outputFile.write(SPACE);
            }
            outputFile.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
