package Exceptions;

public class DecryptionException extends Exception {
    public DecryptionException(String message) {
        super("Cipher: Decryption".concat(message));
    }
}
