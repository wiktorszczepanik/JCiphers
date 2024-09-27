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

public class Cezar extends UtilCipher
        implements BaseCipher, SymmetricCipher {

    public Cezar(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    @Override
    public void encrypt() throws EncryptionException, FileException {
        StringBuilder tempKey = readFileContent(ActionTypes.KEY);
        int key;
        try { key = Integer.parseInt(tempKey.toString());
        } catch (Exception exception) {
            throw new EncryptionException(messages.get("err.enc.cot.key"));
        }
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
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(encryptedText);
        else writeFileContent(ActionTypes.OUTPUT, encryptedText);
    }

    @Override
    public void decrypt() throws DecryptionException, FileException {
        StringBuilder tempKey = readFileContent(ActionTypes.KEY);
        int key;
        try { key = Integer.parseInt(tempKey.toString());
        } catch (Exception exception) {
            throw new DecryptionException(messages.get("err.enc.cot.key"));
        }
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
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(decryptedText);
        else writeFileContent(ActionTypes.OUTPUT, decryptedText);
    }

    @Override
    public void generate() throws GenerateException, FileException {
        SecureRandom secureRandom = new SecureRandom();
        int randomNumber = secureRandom.nextInt(26) + 1;
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(randomNumber);
        else writeFileContent(
                ActionTypes.OUTPUT, new StringBuilder(Integer.toString(randomNumber))
        );
    }
}
