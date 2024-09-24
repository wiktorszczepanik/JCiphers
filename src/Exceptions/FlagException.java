package Exceptions;

public class FlagException extends Exception {
    public FlagException(String message) {
        super("Cipher: Flags: ".concat(message));
    }
}
