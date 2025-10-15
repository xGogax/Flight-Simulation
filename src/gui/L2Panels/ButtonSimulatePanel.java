package gui.L2Panels;

import body.AirportFlightTable;
import body.let.Let;
import body.threadback.SharedData;
import gui.AppContext;

import java.awt.*;
import java.util.List;

public class ButtonSimulatePanel extends Panel {
    private Button simulate;
    private Button pause;

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

        SharedData data = new SharedData();
        ctx.setSharedData(data);

        // --- Simulate dugme ---
        simulate.addActionListener((ae) -> {
            pause.setEnabled(true);
            if (data.running) return;
            data.running = true;

            AirportFlightTable aft = new AirportFlightTable(ctx.getAerodromContainer(), ctx.getLetContainer());
            aft.sort();

            // --- Glavni thread: vreme i pokretanje letova ---
            new Thread(() -> {
                try {
                    EventQueue.invokeLater(() ->
                            AppContext.getInstance().getConsole().append("Simulation started.\n")
                    );

                    while (data.hours < 24) {
                        // Pauza
                        while (data.paused) Thread.sleep(100);

                        // Vreme simulacije
                        String timeStr = String.format("%02d:%02d", data.hours, data.minutes);
                        EventQueue.invokeLater(() ->
                                AppContext.getInstance().getConsole().append(timeStr + "\n")
                        );

                        // Letovi koji krecu
                        List<Let> flightsInInterval = aft.getFlightsInterval(data.hours, data.minutes);
                        for (Let let : flightsInInterval) {
                            EventQueue.invokeLater(() ->
                                    AppContext.getInstance().getConsole().append("INFO: Flight " + let.getStart().getCode() + " -> " + let.getEnd().getCode() + " has started\n")
                            );
                            synchronized (data.activeFlights) {
                                data.activeFlights.add(let);
                                data.activeFlights.notify();
                            }
                        }

                        // Pomeranje simulacije za 10 minuta
                        data.minutes += 10;
                        if (data.minutes >= 60) {
                            data.minutes = 0;
                            data.hours++;
                        }

                        Thread.sleep(1000); // 1 sekunda = 10 minuta simulacije
                    }

                    EventQueue.invokeLater(() -> {
                        AppContext.getInstance().getConsole().append("Simulation finished.\n");
                        aft.reset();
                        pause.setEnabled(false);
                        pause.setLabel("Pause");
                        data.paused = false;
                        data.running = false;
                    });

                } catch (InterruptedException e) {
                    EventQueue.invokeLater(() ->
                            AppContext.getInstance().getConsole().append(e.getMessage() + "\n")
                    );
                }
            }).start();

            // --- Refresh thread: crtanje i landing ---
            new Thread(() -> {
                try {
                    while (data.hours < 24 || !data.activeFlights.isEmpty()) {
                        while (data.paused) Thread.sleep(100);

                        synchronized (data.activeFlights) {
                            var iter = data.activeFlights.iterator();
                            while (iter.hasNext()) {
                                Let let = iter.next();
                                let.step();

                                if (let.isFinished()) {
                                    EventQueue.invokeLater(() ->
                                            AppContext.getInstance().getConsole().append("INFO: Flight " + let.getStart().getCode() + " -> " + let.getEnd().getCode() + " has landed\n")
                                    );
                                    iter.remove();

                                    if (data.activeFlights.isEmpty()) {
                                        EventQueue.invokeLater(() -> ctx.getSimulator().refresh());
                                    }
                                }
                            }

                            if (data.activeFlights.isEmpty()) {
                                data.activeFlights.wait();
                            } else {
                                EventQueue.invokeLater(() -> ctx.getSimulator().refresh());
                            }
                        }

                        if (!data.activeFlights.isEmpty()) {
                            Thread.sleep(200); // refresh svakih 200ms samo kad ima letova
                        }
                    }
                } catch (InterruptedException e) {
                    EventQueue.invokeLater(() ->
                            AppContext.getInstance().getConsole().append(e.getMessage() + "\n")
                    );
                }
            }).start();
        });

        // --- Pause dugme ---
        pause.addActionListener((ae) -> {
            if (!data.paused) {
                data.paused = true;
                pause.setLabel("Resume");
            } else {
                data.paused = false;
                pause.setLabel("Pause");
            }
        });

        add(simulate);
        add(pause);
    }

    public Button getSimulate() { return simulate; }
    public Button getPause() { return pause; }
}