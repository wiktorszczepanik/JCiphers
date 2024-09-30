package Ciphers.Symetric;

import Ciphers.BaseCipher;
import Ciphers.SymmetricCipher;
import Ciphers.UtilCipher;
import Constants.Flags.ActionTypes;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

public class Cezar extends UtilCipher
        implements BaseCipher, SymmetricCipher {

    public Cezar(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    @Override
    public void encrypt() throws EncryptionException, FileException {
        int key = keySelector();
        StringBuilder cleanText = readFileContent(ActionTypes.ENCRYPT);
        StringBuilder encryptedText = new StringBuilder();
        char letter;
        for (int i = 0; i < cleanText.length(); i++) {
            letter = cleanText.charAt(i);
            if (letter >= 'A' && letter <= 'Z')
                encryptedText.append((char) ('A' + ((letter - 'A' + key) % 26)));
            else if (letter >= 'a' && letter <= 'z')
                encryptedText.append((char) ('a' + ((letter - 'a' + key) % 26)));
            else encryptedText.append(letter);
        }
        print(encryptedText);
    }


    @Override
    public void decrypt() throws DecryptionException, FileException {
        int key = keySelector();
        StringBuilder cleanText = readFileContent(ActionTypes.DECRYPT);
        StringBuilder decryptedText = new StringBuilder();
        char letter;
        for (int i = 0; i < cleanText.length(); i++) {
            letter = cleanText.charAt(i);
            if (letter >= 'A' && letter <= 'Z')
                decryptedText.append((char) (((letter - 'A' - key + 26) % 26) + 'A'));
            else if (letter >= 'a' && letter <= 'z')
                decryptedText.append((char) (((letter - 'a' - key + 26) % 26) + 'a'));
            else decryptedText.append(letter);
        }
        print(decryptedText);
    }

    private int keySelector() throws FileException {
        StringBuilder tempKey;
        if (isFileExist(ActionTypes.KEY))
            tempKey = readFileContent(ActionTypes.KEY);
        else tempKey = new StringBuilder(
                valueSelector(ActionTypes.KEY));
        int key;
        try { key = Integer.parseInt(tempKey.toString());
        } catch (Exception exception) {
            throw new FileException(messages.get("err.enc.cot.key"));
        }
        return key;
    }

    @Override
    public void generate() throws GenerateException, FileException {
        SecureRandom secureRandom = new SecureRandom();
        int randomNumber = secureRandom.nextInt(26) + 1;
        print(Integer.toString(randomNumber));
    }
}
