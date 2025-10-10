package gui.L2Panels;

import body.aerodrom.AerodromContainer;

import java.awt.*;

public class SimulationPanel extends Panel {
    private Label simPanel = new Label("Simulation");
    private Canvas simulator = new Canvas();

    private void populateSimulationPanel() {
        this.setLayout(new BorderLayout());
        //TEXT
        this.add(simPanel, BorderLayout.NORTH);
        simPanel.setForeground(new Color(49, 95, 166));
        simPanel.setFont(new Font("Ariel", Font.BOLD, 20));
        simPanel.setAlignment(Label.CENTER);

        //CANVAS
        simulator.setSize(181*4, 181*4);
        simulator.setBackground(new Color(82, 176, 221));
        this.add(simulator, BorderLayout.CENTER);
    }

    public SimulationPanel(AerodromContainer aerodroms) {
        populateSimulationPanel();
    }
}
