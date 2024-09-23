package Exceptions;

public class GenerateException extends Exception {
    public GenerateException(String message) {
        super("Cipher: Generate: ".concat(message));
    }
}
