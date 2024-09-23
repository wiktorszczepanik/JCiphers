package Exceptions;

public class EncryptionException extends Exception {
    public EncryptionException(String message) {
        super("Cipher: Encryption: ".concat(message));
    }
}
