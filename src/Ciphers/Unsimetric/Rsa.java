package Ciphers.Unsimetric;

import Ciphers.AsymmetricCipher;
import Ciphers.BaseCipher;
import Ciphers.UtilCipher;
import Constants.Flags.ActionTypes;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import java.util.List;

public class Rsa extends UtilCipher implements BaseCipher, AsymmetricCipher {

    public Rsa(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
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

    }
}
