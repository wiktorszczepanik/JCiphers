package Ciphers;

import Exceptions.DecryptionException;
import Exceptions.EncryptionException;
import Exceptions.FileException;

public interface BaseCipher {
    StringBuilder encrypt() throws EncryptionException, FileException;
    StringBuilder decrypt() throws DecryptionException, FileException;
}
