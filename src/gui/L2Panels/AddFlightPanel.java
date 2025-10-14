package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;
import gui.AppContext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFlightPanel extends Panel {
    TextField start = new TextField(15);
    TextField end = new TextField(15);
    TextField takeOff = new TextField(15);
    TextField duration = new TextField(15);
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
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // ===== RED 1: Start i End =====
        Panel row1 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Panel startPanel = new Panel(new BorderLayout());
        startLabel.setForeground(new Color(49, 95, 166));
        startLabel.setFont(new Font("Arial", Font.BOLD, 20));
        startPanel.add(startLabel, BorderLayout.NORTH);
        start.setFont(new Font("Arial", Font.BOLD, 20));
        startPanel.add(start, BorderLayout.CENTER);

        Panel endPanel = new Panel(new BorderLayout());
        endLabel.setForeground(new Color(49, 95, 166));
        endLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endPanel.add(endLabel, BorderLayout.NORTH);
        end.setFont(new Font("Arial", Font.BOLD, 20));
        endPanel.add(end, BorderLayout.CENTER);

        row1.add(startPanel);
        row1.add(endPanel);

        row1.setPreferredSize(new Dimension(0, 70));

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(row1, gbc);

        // ===== RED 2: TakeOff i Duration =====
        Panel row2 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Panel takeOffPanel = new Panel(new BorderLayout());
        takeOffLabel.setForeground(new Color(49, 95, 166));
        takeOffLabel.setFont(new Font("Arial", Font.BOLD, 20));
        takeOffPanel.add(takeOffLabel, BorderLayout.NORTH);
        takeOff.setFont(new Font("Arial", Font.BOLD, 20));
        takeOffPanel.add(takeOff, BorderLayout.CENTER);

        Panel durationPanel = new Panel(new BorderLayout());
        durationLabel.setForeground(new Color(49, 95, 166));
        durationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        durationPanel.add(durationLabel, BorderLayout.NORTH);
        duration.setFont(new Font("Arial", Font.BOLD, 20));
        durationPanel.add(duration, BorderLayout.CENTER);

        row2.add(takeOffPanel);
        row2.add(durationPanel);

        // Ograničavamo visinu reda
        row2.setPreferredSize(new Dimension(0, 70));

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(row2, gbc);

        // ===== RED 3: Dugme full-width =====
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addFlight.setFont(new Font("Arial", Font.BOLD, 20));
        addFlight.setBackground(new Color(105, 161, 236));
        this.add(addFlight, gbc);

        addFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleAddFlight();
            }
        });
    }

    private void handleAddFlight() {
        try {
            String startCode = start.getText().trim();
            String endCode = end.getText().trim();

            Aerodrom startTemp = null;
            Aerodrom endTemp = null;

            for (Aerodrom a : aerodroms.getAerodroms()) {
                if (a.getCode().equals(startCode)) startTemp = a;
                if (endTemp == null && a.getCode().equals(endCode)) endTemp = a;
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

            consoleArea.append("UPDATE: Added flight (" + startCode + " -> " + endCode + "), TakeOff: "
                    + formattedTakeOff + ", Duration: " + dur + "\n");

            flightsPanel.refreshTable();

        } catch (FlightMustHaveAirport | InvalidTime | FlightDuration | SameAirports | FlightDurationString e) {
            consoleArea.append("ERROR: " + e.getMessage() + "\n");
        }

        start.setText("");
        end.setText("");
        duration.setText("");
        takeOff.setText("");
    }
}