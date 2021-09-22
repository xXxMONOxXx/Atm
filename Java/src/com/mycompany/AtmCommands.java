package com.mycompany;

public class AtmCommands {
    enum COMMANDS {
        Invalid,
        Out,
        TopUp,
        Withdraw
    }

    public static COMMANDS commandType(String value) {
        switch (value) {
            case "Out":
                return COMMANDS.Out;
            case "TopUp":
                return COMMANDS.TopUp;
            case "Withdraw":
                return COMMANDS.Withdraw;
            default:
                return COMMANDS.Invalid;
        }
    }
}
