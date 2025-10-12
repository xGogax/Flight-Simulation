package gui;

import body.aerodrom.AerodromContainer;
import body.let.LetContainer;
import gui.L2Panels.AirportsPanel;
import gui.L2Panels.FlightsPanel;
import gui.L2Panels.SimulationPanel;

import java.awt.TextArea;

public class AppContext {
    private static AppContext instance = null;

    private SimulationPanel simulator;
    private AirportsPanel airportsPanel;
    private FlightsPanel flightsPanel;
    private LetContainer letContainer;
    private AerodromContainer aerodromContainer;
    private TextArea console;

    private AppContext() {}

    public static AppContext getInstance() {
        if (instance == null) instance = new AppContext();
        return instance;
    }

    // Setteri
    public void setSimulator(SimulationPanel simulator) { this.simulator = simulator; }
    public void setAirportsPanel(AirportsPanel panel) { this.airportsPanel = panel; }
    public void setFlightsPanel(FlightsPanel panel) { this.flightsPanel = panel; }
    public void setLetContainer(LetContainer lc) { this.letContainer = lc; }
    public void setAerodromContainer(AerodromContainer ac) { this.aerodromContainer = ac; }
    public void setConsole(TextArea console) { this.console = console; }

    // Getteri
    public SimulationPanel getSimulator() { return simulator; }
    public AirportsPanel getAirportsPanel() { return airportsPanel; }
    public FlightsPanel getFlightsPanel() { return flightsPanel; }
    public LetContainer getLetContainer() { return letContainer; }
    public AerodromContainer getAerodromContainer() { return aerodromContainer; }
    public TextArea getConsole() { return console; }
}