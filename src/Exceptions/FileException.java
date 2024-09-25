package Exceptions;

public class FileException extends Exception {
    public FileException(String message) {
        super("Cipher: File".concat(message));
    }
}
