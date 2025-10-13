package gui.L2Panels;

import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import gui.Timer.AutoCloseTimer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class SimulationPanel extends Panel {
    private Label simPanel = new Label("Simulation");
    private Canvas simulator;
    private AerodromContainer aerodroms;

    private final int scale = 4;
    private final int canvasSize = 181 * scale;

    // selekcija i treperenje
    private Aerodrom selectedAerodrom = null;
    private boolean blinkState = false;
    private Timer blinkTimer = null;

    // AutoCloseTimer
    private AutoCloseTimer autoCloseTimer;
    // Setter za AutoCloseTimer
    public void setAutoCloseTimer(AutoCloseTimer timer) {
        this.autoCloseTimer = timer;
    }

    public SimulationPanel(AerodromContainer aerodroms) {
        this.aerodroms = aerodroms;
        populateSimulationPanel();
    }

    private void populateSimulationPanel() {
        this.setLayout(new BorderLayout());

        // TEXT
        this.add(simPanel, BorderLayout.NORTH);
        simPanel.setForeground(new Color(49, 95, 166));
        simPanel.setFont(new Font("Arial", Font.BOLD, 20));
        simPanel.setAlignment(Label.CENTER);

        // CANVAS
        simulator = new SimulatorCanvas();
        simulator.setSize(canvasSize, canvasSize);
        simulator.setBackground(new Color(82, 176, 221));

        simulator.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }


        });

        this.add(simulator, BorderLayout.CENTER);
    }

    public void refresh() {
        simulator.repaint();
    }

    private void handleClick(int x, int y) {
        for (Aerodrom a : aerodroms.getAerodroms()) {
            int ax = (a.getX() + 90) * scale;
            int ay = (90 - a.getY()) * scale;

            if (x >= ax && x <= ax + scale && y >= ay && y <= ay + scale) {
                toggleBlinking(a);
                return;
            }
        }
    }

    private void toggleBlinking(Aerodrom a) {
        if (a == selectedAerodrom) {
            // Zaustavi blink
            stopBlinking();
            selectedAerodrom = null;
            simulator.repaint();

            if (autoCloseTimer != null) {
                autoCloseTimer.resumeTimer();
            }
            return;
        }

        stopBlinking();

        selectedAerodrom = a;
        blinkState = false;

        // Pauziraj AutoCloseTimer dok treperi
        if (autoCloseTimer != null) {
            autoCloseTimer.pauseTimer();
        }

        // Start blink timer
        blinkTimer = new Timer(true);
        blinkTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                blinkState = !blinkState;
                simulator.repaint();
            }
        }, 0, 1000);
    }

    private void stopBlinking() {
        if (blinkTimer != null) {
            blinkTimer.cancel();
            blinkTimer = null;
        }
    }

    private class SimulatorCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            draw(g);
        }

        public void draw(Graphics g) {
            g.setFont(new Font("Arial", Font.BOLD, 12));

            for (Aerodrom a : aerodroms.getAerodroms()) {
                if(a.getShow()){
                    int xPixel = (a.getX() + 90) * scale;
                    int yPixel = (90 - a.getY()) * scale;

                    if (a == selectedAerodrom && blinkState) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.GRAY);
                    }

                    g.fillRect(xPixel, yPixel, scale, scale);

                    g.setColor(Color.BLACK);
                    g.drawString(a.getCode(), xPixel + scale + 2, yPixel + scale + 2);
                }
            }
        }
    }
}