package Exceptions;

public class FlightMustHaveAirport extends Exception {
    public FlightMustHaveAirport() {
        super("ERROR: Flight must have start/end airport.");
    }
}
