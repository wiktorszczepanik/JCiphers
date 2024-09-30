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
    protected List<FlagTuple<ActionTypes, String>> flags;
    protected Messages messages = Messages.getInstance();

    public UtilCipher(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        this.selectedOptions = selectedOptions;
        this.flags = flags;
    }

    abstract protected void encrypt() throws EncryptionException, FileException;
    abstract protected void decrypt() throws DecryptionException, FileException;
    abstract protected void generate() throws GenerateException, FileException;

    public void run() throws EncryptionException, DecryptionException, GenerateException, FileException {
        int mode = (selectedOptions >> 2) & 0b111;
        if (mode == 0b100) encrypt();
        else if (mode == 0b010) decrypt();
        else generate();
    }

    protected boolean isFileExist(ActionTypes possiblePath) {
        File file = new File(valueSelector(possiblePath));
        return file.exists();
    }

    protected StringBuilder readFileContent(ActionTypes action) throws FileException {
        File file = new File(valueSelector(action));
        StringBuilder key = new StringBuilder();
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
            throw new FileException(messages.get("err.red.reg.dec"));
        else if (action.getShortFlag().equals("-k"))
            throw new FileException(messages.get("err.red.reg.key"));
        else throw new FileException(messages.get("err.red.reg.all"));
    }

    private void selectReadEmptyExceptionMessage(ActionTypes action) throws FileException {
        if (action.getShortFlag().equals("-e"))
            throw new FileException(messages.get("err.red.nul.enc"));
        else if (action.getShortFlag().equals("-d"))
            throw new FileException(messages.get("err.red.nul.dec"));
        else if (action.getShortFlag().equals("-k"))
            throw new FileException(messages.get("err.red.nul.key"));
        else throw new FileException(messages.get("err.red.nul.all"));
    }

    protected void writeFileContent(ActionTypes action, StringBuilder text) throws FileException {
        File file = new File(valueSelector(action));
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

    protected int flagTupleSelector(ActionTypes searchFlag) {
        int flagIndex = -1, counter = 0;
        for (FlagTuple pair : flags) {
            if (pair.flag == searchFlag)
                flagIndex = counter;
            counter++;
        }
        return flagIndex;
    }

    protected String valueSelector(ActionTypes flag) {
        return flags.get(flagTupleSelector(flag)).value;
    }

    protected void print(StringBuilder transformedText) throws FileException {
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(transformedText);
        else writeFileContent(ActionTypes.OUTPUT, transformedText);
    }

    protected void print(String text) throws FileException {
        StringBuilder transformedText = new StringBuilder(text);
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(transformedText);
        else writeFileContent(ActionTypes.OUTPUT, transformedText);
    }

}
