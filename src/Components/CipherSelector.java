package Components;

import Ciphers.Crypt;
import Ciphers.Symetric.Rot13;
import Constants.Flags.ActionTypes;
import Constants.Flags.CipherTypes;
import Constants.Messages;
import Exceptions.FlagExcpetion;
import Structures.FlagTuple;

public class CipherSelector {

    private final String cipherType;
    private final byte options;
    Messages messages = Messages.getInstance();

    public CipherSelector(FlagTuple<ActionTypes, String> cipherType, byte options) {
        this.cipherType = cleanCipherType(cipherType.value);
        this.options = options;
    }

    private String cleanCipherType(String type) {
        return type.trim().toUpperCase();
    }

    public Crypt select() throws FlagExcpetion {
        return switch (CipherTypes.valueOf(cipherType)) {
            case CipherTypes.ROT13 -> new Rot13(options);
            // future ciphers ...
        };
    }

}
