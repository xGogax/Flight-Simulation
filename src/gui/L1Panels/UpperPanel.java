package gui.L1Panels;

import body.aerodrom.AerodromContainer;
import gui.L2Panels.AirportsPanel;
import gui.L2Panels.SimulationPanel;

import java.awt.*;

public class UpperPanel extends Panel {
    SimulationPanel simulator;
    AirportsPanel airportsPanel;

    private void populateUpperPanel(){
        this.setBackground(new Color(206, 237, 249));

        //SIMULATION PANEL
        this.add(simulator);

        //AIRPORTS PANEL
        this.add(airportsPanel);
    }

    public UpperPanel(AerodromContainer aerodroms){
        simulator = new SimulationPanel(aerodroms);
        airportsPanel = new AirportsPanel(aerodroms);
        populateUpperPanel();
    }

    public AirportsPanel getAirportsPanel() {
        return airportsPanel;
    }
}
