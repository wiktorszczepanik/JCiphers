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
//        for (int i = 0; i < textLength; i++) {
//            char changed = (char) (text[i] ^ key[i]);
//            exitText.append(changed);
//        }
        return exitText;
    }

    private StringBuilder equalizer(StringBuilder cleanText, StringBuilder keyText) {
        int cleanTextLen = cleanText.length();
        int keyTextLen = keyText.length();
        if (cleanTextLen < keyTextLen) keyText.setLength(cleanTextLen);
        else {
            String originalKey = keyText.toString();
            int appendSize = cleanTextLen - keyTextLen;
            int j = 0;
            for (int i = 0; i < appendSize; i++) {
                keyText.append(originalKey.charAt(j));
                j++;
                if (j >= originalKey.length()) j = 0;
            }
        }
        return keyText;
    }

    @Override
    public void encrypt() throws EncryptionException, FileException {
        StringBuilder cleanText = readFileContent(ActionTypes.ENCRYPT);
        var keyValue = new StringBuilder(equalizer(cleanText, readFileContent(ActionTypes.KEY)).toString().strip());
        StringBuilder encryptedText = algorithm(cleanText, keyValue);
        print(encryptedText);
    }

    @Override
    public void decrypt() throws DecryptionException, FileException {
        StringBuilder encryptedText = readFileContent(ActionTypes.DECRYPT);
        var keyValue = new StringBuilder(equalizer(encryptedText, readFileContent(ActionTypes.KEY)).toString().strip());
        StringBuilder decryptedText = algorithm(encryptedText, keyValue);
        decryptedText.setLength(decryptedText.length() - 2); // temp solution
        print(decryptedText);
    }

    @Override
    public void generate() throws GenerateException, FileException {
        long charNum = -1;
        try {
            String charLength = valueSelector(ActionTypes.GENERATE);
            // Number of characters
            charNum = Long.parseLong(charLength);
            if (charNum < 0) throw new Exception();
        } catch (Exception exception) {
            throw new GenerateException(messages.get("err.flg.gen.out"));
        }
        StringBuilder keyText = new StringBuilder();
        SecureRandom secRandom = new SecureRandom();
        char tempChar;
        for (long i = 0; i < charNum; i++) {

            // Characters Up to max values
            // tempChar = (char) secRandom.nextInt(Character.MAX_VALUE);
            // keyText.append(tempChar);

            // Limitation to ascii basic characters
            tempChar = (char) secRandom.nextInt(94);
            keyText.append((char) (tempChar + 32));
        }
        print(keyText);
    }
}
