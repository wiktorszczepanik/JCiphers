package Ciphers.Symetric;

import Ciphers.SymmetricCipher;
import Ciphers.UtilCipher;
import Ciphers.BaseCipher;
import Constants.Flags.ActionTypes;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import java.security.SecureRandom;
import java.util.List;

public class Vigenere extends UtilCipher implements BaseCipher, SymmetricCipher {

    public Vigenere(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    private StringBuilder equalizer(StringBuilder cleanText, StringBuilder keyText) {
        keyText = new StringBuilder(keyText.toString().strip());
        int cleanTextLen = cleanText.length();
        int keyTextLen = keyText.length();
        if (cleanTextLen < keyTextLen) {
            keyText.setLength(cleanTextLen);
        } else {
            String originalKey = keyText.toString();
            int appendSize = cleanTextLen - keyTextLen;
            int j = 0;
            for (int i = 0; i < appendSize; i++) {
                keyText.append(originalKey.charAt(j));
                j++;
                if (j >= originalKey.length()) j = 0;
            }
        }
        for (int i = 0; i < cleanTextLen; i++) {
            if (cleanText.charAt(i) == 0x20)
                keyText.insert(i, ' ');
        }
        return keyText;
    }

    // TODO: Encrypt without space _ character
    @Override
    public void encrypt() throws EncryptionException, FileException {
        StringBuilder cleanText = readFileContent(ActionTypes.ENCRYPT);
        cleanText = new StringBuilder(cleanText.toString().strip());
        StringBuilder keyValue = equalizer(cleanText, readFileContent(ActionTypes.KEY));
        var upperCleanText = new StringBuilder(cleanText.toString().toUpperCase());
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < upperCleanText.length(); i++) {
            char inputChar = upperCleanText.charAt(i); // M = 77
            char keyChar = keyValue.charAt(i); // O = 79
            if (inputChar != ' ')
                encryptedText.append(
                    (char) ((inputChar - 'A' + (keyChar - 'A')) % 26 + 'A')
                );
            else encryptedText.append(' ');
        }
        print(encryptedText);
    }

    @Override
    public void decrypt() throws DecryptionException, FileException {
        StringBuilder encryptedText = readFileContent(ActionTypes.DECRYPT);
        encryptedText = new StringBuilder(encryptedText.toString().strip());
        StringBuilder keyValue = equalizer(encryptedText, readFileContent(ActionTypes.KEY));
        var upperCleanText = new StringBuilder(encryptedText.toString().toUpperCase());
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < upperCleanText.length(); i++) {
            char inputChar = upperCleanText.charAt(i); // M = 77
            char keyChar = keyValue.charAt(i); // O = 79
            if (inputChar != ' ')
                decryptedText.append(
                    (char) ((inputChar - keyChar + 26) % 26 + 'A')
                );
            else decryptedText.append(' ');
        }
        print(decryptedText);
    }

    @Override
    public void generate() throws GenerateException, FileException {
        long charNum = -1;
        try {
            String charLength = valueSelector(ActionTypes.GENERATE);
            charNum = Long.parseLong(charLength);
            if (charNum < 0) throw new Exception();
        } catch (Exception exception) {
            throw new GenerateException(messages.get("err.flg.gen.out"));
        }
        StringBuilder keyText = new StringBuilder();
        SecureRandom secRandom = new SecureRandom();
        for (long i = 0; i < charNum; i++) {
            char tempChar = (char) secRandom.nextInt(26);
            keyText.append((char) (tempChar + 65));
        }
        print(keyText);
    }
}
