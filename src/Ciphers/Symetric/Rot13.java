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

import java.util.List;

public class Rot13 extends UtilCipher
    implements BaseCipher, SymmetricCipher {

    private int key = 13;

    public Rot13(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    private StringBuilder algorithm(StringBuilder cleanText) {
        StringBuilder extractText = new StringBuilder();
        char letter;
        for (int i = 0; i < cleanText.length(); i++) {
            letter = cleanText.charAt(i);
            if (letter >= 'A' && letter <= 'Z')
                extractText.append((char) ('A' + ((letter - 'A' + key) % 26)));
            else if (letter >= 'a' && letter <= 'z')
                extractText.append((char) ('a' + ((letter - 'a' + key) % 26)));
            else extractText.append(letter);
        }
        return extractText;
    }

    @Override
    public void encrypt() throws EncryptionException, FileException {
        StringBuilder textToEncrypt = readFileContent(ActionTypes.ENCRYPT);
        StringBuilder encryptedText = algorithm(textToEncrypt);
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(encryptedText);
        else writeFileContent(ActionTypes.OUTPUT, encryptedText);
    }

    @Override
    public void decrypt() throws DecryptionException, FileException {
        StringBuilder textToDecrypt = readFileContent(ActionTypes.DECRYPT);
        StringBuilder decryptedText = algorithm(textToDecrypt);
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(decryptedText);
        else writeFileContent(ActionTypes.OUTPUT, decryptedText);
    }

    @Override
    public void generate() throws GenerateException, FileException {
        if (flagTupleSelector(ActionTypes.OUTPUT) == -1)
            System.out.println(key);
        else writeFileContent(
            ActionTypes.OUTPUT, new StringBuilder(Integer.toString(key))
        );
    }

}
