package gui.L1Panels;

import body.aerodrom.AerodromContainer;
import body.let.LetContainer;
import gui.L2Panels.AirportsPanel;
import gui.L2Panels.FlightsPanel;
import gui.L2Panels.SimulationPanel;
import gui.AppContext;

import java.awt.*;

public class UpperPanel extends Panel {
    private SimulationPanel simulator;
    private AirportsPanel airportsPanel;
    private FlightsPanel flightsPanel;

    public UpperPanel() {
        AppContext ctx = AppContext.getInstance();
        AerodromContainer aerodroms = ctx.getAerodromContainer();
        LetContainer letContainer = ctx.getLetContainer();

        simulator = new SimulationPanel(aerodroms);
        airportsPanel = new AirportsPanel(aerodroms);
        flightsPanel = new FlightsPanel(letContainer);

        ctx.setAirportsPanel(airportsPanel);
        ctx.setFlightsPanel(flightsPanel);
        ctx.setSimulator(simulator);

        populateUpperPanel();
    }

    private void populateUpperPanel() {
        this.setBackground(new Color(206, 237, 249));

        this.add(simulator);
        this.add(airportsPanel);
        this.add(flightsPanel);
    }

    public AirportsPanel getAirportsPanel() {
        return airportsPanel;
    }

    public FlightsPanel getFlightsPanel() {
        return flightsPanel;
    }

    public SimulationPanel getSimulationPanel() {
        return simulator;
    }
}