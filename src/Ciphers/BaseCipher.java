package Ciphers;

import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;

public interface BaseCipher {
    void encrypt() throws EncryptionException, FileException;
    void decrypt() throws DecryptionException, FileException;
}
