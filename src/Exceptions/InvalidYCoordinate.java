package Exceptions;

public class InvalidYCoordinate extends Exception {
    public InvalidYCoordinate() {
        super("ERROR: Y coordinate must be Number in between -90 and 90.");
    }
}