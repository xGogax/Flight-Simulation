package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;

import javax.swing.*;
import java.awt.*;

public class AddFlightPanel extends Panel {
    TextField start = new TextField(20);
    TextField end = new TextField(15);
    TextField takeOff = new TextField(15);
    TextField duration = new TextField(15);
    Button addFlight = new Button("Add Flight");

    Label startLabel = new Label("Start Airport");
    Label endLabel = new Label("End Airport");
    Label takeOffLabel = new Label("Take Off");
    Label durationLabel = new Label("Duration");

    TextArea consoleArea;

    LetContainer letContainer;
    AerodromContainer aerodroms;

    FlightsPanel flightsPanel;

    public AddFlightPanel(LetContainer letContainer, AerodromContainer aerodroms, TextArea consoleArea, FlightsPanel flightsPanel) {
        this.consoleArea = consoleArea;
        this.letContainer = letContainer;
        this.aerodroms = aerodroms;
        this.flightsPanel = flightsPanel;
        populateAddFlightPanel();
    }

    private void populateAddFlightPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // START END
        Panel row1 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        Panel startPanel = new Panel(new BorderLayout());
        startLabel.setForeground(new Color(49, 95, 166));
        startLabel.setFont(new Font("Arial", Font.BOLD, 20));
        startLabel.setAlignment(Label.LEFT);
        start.setFont(new Font("Arial", Font.BOLD, 20));
        startPanel.add(startLabel, BorderLayout.NORTH);
        startPanel.add(start, BorderLayout.CENTER);

        Panel endPanel = new Panel(new BorderLayout());
        endLabel.setForeground(new Color(49, 95, 166));
        endLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endLabel.setAlignment(Label.LEFT);
        end.setFont(new Font("Arial", Font.BOLD, 20));
        endPanel.add(endLabel, BorderLayout.NORTH);
        endPanel.add(end, BorderLayout.CENTER);

        row1.add(startPanel);
        row1.add(endPanel);

        // TAKEOFF DURATION
        Panel row2 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Panel takeOffPanel = new Panel(new BorderLayout());
        takeOffLabel.setForeground(new Color(49, 95, 166));
        takeOffLabel.setFont(new Font("Arial", Font.BOLD, 20));
        takeOffLabel.setAlignment(Label.LEFT);
        takeOff.setFont(new Font("Arial", Font.BOLD, 20));
        takeOffPanel.add(takeOffLabel, BorderLayout.NORTH);
        takeOffPanel.add(takeOff, BorderLayout.CENTER);

        Panel durationPanel = new Panel(new BorderLayout());
        durationLabel.setForeground(new Color(49, 95, 166));
        durationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        durationLabel.setAlignment(Label.LEFT);
        duration.setFont(new Font("Arial", Font.BOLD, 20));
        durationPanel.add(durationLabel, BorderLayout.NORTH);
        durationPanel.add(duration, BorderLayout.CENTER);

        row2.add(takeOffPanel);
        row2.add(durationPanel);

        // BUTTON
        Panel row3 = new Panel(new BorderLayout());
        addFlight.setFont(new Font("Arial", Font.BOLD, 20));
        addFlight.setBackground(new Color(105, 161, 236));
        row3.add(addFlight, BorderLayout.CENTER);

        addFlight.addActionListener((ae) -> {
            try{
                String startCode = start.getText().trim();
                String endCode = end.getText().trim();

                Aerodrom startTemp = null;
                Aerodrom endTemp = null;

                for(Aerodrom a : aerodroms.getAerodroms()) {
                    if(a.getCode().equals(startCode)) {
                        startTemp = a;
                    }
                    if (a.getCode().equals(endCode)) {
                        endTemp = a;
                    }
                }
                if(startTemp == null || endTemp == null) {
                    throw new FlightMustHaveAirport();
                }

                String[] parts = takeOff.getText().split(":");
                if(!parts[0].matches("-?\\d+") || !parts[1].matches("-?\\d+")){
                    throw new InvalidTime();
                }
                int first = Integer.parseInt(parts[0]);
                int second = Integer.parseInt(parts[1]);

                if(!duration.getText().matches("-?\\d+")){
                    throw new FlightDurationString();
                }

                letContainer.add(new Let(
                        startTemp,
                        endTemp,
                        first,
                        second,
                        Integer.parseInt(duration.getText())
                ));

                consoleArea.append("UPDATE: Added flight (" + start.getText() + " - " +  end.getText() + "), TakeOff: " + takeOff.getText() + ", Duration: " + duration.getText() + "\n");

                flightsPanel.refreshTable();
            } catch (FlightMustHaveAirport | InvalidTime | FlightDuration | SameAirports | FlightDurationString e) {
                System.err.println(e.getMessage());
                consoleArea.append(e.getMessage());
                consoleArea.append("\n");
            }

            start.setText("");
            end.setText("");
            duration.setText("");
            takeOff.setText("");
        });

        // Dodavanje svih redova u glavni panel
        this.add(row1);
        this.add(row2);
        this.add(row3);
    }
}