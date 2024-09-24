package Ciphers.Symetric;

import Exceptions.GenerateException;

public interface SymmetricCipher {
    public StringBuilder generate() throws GenerateException;
}
