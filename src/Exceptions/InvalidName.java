package Exceptions;

public class InvalidName extends Exception {
    public InvalidName() {
        super("ERROR: Airplane name is not inserted.");
    }
}
