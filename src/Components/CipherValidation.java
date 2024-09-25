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
        } catch (IllegalArgumentException exception) {
            throw new FlagException(messages.get("err.flg.typ.typ"));
        }
        char flag = extractActionFlag(actionFlag);
        return switch (flag) {
            case 'e' -> type.getEncryptionOptions();
            case 'd' -> type.getDecryptionOptions();
            case 'g' -> type.getGenerateOptions();
            default -> throw new FlagException(messages.get("err.flg.typ.act"));
        };
    }

    private char extractActionFlag(String flag) {
        return (flag.length() > 2) ? flag.charAt(2) : flag.charAt(1);
    }

    public void validate() throws FlagException {
        byte checker = (byte) (optionsTemplate ^ options);
        int overAct = (options >> 2) & 0b111;
        if (!(overAct == 1 || overAct == 2 || overAct == 4))
            throw new FlagException(messages.get("err.flg.typ.crp"));
        if (((checker >> 4) & 0b1) == 1)
            throw new FlagException(messages.get("err.flg.typ.enc"));
        if (((checker >> 3) & 0b1) == 1)
            throw new FlagException(messages.get("err.flg.typ.dec"));
        if (((checker >> 2) & 0b1) == 1)
            throw new FlagException(messages.get("err.flg.typ.gen"));
        if (((checker >> 1) & 0b1) == 1)
            throw new FlagException(messages.get("err.flg.typ.key"));
        if (checker > 1)
            throw new FlagException(messages.get("err.flg.typ.bas"));
    }
}
