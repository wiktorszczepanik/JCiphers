package Components;

import Constants.Flags.ActionTypes;
import Constants.Flags.CipherTypes;
import Constants.Messages;
import Exceptions.FlagExcpetion;
import Structures.FlagTuple;

public class CipherValidation {

    private final String cipherType;
    private final int cipherAction;
    private final byte options;
    Messages messages = Messages.getInstance();

    public CipherValidation(FlagTuple<ActionTypes, String> cipherType, FlagTuple<ActionTypes, String> cipherAction, byte options) {
        this.cipherType = cleanCipherType(cipherType.value);
        this.cipherAction = cleanAction(cipherAction.flag.getShortFlag());
        this.options = options;
    }

    private String cleanCipherType(String type) {
        return type.trim().toUpperCase();
    }

    private int cleanAction(String ) {
        if (cipherAction.e)
    }

    public void validate() throws FlagExcpetion {
        CipherTypes selectedType;
        try { selectedType = CipherTypes.valueOf(cipherType);
        } catch (IllegalArgumentException iae) {
            throw new FlagExcpetion(messages.get("err.flg.typ.typ"));
        }
    }
}
