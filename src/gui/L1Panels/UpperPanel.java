package gui.L1Panels;

import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;
import gui.L2Panels.AirportsPanel;
import gui.L2Panels.FlightsPanel;
import gui.L2Panels.SimulationPanel;

import java.awt.*;

public class UpperPanel extends Panel {
    SimulationPanel simulator;
    AirportsPanel airportsPanel;
    FlightsPanel fligtsPanel;

    private void populateUpperPanel(){
        this.setBackground(new Color(206, 237, 249));

        //SIMULATION PANEL
        this.add(simulator);

        //AIRPORTS PANEL
        this.add(airportsPanel);

        //FLIGHTS PANEL
        this.add(fligtsPanel);
    }

    public UpperPanel(AerodromContainer aerodroms, LetContainer letContainer) {
        simulator = new SimulationPanel(aerodroms);
        airportsPanel = new AirportsPanel(aerodroms);
        fligtsPanel = new FlightsPanel(letContainer);
        populateUpperPanel();
    }

    public AirportsPanel getAirportsPanel() {
        return airportsPanel;
    }
}
