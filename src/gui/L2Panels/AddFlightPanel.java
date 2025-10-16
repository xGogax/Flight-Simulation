package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;
import gui.AppContext;

import java.awt.*;

public class AddFlightPanel extends Panel {
    TextField start = new TextField();
    TextField end = new TextField();
    TextField takeOff = new TextField();
    TextField duration = new TextField();
    Button addFlight = new Button("Add Flight");

    Label startLabel = new Label("Start Airport");
    Label endLabel = new Label("End Airport");
    Label takeOffLabel = new Label("Take Off");
    Label durationLabel = new Label("Duration");

    private TextArea consoleArea;
    private LetContainer letContainer;
    private AerodromContainer aerodroms;
    private FlightsPanel flightsPanel;

    public AddFlightPanel() {
        AppContext ctx = AppContext.getInstance();
        this.consoleArea = ctx.getConsole();
        this.letContainer = ctx.getLetContainer();
        this.aerodroms = ctx.getAerodromContainer();
        this.flightsPanel = ctx.getFlightsPanel();

        populateAddFlightPanel();
    }

    private void populateAddFlightPanel() {
        this.setLayout(new BorderLayout(5,5));
        this.setBackground(new Color(206, 237, 249));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.BOLD, 14);
        Dimension fieldSize = new Dimension(80, 25);

        // --- Panel sa poljima ---
        Panel fieldsPanel = new Panel(new GridLayout(2, 2, 5, 5));
        fieldsPanel.setBackground(new Color(206, 237, 249));

        // START
        startLabel.setFont(labelFont);
        startLabel.setForeground(new Color(49, 95, 166));
        start.setFont(fieldFont);
        start.setPreferredSize(fieldSize);
        Panel startPanel = new Panel(new BorderLayout());
        startPanel.add(startLabel, BorderLayout.NORTH);
        startPanel.add(start, BorderLayout.CENTER);

        // END
        endLabel.setFont(labelFont);
        endLabel.setForeground(new Color(49, 95, 166));
        end.setFont(fieldFont);
        end.setPreferredSize(fieldSize);
        Panel endPanel = new Panel(new BorderLayout());
        endPanel.add(endLabel, BorderLayout.NORTH);
        endPanel.add(end, BorderLayout.CENTER);

        // TAKEOFF
        takeOffLabel.setFont(labelFont);
        takeOffLabel.setForeground(new Color(49, 95, 166));
        takeOff.setFont(fieldFont);
        takeOff.setPreferredSize(fieldSize);
        Panel takeOffPanel = new Panel(new BorderLayout());
        takeOffPanel.add(takeOffLabel, BorderLayout.NORTH);
        takeOffPanel.add(takeOff, BorderLayout.CENTER);

        // DURATION
        durationLabel.setFont(labelFont);
        durationLabel.setForeground(new Color(49, 95, 166));
        duration.setFont(fieldFont);
        duration.setPreferredSize(fieldSize);
        Panel durationPanel = new Panel(new BorderLayout());
        durationPanel.add(durationLabel, BorderLayout.NORTH);
        durationPanel.add(duration, BorderLayout.CENTER);

        fieldsPanel.add(startPanel);
        fieldsPanel.add(endPanel);
        fieldsPanel.add(takeOffPanel);
        fieldsPanel.add(durationPanel);

        // --- Dugme ---
        addFlight.setFont(fieldFont);
        addFlight.setBackground(new Color(105, 161, 236));
        addFlight.setPreferredSize(new Dimension(100, 25));

        this.add(fieldsPanel, BorderLayout.CENTER);
        this.add(addFlight, BorderLayout.SOUTH);

        // --- Action ---
        addFlight.addActionListener((ae) -> handleAddFlight());
    }

    private void handleAddFlight() {
        try {
            String startCode = start.getText().trim();
            String endCode = end.getText().trim();

            Aerodrom startTemp = null;
            Aerodrom endTemp = null;

            for (Aerodrom a : aerodroms.getAerodroms()) {
                if (a.getCode().equals(startCode))
                    startTemp = a;
                if (endTemp == null && a.getCode().equals(endCode))
                    endTemp = a;
            }

            if (startTemp == null || endTemp == null)
                throw new FlightMustHaveAirport();

            String[] parts = takeOff.getText().split(":");
            if (parts.length != 2 || !parts[0].matches("\\d+") || !parts[1].matches("\\d+"))
                throw new InvalidTime();

            int first = Integer.parseInt(parts[0]);
            int second = Integer.parseInt(parts[1]);
            String formattedTakeOff = String.format("%02d:%02d", first, second);

            if (!duration.getText().matches("\\d+"))
                throw new FlightDurationString();

            int dur = Integer.parseInt(duration.getText());

            letContainer.add(new Let(startTemp, endTemp, first, second, dur));
            consoleArea.append(
                    "UPDATE: Added flight (" + startCode + " -> " + endCode +
                            "), TakeOff: " + formattedTakeOff + ", Duration: " + dur + "\n"
            );

            flightsPanel.refreshTable();

        } catch (Exception e) {
            consoleArea.append(e.getMessage() + "\n");
        }

        start.setText("");
        end.setText("");
        duration.setText("");
        takeOff.setText("");
    }
}