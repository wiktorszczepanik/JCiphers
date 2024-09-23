package Ciphers;

import Exceptions.DecryptionException;
import Exceptions.EncryptionException;

public interface BaseCrypt {
    public StringBuilder encrypt() throws EncryptionException;
    public StringBuilder decrypt() throws DecryptionException;
}
