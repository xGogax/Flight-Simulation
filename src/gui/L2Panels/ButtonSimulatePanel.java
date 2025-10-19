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
    private Button stop;

    public ButtonSimulatePanel() {
        AppContext ctx = AppContext.getInstance();
        setLayout(new GridLayout(1, 3, 5, 0));

        // --- Dugmad ---
        simulate = new Button("Simulate");
        pause = new Button("Pause");
        stop = new Button("Stop");

        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        Color buttonColor = new Color(105, 161, 236);

        simulate.setFont(buttonFont);
        pause.setFont(buttonFont);
        stop.setFont(buttonFont);

        simulate.setBackground(buttonColor);
        pause.setBackground(buttonColor);
        stop.setBackground(buttonColor);

        pause.setEnabled(false);

        // --- SharedData ---
        SharedData data = new SharedData();
        ctx.setSharedData(data);

        // --- Akcije ---
        simulate.addActionListener((ae) -> startSimulation(ctx, data));
        pause.addActionListener((ae) -> togglePause(data));
        stop.addActionListener((ae) -> stopSimulation(ctx, data));

        // --- Dodavanje u panel ---
        add(simulate);
        add(pause);
        add(stop);
    }

    // --- Metode za akcije ---
    private void startSimulation(AppContext ctx, SharedData data) {
        pause.setEnabled(true);
        if (data.running) return;
        data.running = true;

        AirportFlightTable aft = new AirportFlightTable(ctx.getAerodromContainer(), ctx.getLetContainer());
        aft.sort();

        // --- Glavni thread ---
        new Thread(() -> {
            try {
                EventQueue.invokeLater(() -> ctx.getConsole().append("Simulation started.\n"));

                while (data.hours < 24 && data.running) {
                    while (data.paused && data.running) Thread.sleep(100);
                    if (!data.running) break;

                    String timeStr = String.format("%02d:%02d", data.hours, data.minutes);
                    EventQueue.invokeLater(() -> ctx.getConsole().append(timeStr + "\n"));

                    List<Let> flightsInInterval = aft.getFlightsInterval(data.hours, data.minutes);
                    for (Let let : flightsInInterval) {
                        EventQueue.invokeLater(() -> ctx.getConsole().append(
                                "INFO: Flight " + let.getStart().getCode() + " -> " + let.getEnd().getCode() + " has started\n"));
                        synchronized (data.activeFlights) {
                            data.activeFlights.add(let);
                            data.activeFlights.notify();
                        }
                    }

                    data.minutes += 10;
                    if (data.minutes >= 60) {
                        data.minutes = 0;
                        data.hours++;
                    }

                    Thread.sleep(1000); // 1 sekunda = 10 minuta simulacije
                }

                EventQueue.invokeLater(() -> {
                    if (data.running) ctx.getConsole().append("Simulation finished.\n");
                    aft.reset();
                    pause.setEnabled(false);
                    pause.setLabel("Pause");
                    data.paused = false;
                    data.running = false;
                });

            } catch (InterruptedException e) {
                EventQueue.invokeLater(() -> ctx.getConsole().append(e.getMessage() + "\n"));
            }
        }).start();

        // --- Refresh thread ---
        new Thread(() -> {
            try {
                while ((data.hours < 24 || !data.activeFlights.isEmpty()) && data.running) {
                    while (data.paused && data.running) Thread.sleep(100);
                    if (!data.running) break;

                    synchronized (data.activeFlights) {
                        var iter = data.activeFlights.iterator();
                        while (iter.hasNext()) {
                            Let let = iter.next();
                            let.step();
                            if (let.isFinished()) {
                                EventQueue.invokeLater(() -> ctx.getConsole().append(
                                        "INFO: Flight " + let.getStart().getCode() + " -> " + let.getEnd().getCode() + " has landed\n"));
                                iter.remove();
                            }
                        }
                        EventQueue.invokeLater(() -> ctx.getSimulator().refresh());

                        if (data.activeFlights.isEmpty()) {
                            data.activeFlights.wait();
                        }
                    }

                    if (!data.activeFlights.isEmpty()) Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                EventQueue.invokeLater(() -> ctx.getConsole().append(e.getMessage() + "\n"));
            }
        }).start();
    }

    private void togglePause(SharedData data) {
        if (!data.paused) {
            data.paused = true;
            pause.setLabel("Resume");
        } else {
            data.paused = false;
            pause.setLabel("Pause");
        }
    }

    private void stopSimulation(AppContext ctx, SharedData data) {
        data.running = false;
        data.paused = false;

        synchronized (data.activeFlights) {
            data.activeFlights.clear();
            data.activeFlights.notifyAll();
        }

        data.hours = 0;
        data.minutes = 0;

        AirportFlightTable aft = new AirportFlightTable(
                ctx.getAerodromContainer(),
                ctx.getLetContainer()
        );
        aft.reset();

        pause.setEnabled(false);
        pause.setLabel("Pause");

        EventQueue.invokeLater(() -> {
            ctx.getConsole().append("Simulation stopped.\n");
            ctx.getSimulator().refresh();
        });
    }

    public Button getSimulate() { return simulate; }
    public Button getPause() { return pause; }
    public Button getStop() { return stop; }
}