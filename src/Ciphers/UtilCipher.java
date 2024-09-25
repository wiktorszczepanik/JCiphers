package Ciphers;

import Constants.Flags.ActionTypes;
import Constants.Messages;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class UtilCipher {

    private byte selectedOptions;
    private List<FlagTuple<ActionTypes, String>> flags;
    Messages messages = Messages.getInstance();

    public UtilCipher(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        this.selectedOptions = selectedOptions;
        this.flags = flags;
    }

    abstract protected StringBuilder encrypt() throws EncryptionException, FileException;
    abstract protected StringBuilder decrypt() throws DecryptionException, FileException;
    abstract protected StringBuilder generate() throws GenerateException, FileException;

    public void run() throws EncryptionException, DecryptionException, GenerateException, FileException {
        int mode = (selectedOptions >> 2) & 0b111;
        if (mode == 0b100) encrypt();
        else if (mode == 0b010) decrypt();
        else generate();
    }

    protected StringBuilder readFileContent(ActionTypes action) throws FileException {
        int index = flagSelector(action);
        File file = new File(flags.get(index).value);
        StringBuilder key = null;
        try {
            FileReader fileReader = new FileReader(file);
            int state;
            while ((state = fileReader.read()) != -1)
                key.append((char) state);
            fileReader.close();
        } catch (IOException exception) {
            selectReadExceptionMessage(action);
        }
        if (key != null && !key.isEmpty()) return key;
        else selectReadEmptyExceptionMessage(action);
        return new StringBuilder();
    }

    private void selectReadExceptionMessage(ActionTypes action) throws FileException {
        if (action.getShortFlag().equals("-e"))
            throw new FileException(messages.get("err.red.reg.enc"));
        else if (action.getShortFlag().equals("-d"))
            throw new FileException("err.red.reg.dec");
        else if (action.getShortFlag().equals("-k"))
            throw new FileException("err.red.reg.key");
        else throw new FileException(messages.get("err.red.reg.all"));
    }

    private void selectReadEmptyExceptionMessage(ActionTypes action) throws FileException {
        if (action.getShortFlag().equals("-e"))
            throw new FileException(messages.get("err.red.nul.enc"));
        else if (action.getShortFlag().equals("-d"))
            throw new FileException("err.red.nul.dec");
        else if (action.getShortFlag().equals("-k"))
            throw new FileException("err.red.nul.key");
        else throw new FileException(messages.get("err.red.nul.all"));
    }

    protected void writeFileContent(ActionTypes action, StringBuilder text) throws FileException {
        int index = flagSelector(action);
        File file = new File(flags.get(index).value);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(text);
            fileWriter.close();
        } catch (IOException exception) {
            selectWriteExceptionMessage(action);
        }
    };

    private void selectWriteExceptionMessage(ActionTypes action) throws FileException {
        if (action.getShortFlag().equals("-e"))
            throw new FileException(messages.get("err.wrt.reg.enc"));
        else if (action.getShortFlag().equals("-d"))
            throw new FileException("err.wrt.reg.dec");
        else if (action.getShortFlag().equals("-k"))
            throw new FileException("err.wrt.reg.key");
        else throw new FileException(messages.get("err.wrt.reg.all"));

    }

    protected int flagSelector(ActionTypes searchFlag) {
        int flagIndex = 0, counter = 0;
        for (FlagTuple pair : flags) {
            if (pair.flag == searchFlag)
                flagIndex = counter;
            counter++;
        }
        return flagIndex;
    }
}
