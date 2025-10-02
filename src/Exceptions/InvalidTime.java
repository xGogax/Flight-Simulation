package Exceptions;

public class InvalidTime extends Exception {
    public InvalidTime() {
        super("ERROR: Vreme polaska mora biti u formatu HH:MM (00-23 : 00-59).");
    }
}
