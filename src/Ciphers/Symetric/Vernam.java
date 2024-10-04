package Ciphers.Symetric;

import Ciphers.BaseCipher;
import Ciphers.SymmetricCipher;
import Ciphers.UtilCipher;
import Constants.Flags.ActionTypes;
import Exceptions.*;
import Structures.FlagTuple;

import java.security.SecureRandom;
import java.util.List;

public class Vernam extends UtilCipher implements BaseCipher, SymmetricCipher {

    public Vernam(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    private StringBuilder algorithm(StringBuilder entryText, StringBuilder entryKey) {
        int textLength = entryText.length();
        char[] text = new char[textLength];
        entryText.getChars(0, textLength, text, 0);
        char[] key = new char[textLength];
        entryKey.getChars(0, textLength, key, 0);

        StringBuilder exitText = new StringBuilder();
        --textLength;
        char changed;
        while (textLength >= 0) {
            changed = (char) (text[textLength] ^ key[textLength]);
            exitText.insert(0, changed);
            textLength--;
        }
        return exitText;
    }

    @Override
    public void encrypt() throws EncryptionException, FileException {
        StringBuilder cleanText = readFileContent(ActionTypes.ENCRYPT);
        StringBuilder keyValue = readFileContent(ActionTypes.KEY);
        StringBuilder encryptedText = algorithm(cleanText, keyValue);
        print(encryptedText);
    }

    @Override
    public void decrypt() throws DecryptionException, FileException {
        StringBuilder encryptedText = readFileContent(ActionTypes.DECRYPT);
        StringBuilder keyValue = readFileContent(ActionTypes.KEY);
        StringBuilder decryptedText = algorithm(encryptedText, keyValue);
        print(decryptedText);
    }

    @Override
    public void generate() throws GenerateException, FileException {
        long charNum = -1;
        try {
            String charLength = valueSelector(ActionTypes.GENERATE);
            // Convert to bite length
            charNum = Long.parseLong(charLength) / 16;
            if (charNum < 0) throw new Exception();
        } catch (Exception exception) {
            throw new GenerateException(messages.get("err.flg.gen.out"));
        }
        StringBuilder keyText = new StringBuilder();
        SecureRandom secRandom = new SecureRandom();
        char tempChar;
        for (long i = 0; i < charNum; i++) {
            tempChar = (char) secRandom.nextInt(Character.MAX_VALUE);
            keyText.append(tempChar);
        }
        print(keyText);
    }
}
