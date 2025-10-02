package Exceptions;

public class InvalidYCoordinate extends Exception {
    public InvalidYCoordinate() {
        super("ERROR: Y coordinate must be in between -90 and 90.");
    }
}
