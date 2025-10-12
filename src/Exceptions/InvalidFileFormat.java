package Exceptions;

public class InvalidFileFormat extends Exception {
    public InvalidFileFormat() {
        super("ERROR: File format not supported.");
    }
}
