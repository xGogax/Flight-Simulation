package Exceptions;

public class InvalidXCoordinate extends Exception {
    public InvalidXCoordinate() {
        super("ERROR: X coordinate must be in between -90 and 90.");
    }
}
