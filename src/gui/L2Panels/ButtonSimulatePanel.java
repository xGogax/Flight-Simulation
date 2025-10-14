package gui.L2Panels;

import body.AirportFlightTable;
import body.let.Let;
import gui.AppContext;

import java.awt.*;
import java.util.List;

public class ButtonSimulatePanel extends Panel {
    private Button simulate;
    private Button pause;

    private volatile boolean paused = false; // flag za pauzu
    private volatile boolean running = false; // da se thread ne pokrene vise puta

    public ButtonSimulatePanel() {
        AppContext ctx = AppContext.getInstance();
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        simulate = new Button("Simulate");
        pause = new Button("Pause");

        Dimension buttonSize = new Dimension(300, 40);
        simulate.setPreferredSize(buttonSize);
        pause.setPreferredSize(buttonSize);
        pause.setEnabled(false);

        simulate.setFont(new Font("Arial", Font.BOLD, 16));
        pause.setFont(new Font("Arial", Font.BOLD, 16));

        simulate.setBackground(new Color(105, 161, 236));
        pause.setBackground(new Color(105, 161, 236));

        // --- Simulate dugme ---
        simulate.addActionListener((ae) -> {
            pause.setEnabled(true);
            if (running) return;
            running = true;

            AirportFlightTable aft = new AirportFlightTable(ctx.getAerodromContainer(), ctx.getLetContainer());
            aft.sort();
            System.out.println(aft);

            new Thread(() -> {
                int hours = 0;
                int minutes = 0;
                boolean wasPaused = false;

                try {
                    EventQueue.invokeLater(() ->
                            AppContext.getInstance().getConsole().append("Simulation started.\n")
                    );

                    while (hours < 24) {
                        if (paused && !wasPaused) {
                            EventQueue.invokeLater(() ->
                                    AppContext.getInstance().getConsole().append("Simulation paused.\n")
                            );
                            wasPaused = true;
                        }

                        while (paused) {
                            Thread.sleep(100);
                        }

                        if (wasPaused) {
                            wasPaused = false;
                        }

                        String timeStr = String.format("%02d:%02d", hours, minutes);
                        EventQueue.invokeLater(() ->
                                AppContext.getInstance().getConsole().append(timeStr + "\n")
                        );

                        List<Let> flightsInInterval = aft.getFlightsInterval(hours, minutes);
                        EventQueue.invokeLater(() -> {
                            for (Let let : flightsInInterval) {
                                AppContext.getInstance().getConsole().append("INFO: Flight " + let.getStart().getCode() + " -> " + let.getEnd().getCode() + " has begun.\n");
                            }
                        });

                        minutes += 10;
                        if (minutes >= 60) {
                            minutes = 0;
                            hours++;
                        }

                        Thread.sleep(1000);
                    }
                    if(hours == 24) {
                        pause.setEnabled(false);
                    }

                    EventQueue.invokeLater(() ->{
                        AppContext.getInstance().getConsole().append("Simulation finished.\n");
                        aft.reset();
                        pause.setEnabled(false);
                        pause.setLabel("Pause");
                        paused = false;
                        running = false;
                    });

                } catch (InterruptedException e) {
                    EventQueue.invokeLater(() ->
                            AppContext.getInstance().getConsole().append(e.getMessage() + "\n")
                    );
                }
            }).start();
        });

        // --- Pause dugme ---
        pause.addActionListener((ae) -> {
            if (!paused) {
                paused = true;
                pause.setLabel("Resume");
            } else {
                paused = false;
                pause.setLabel("Pause");
            }
        });

        add(simulate);
        add(pause);
    }

    public Button getSimulate() { return simulate; }
    public Button getPause() { return pause; }
}