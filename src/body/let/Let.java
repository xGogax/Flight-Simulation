package body.let;

import Exceptions.FlightDuration;
import Exceptions.FlightMustHaveAirport;
import Exceptions.InvalidTime;
import Exceptions.SameAirports;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;

public class Let {
    private Aerodrom start;
    private Aerodrom end;
    private int sat;
    private int minut;
    private int trajanjeMin;

    private boolean changed = false;

    public Let(Aerodrom start, Aerodrom end, int sat, int minut, int trajanjeMin)
    throws FlightMustHaveAirport, FlightMustHaveAirport, InvalidTime, FlightDuration, SameAirports {
        boolean startExists = false, endExists = false;
        for(Aerodrom a : AerodromContainer.getAerodromList()){
            if(a.getCode().equals(start.getCode())) startExists = true;
            if(a.getCode().equals(end.getCode())) endExists = true;
        }
        if(!startExists && !endExists){
            throw new FlightMustHaveAirport();
        }

        setStart(start);
        setEnd(end);
        setSat(sat);
        setMinut(minut);
        setTrajanjeMin(trajanjeMin);

        if(start.getCode() == end.getCode()) {
            throw new SameAirports();
        }
    }

    public Aerodrom getStart() {
        return start;
    }

    public void setStart(Aerodrom start) throws FlightMustHaveAirport {
        if(start == null) throw new FlightMustHaveAirport();
        this.start = start;
    }

    public Aerodrom getEnd() {
        return end;
    }

    public void setEnd(Aerodrom end) throws FlightMustHaveAirport {
        if(end == null) throw new FlightMustHaveAirport();
        this.end = end;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) throws InvalidTime {
        if(sat > 23 || sat < 0) throw new InvalidTime();
        this.sat = sat;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) throws InvalidTime {
        if(minut < 0 || minut > 59) throw new InvalidTime();
        this.minut = minut;
    }

    public int getTrajanjeMin() {
        return trajanjeMin;
    }

    public void setTrajanjeMin(int trajanjeMin) throws FlightDuration{
        if(trajanjeMin < 0) throw new FlightDuration();
        this.trajanjeMin = trajanjeMin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(start.getCode()).append(" -> ").append(end.getCode())
                .append(", Takeoff: ").append(getSat()).append(":").append(getMinut())
                .append(", Duration: ").append(getTrajanjeMin());
        return sb.toString();
    }

    public boolean getChanged() {
        return changed;
    }
    public void setChanged(boolean changed){
        this.changed = changed;
    }
}
