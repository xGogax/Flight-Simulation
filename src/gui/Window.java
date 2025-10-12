package gui;

import body.aerodrom.AerodromContainer;
import body.let.LetContainer;
import gui.L1Panels.LowerPanel;
import gui.L1Panels.UpperPanel;
import gui.Timer.AutoCloseTimer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends Frame {
    private AerodromContainer aerodroms = new AerodromContainer();
    private LetContainer letContainer = new LetContainer();

    private UpperPanel UP;
    private LowerPanel LP;
    private AutoCloseTimer autoCloseTimer;

    public Window() {
        setResizable(false);
        setTitle("Flight Simulator");

        AppContext ctx = AppContext.getInstance();
        ctx.setAerodromContainer(aerodroms);
        ctx.setLetContainer(letContainer);

        UP = new UpperPanel();
        ctx.setAirportsPanel(UP.getAirportsPanel());
        ctx.setFlightsPanel(UP.getFlightsPanel());
        ctx.setSimulator(UP.getSimulationPanel());

        LP = new LowerPanel();

        add(UP, BorderLayout.NORTH);
        add(LP, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        // AUTOCLOSE TIMER
        autoCloseTimer = new AutoCloseTimer(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                autoCloseTimer.stopTimer();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }
}