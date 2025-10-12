package gui.L2Panels;

import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;

import java.awt.*;

public class SimulationPanel extends Panel {
    private Label simPanel = new Label("Simulation");
    private Canvas simulator;
    private AerodromContainer aerodroms;

    private final int scale = 4;
    private final int canvasSize = 181 * scale;

    private void populateSimulationPanel() {
        this.setLayout(new BorderLayout());

        //TEXT
        this.add(simPanel, BorderLayout.NORTH);
        simPanel.setForeground(new Color(49, 95, 166));
        simPanel.setFont(new Font("Ariel", Font.BOLD, 20));
        simPanel.setAlignment(Label.CENTER);

        //CANVAS
        simulator = new SimulatorCanvas();
        simulator.setSize(canvasSize, canvasSize);
        simulator.setBackground(new Color(82, 176, 221));
        this.add(simulator, BorderLayout.CENTER);
    }

    public void refresh(){
        simulator.repaint();
    }

    public SimulationPanel(AerodromContainer aerodroms) {
        this.aerodroms = aerodroms;
        populateSimulationPanel();
    }

    private class SimulatorCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            draw(g);
        }

        public void draw(Graphics g) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            for(Aerodrom a : aerodroms.getAerodroms()){
                int xPixel = (a.getX() + 90) * scale;
                int yPixel = (90 - a.getY()) * scale;
                g.fillRect(xPixel, yPixel, scale, scale);

                g.setColor(Color.BLACK);
                g.drawString(a.getCode(), xPixel + scale + 2, yPixel + scale + 2);
                g.setColor(Color.GRAY);
            }
        }
    }
}
