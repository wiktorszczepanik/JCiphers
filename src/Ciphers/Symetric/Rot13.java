package Ciphers.Symetric;

import Ciphers.CipherParameters;
import Ciphers.BaseCrypt;
import Constants.Flags.ActionTypes;
import Constants.Flags.CipherTypes;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import java.util.List;

public class Rot13 extends CipherParameters
    implements BaseCrypt, SymmetricCrypt {

    public Rot13(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    @Override
    public StringBuilder encrypt() throws EncryptionException {
        return null;
    }

    @Override
    public StringBuilder decrypt() throws DecryptionException {
        return null;
    }

    @Override
    public StringBuilder generate() throws GenerateException {
        return null;
    }
}
