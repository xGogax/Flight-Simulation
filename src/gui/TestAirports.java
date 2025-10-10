package gui;

import gui.L2Panels.AirportsPanel;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestAirports {

    public static void main(String[] args) throws Exception {

        // Kreiramo container i dodamo par aerodroma
        AerodromContainer container = new AerodromContainer();
        container.add(new Aerodrom("Belgrade Nikola Tesla", "BEG", 44, 20));
        container.add(new Aerodrom("Paris Charles de Gaulle", "CDG", 49, 2));
        container.add(new Aerodrom("London Heathrow", "LHR", 51, -0));

        // Kreiramo AWT Frame
        Frame frame = new Frame("Airports Test");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        AirportsPanel apPanel = new AirportsPanel();

        // Dodajemo sve aerodrome iz container-a u AirportsPanel
        for (Aerodrom a : container.getAerodroms()) {
            apPanel.addAirport(
                    a.getName(),
                    a.getCode(),
                    String.valueOf(a.getX()),
                    String.valueOf(a.getY()),
                    "Yes"  // primer vrednosti za kolonu Shown
            );
        }

        frame.add(apPanel, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
