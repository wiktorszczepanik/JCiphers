package Ciphers.Symetric;

import Ciphers.Crypt;
import Constants.Flags.CipherTypes;

public class Rot13 implements Crypt, SymmetricCrypt {

    private CipherTypes type = CipherTypes.ROT13;
    private byte selectedOptions;

    public Rot13(byte selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public CipherTypes getType() {
        return type;
    }

    @Override
    public StringBuilder encrypt() {
        return null;
    }

    @Override
    public StringBuilder decrypt() {
        return null;
    }

    @Override
    public StringBuilder generate() {
        return null;
    }
}
