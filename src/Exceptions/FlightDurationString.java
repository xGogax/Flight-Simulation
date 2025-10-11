package Exceptions;

public class FlightDurationString extends Exception{
    public FlightDurationString() {
        super("ERROR: Flight duration must be NUMBER");
    }
}
