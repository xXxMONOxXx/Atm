package com.mycompany;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BlockedCards {

    ArrayList<BlockedCard> blockedCards = new ArrayList<BlockedCard>();

    private static final int NUMBER_OF_PARAMETERS = 3;

    private static final String SPACE = " ";

    private static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public BlockedCards() {
    }

    public int size() {
        return blockedCards.size();
    }

    public boolean add(String value) {
        if (!CardNumber.valid(value)) {
            return false;
        } else {
            Date dateTimeNow = new Date();
            Date dateTimeTomorrow = new Date(dateTimeNow.getTime() + MILLISECONDS_IN_DAY);
            dateTimeTomorrow.toInstant();
            blockedCards.add(new BlockedCard(value, formatter.format(dateTimeTomorrow)));
            return true;
        }
    }

    public int findIndexOfBlockCardByNumber(String value) {
        for (int i = 0; i < size(); i++) {
            if (blockedCards.get(i).number().equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public String dateAt(int index) {
        if (index >= size()) {
            return null;
        } else {
            return formatter.format(blockedCards.get(index).date());
        }
    }

    private boolean parseToBlockedCards(String value) {
        String[] arrayOfStrings = value.split(SPACE);
        int sizeOfArray = arrayOfStrings.length;
        try {
            for (int i = 0; i < sizeOfArray; i += NUMBER_OF_PARAMETERS) {
                blockedCards.add(new BlockedCard(arrayOfStrings[i], String.format("%s %s",arrayOfStrings[i + 1],arrayOfStrings[i + 2])));
                if (!CardNumber.valid(blockedCards.get(size() - 1).number())
                        || blockedCards.get(size() - 1).nullDate()) {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public boolean load() {
        try {
            File inputFile = new File("resources\\com\\mycompany\\BlockedCardsInfo.txt");
            Scanner reader = new Scanner(inputFile);
            try {
                boolean successOrNot = parseToBlockedCards(reader.nextLine().trim());
                reader.close();
                return successOrNot;
            }
            catch (NoSuchElementException e){
                reader.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean save() {
        try {
            FileWriter outputFile = new FileWriter("resources\\com\\mycompany\\BlockedCardsInfo.txt");
            for (int i = 0; i < size(); i++) {
                outputFile.write(blockedCards.get(i).getInfo());
                outputFile.write(SPACE);
            }
            outputFile.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean blockExpired(String number) {
        int index = findIndexOfBlockCardByNumber(number);
        if (index == -1) {
            return true;
        } else {
            Date dateTimeNow = new Date();
            dateTimeNow.toInstant();
            if (dateTimeNow.compareTo(blockedCards.get(index).date()) >= 0) {
                blockedCards.remove(index);
                return true;
            } else{
                return false;
            }
        }
    }

}
