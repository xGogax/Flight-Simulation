package Exceptions;

public class InvalidTime extends Exception {
    public InvalidTime() {
        super("ERROR: Take-off must be in format HH:MM (00-23 : 00-59).");
    }
}
