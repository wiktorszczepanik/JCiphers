package Ciphers.Symetric;

import Exceptions.FileException;
import Exceptions.GenerateException;

public interface SymmetricCipher {
    public void generate() throws GenerateException, FileException;
}
