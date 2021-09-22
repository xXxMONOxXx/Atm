package com.mycompany;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BlockedCard {

    private CardNumber cardNumber = new CardNumber();

    private Date blockDate;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    BlockedCard(String numberValue, String dateValue) {
        if (!cardNumber.setNumber(numberValue)) {
            cardNumber = null;
        }
        try {
            blockDate = formatter.parse(dateValue);
        } catch (ParseException e) {
            cardNumber = null;
        }
    }

    public boolean nullDate() {
        return blockDate == null;
    }

    public String getInfo() {
        return String.format("%s %s",cardNumber.getNumber(),formatter.format(blockDate));
    }

    public String number() {
        return cardNumber.getNumber();
    }

    public Date date() {
        return blockDate;
    }
}
