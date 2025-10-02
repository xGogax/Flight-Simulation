package Exceptions;

public class InvalidCode extends Exception {
    public InvalidCode(String input) {
        super("ERROR: Airport code: " + input + " is not valid format." );
    }
}
