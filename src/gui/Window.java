package gui;

import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.LetContainer;
import gui.L1Panels.LowerPanel;
import gui.L1Panels.UpperPanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends Frame {
    private AerodromContainer aerodroms = new AerodromContainer();
    private LetContainer letContainer = new LetContainer();

    private UpperPanel UP = new UpperPanel(aerodroms, letContainer);
    private LowerPanel LP = new LowerPanel(aerodroms, UP.getAirportsPanel(), letContainer, UP.getFlightsPanel());

    private void populateWindow() {
        this.add(UP, BorderLayout.NORTH);
        this.add(LP, BorderLayout.SOUTH);
    }

    public Window() {
        setResizable(false);
        setTitle("Flight Simulator");

        populateWindow();
        pack();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }
}
