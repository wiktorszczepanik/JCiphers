package Ciphers;

import Constants.Flags.ActionTypes;
import Constants.Messages;
import Exceptions.FileException;
import Structures.FlagTuple;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class CipherParameters {

    private byte selectedOptions;
    private List<FlagTuple<ActionTypes, String>> flags;
    Messages messages = Messages.getInstance();

    public CipherParameters(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        this.selectedOptions = selectedOptions;
        this.flags = flags;
    }

    protected StringBuilder readEncrypt() {
        return null;
    };

    protected StringBuilder readDecrypt() {
        return null;
    };

    protected StringBuilder readKey() throws FileException {
        int index = flagSelector(ActionTypes.KEY);
        File file = new File(flags.get(index).value);
        StringBuilder key = null;
        try {
            FileReader fileReader = new FileReader(file);
            int state;
            while ((state = fileReader.read()) != -1)
                key.append((char) state);
        } catch (IOException ioe) {
            throw new FileException(messages.get("err.fil.reg.key"));
        }
        if (key != null && !key.isEmpty()) return key;
        else throw new FileException(messages.get("err.fil.nul.key"));
    }

    protected StringBuilder writeToOutput() {
        return null;
    };

    private int flagSelector(ActionTypes searchFlag) {
        int flagIndex = 0, counter = 0;
        for (FlagTuple pair : flags) {
            if (pair.flag == searchFlag)
                flagIndex = counter;
            counter++;
        }
        return flagIndex;
    }
}
