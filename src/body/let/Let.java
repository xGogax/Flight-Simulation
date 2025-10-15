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
    private boolean iscrtan = false;

    // --- Animacija ---
    private double currX, currY;  // trenutna pozicija
    private double stepX, stepY;  // pomeraj po frame-u
    private int ticksRemaining;   // koliko "frames" pre kraja leta
    private boolean finished = false;

    public Let(Aerodrom start, Aerodrom end, int sat, int minut, int trajanjeMin)
            throws FlightMustHaveAirport, InvalidTime, FlightDuration, SameAirports {
        boolean startExists = false, endExists = false;
        for (Aerodrom a : AerodromContainer.getAerodromList()) {
            if (a.getCode().equals(start.getCode())) startExists = true;
            if (a.getCode().equals(end.getCode())) endExists = true;
        }
        if (!startExists || !endExists) {
            throw new FlightMustHaveAirport();
        }

        setStart(start);
        setEnd(end);
        setSat(sat);
        setMinut(minut);
        setTrajanjeMin(trajanjeMin);

        if (start.getCode().equals(end.getCode())) {
            throw new SameAirports();
        }

        // inicijalizacija animacije
        this.currX = start.getX();
        this.currY = start.getY();

        // --- Animacija ---
        double framesPerSecond = 5.0;              // tvoj thread osvežava 5 puta u sekundi
        double simulatedMinutesPerSecond = 10.0;  // 1 "tick" = 10 min
        double totalFrames = Math.max(1, (trajanjeMin / simulatedMinutesPerSecond) * framesPerSecond);

        this.stepX = (end.getX() - start.getX()) / totalFrames;
        this.stepY = (end.getY() - start.getY()) / totalFrames;
        this.ticksRemaining = (int) totalFrames;
    }

    // --- Getteri i setteri ---
    public Aerodrom getStart() { return start; }
    public void setStart(Aerodrom start) throws FlightMustHaveAirport {
        if (start == null) throw new FlightMustHaveAirport();
        this.start = start;
    }

    public Aerodrom getEnd() { return end; }
    public void setEnd(Aerodrom end) throws FlightMustHaveAirport {
        if (end == null) throw new FlightMustHaveAirport();
        this.end = end;
    }

    public int getSat() { return sat; }
    public void setSat(int sat) throws InvalidTime {
        if (sat > 23 || sat < 0) throw new InvalidTime();
        this.sat = sat;
    }

    public int getMinut() { return minut; }
    public void setMinut(int minut) throws InvalidTime {
        if (minut < 0 || minut > 59) throw new InvalidTime();
        this.minut = minut;
    }

    public int getTrajanjeMin() { return trajanjeMin; }
    public void setTrajanjeMin(int trajanjeMin) throws FlightDuration {
        if (trajanjeMin < 0) throw new FlightDuration();
        this.trajanjeMin = trajanjeMin;
    }

    public boolean getIscrtan() { return iscrtan; }
    public void setIscrtan(boolean iscrtan) { this.iscrtan = iscrtan; }
    public boolean getChanged() { return changed; }
    public void setChanged(boolean changed) { this.changed = changed; }

    @Override
    public String toString() {
        return start.getCode() + " -> " + end.getCode() +
                ", Takeoff: " + sat + ":" + minut +
                ", Duration: " + trajanjeMin;
    }

    // --- Animacija ---
    public void step() {
        if (finished) return;

        currX += stepX;
        currY += stepY;
        ticksRemaining--;
        System.out.println(ticksRemaining);

        if (ticksRemaining <= 0) {
            finished = true;
            currX = end.getX();
            currY = end.getY();
        }
    }

    public boolean isFinished() { return finished; }

    public double getCurrentX() { return currX; }
    public double getCurrentY() { return currY; }
}