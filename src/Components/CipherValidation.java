package Components;

import Constants.Flags.ActionTypes;
import Constants.Flags.CipherTypes;
import Constants.Messages;
import Exceptions.FlagException;
import Structures.FlagTuple;

public class CipherValidation {

    private final String cipherType;
    private final byte optionsTemplate;
    private final byte options;
    Messages messages = Messages.getInstance();

    public CipherValidation(FlagTuple<ActionTypes, String> cipherType, FlagTuple<ActionTypes, String> optionsTemplate, byte options)
        throws FlagException {
        this.cipherType = cipherType.value;
        this.optionsTemplate = cleanAction(optionsTemplate.flag.getShortFlag());
        this.options = options;
    }

    private byte cleanAction(String actionFlag) throws FlagException {
        CipherTypes type;
        try { type = CipherTypes.valueOf(cipherType);
        } catch (IllegalArgumentException iae) {
            throw new FlagException(messages.get("err.flg.typ.typ"));
        }
        char flag = actionFlag.charAt(1);
        return switch (flag) {
            case 'e' -> type.getEncryptionOptions();
            case 'd' -> type.getDecryptionOptions();
            case 'g' -> type.getGenerateOptions();
            default -> throw new FlagException(messages.get("err.flg.typ.act"));
        };
    }

    public void validate() throws FlagException {
        // TODO: Check why throwing error does not work.
        byte checker = (byte) (optionsTemplate ^ options);
        if (checker > 1) throw new FlagException(messages.get("err.flg.typ.bas"));
    }
}
