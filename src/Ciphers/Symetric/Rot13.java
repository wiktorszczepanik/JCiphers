package Ciphers.Symetric;

import Ciphers.UtilCipher;
import Ciphers.BaseCipher;
import Constants.Flags.ActionTypes;
import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.GenerateException;
import Structures.FlagTuple;

import java.util.List;

public class Rot13 extends UtilCipher
    implements BaseCipher, SymmetricCipher {

    public Rot13(byte selectedOptions, List<FlagTuple<ActionTypes, String>> flags) {
        super(selectedOptions, flags);
    }

    @Override
    public StringBuilder encrypt() throws EncryptionException {
        System.out.println("encrypt");
        return null;
    }

    @Override
    public StringBuilder decrypt() throws DecryptionException {
        System.out.println("decrypt");
        return null;
    }

    @Override
    public StringBuilder generate() throws GenerateException {
        System.out.println("generate");
        return null;
    }

}
