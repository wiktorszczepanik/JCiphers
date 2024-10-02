package Ciphers;

import Exceptions.FileException;
import Exceptions.GenerateException;

public interface AsymmetricCipher {
    public void generate() throws GenerateException, FileException;
}
