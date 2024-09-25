package Ciphers.Symetric;

import Exceptions.FileException;
import Exceptions.GenerateException;

public interface SymmetricCipher {
    public StringBuilder generate() throws GenerateException, FileException;
}
