package Exceptions;

public class FlagExcpetion extends Exception {
    public FlagExcpetion(String message) {
        super("Cipher: Flags: ".concat(message));
    }
}
