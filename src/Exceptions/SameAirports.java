package Exceptions;

public class SameAirports extends Exception {
    public SameAirports(){
        super("ERROR: Start and end airport must be different.");
    }
}
