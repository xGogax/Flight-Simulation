package Exceptions;

public class FlightDuration extends Exception {
    public FlightDuration() {
        super("ERROR: Flight duration must be positive.");
    }
}
