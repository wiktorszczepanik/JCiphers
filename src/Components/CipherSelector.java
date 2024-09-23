package Components;

import Ciphers.BaseCrypt;
import Ciphers.Symetric.Rot13;
import Constants.Flags.ActionTypes;
import Constants.Flags.CipherTypes;
import Constants.Messages;
import Exceptions.FlagExcpetion;
import Structures.FlagTuple;

import java.util.List;

public class CipherSelector {

    private final String cipherType;
    private final byte options;
    Messages messages = Messages.getInstance();

    public CipherSelector(FlagTuple<ActionTypes, String> cipherType, byte options) {
        this.cipherType = cipherType.value;
        this.options = options;
    }

    public BaseCrypt select(List<FlagTuple<ActionTypes, String>> flags) throws FlagExcpetion {
        return switch (CipherTypes.valueOf(cipherType)) {
            case CipherTypes.ROT13 -> new Rot13(options, flags);
            // future ciphers ...
        };
    }

}
