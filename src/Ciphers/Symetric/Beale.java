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

import java.util.List;

public class Beale extends UtilCipher
        implements BaseCipher, SymmetricCipher {

    public Beale(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
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
        throw new GenerateException(messages.get("err.gen.try.key"));
    }
}
