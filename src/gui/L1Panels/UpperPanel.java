package gui.L1Panels;

import gui.L2Panels.AirportsPanel;
import gui.L2Panels.SimulationPanel;

import java.awt.*;

public class UpperPanel extends Panel {
    SimulationPanel simulator = new SimulationPanel();
    AirportsPanel airportsPanel = new AirportsPanel();

    private void populateUpperPanel(){
        this.setBackground(new Color(206, 237, 249));

        //SIMULATION PANEL
        this.add(simulator);

        //AIRPORTS PANEL
        this.add(airportsPanel);

        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");
        airportsPanel.addAirport("Nikola Tesla1", "BEG", "31", "46", "YES");
        airportsPanel.addAirport("Paris1", "CDG", "50", "80", "No");

    }

    public UpperPanel(){
        populateUpperPanel();
    }
}
