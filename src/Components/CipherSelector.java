package Components;

import Ciphers.Symetric.*;
import Ciphers.Unsimetric.Rsa;
import Ciphers.UtilCipher;
import Constants.Flags.ActionTypes;
import Constants.Flags.CipherTypes;
import Constants.Messages;
import Exceptions.FlagException;
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

    public UtilCipher select(List<FlagTuple<ActionTypes, String>> flags) throws FlagException {
        return switch (CipherTypes.valueOf(cipherType)) {
            case CipherTypes.ROT13 -> new Rot13(options, flags);
            case CipherTypes.CEZAR -> new Cezar(options, flags);
            case CipherTypes.VERNAM -> new Vernam(options, flags);
            case CipherTypes.DES -> new Des(options, flags);
            case CipherTypes.RSA -> new Rsa(options, flags);
            // future ciphers ...
        };
    }

}
