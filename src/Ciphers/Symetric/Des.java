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

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Des extends UtilCipher implements BaseCipher, SymmetricCipher {

    public Des(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
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
        // TODO: Correct DES generator.
        //...
        String key;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGenerator.generateKey();
            key = secretKey.toString();
        } catch (Exception exception) {
            throw new GenerateException(messages.get("err.gen.des.key"));
        }
        print(key);
    }
}
