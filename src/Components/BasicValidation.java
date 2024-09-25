package Components;

import Constants.Messages;
import Exceptions.FileException;
import Exceptions.FlagException;

public class BasicValidation {

    public static void flagsNumber(String[] flags, Messages messages) throws FlagException {
        if (flags.length < 3)
            throw new FlagException(messages.get("err.flg.num.sml"));
        if (flags.length > 8)
            throw new FlagException(messages.get("err.flg.num.big"));
    }
}
