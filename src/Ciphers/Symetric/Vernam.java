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

    @Override
    public void encrypt() throws EncryptionException, FileException {

    }

    @Override
    public void decrypt() throws DecryptionException, FileException {

    }

    @Override
    public void generate() throws GenerateException, FileException {
        int charNum = -1;
        try {
            String charLength = valueSelector(ActionTypes.GENERATE);
            charNum = Integer.parseInt(charLength);
            if (charNum < 0) throw new Exception();
        } catch (Exception exception) {
            throw new GenerateException(messages.get("err.flg.gen.out"));
        }
        StringBuilder keyText = new StringBuilder();
        SecureRandom secRandom = new SecureRandom();
        char tempChar;
        for (int i = 0; i < charNum; i++) {
            tempChar = (char) secRandom.nextInt(Character.MAX_VALUE);
            keyText.append(tempChar);
        }
        print(keyText);
    }
}
