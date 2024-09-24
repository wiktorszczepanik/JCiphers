package Ciphers;

import Exceptions.DecryptionException;
import Exceptions.EncryptionException;

public interface BaseCipher {
    StringBuilder encrypt() throws EncryptionException;
    StringBuilder decrypt() throws DecryptionException;
}
