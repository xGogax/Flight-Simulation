package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;

import javax.swing.*;
import java.awt.*;

public class AddAirportPanel extends Panel {
    TextField name = new TextField(15);
    TextField code = new TextField(15);
    TextField X = new TextField(15);
    TextField Y = new TextField(15);
    Button addAirport = new Button("Add Airport");

    Label nameLabel = new Label("Name");
    Label codeLabel = new Label("Code");
    Label XLabel = new Label("X");
    Label YLabel = new Label("Y");

    TextArea consoleArea;

    AerodromContainer aerodroms;
    AirportsPanel aerodromsPanel;
    SimulationPanel simulator;

    public AddAirportPanel(AerodromContainer aerodroms, AirportsPanel aerodromsPanel, TextArea consoleArea, SimulationPanel simulator) {
        this.simulator = simulator;
        this.consoleArea = consoleArea;
        this.aerodroms = aerodroms;
        this.aerodromsPanel = aerodromsPanel;
        populateAddAirportPanel();
    }

    private void populateAddAirportPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Name Code
        Panel row1 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Panel namePanel = new Panel(new BorderLayout());
        nameLabel.setForeground(new Color(49, 95, 166));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setAlignment(Label.LEFT);
        name.setFont(new Font("Arial", Font.BOLD, 20));
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(name, BorderLayout.CENTER);

        Panel codePanel = new Panel(new BorderLayout());
        codeLabel.setForeground(new Color(49, 95, 166));
        codeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        codeLabel.setAlignment(Label.LEFT);
        code.setFont(new Font("Arial", Font.BOLD, 20));
        codePanel.add(codeLabel, BorderLayout.NORTH);
        codePanel.add(code, BorderLayout.CENTER);

        row1.add(namePanel);
        row1.add(codePanel);

        //X i Y
        Panel row2 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        Panel xPanel = new Panel(new BorderLayout());
        XLabel.setForeground(new Color(49, 95, 166));
        XLabel.setFont(new Font("Arial", Font.BOLD, 20));
        XLabel.setAlignment(Label.LEFT);
        X.setFont(new Font("Arial", Font.BOLD, 20));
        xPanel.add(XLabel, BorderLayout.NORTH);
        xPanel.add(X, BorderLayout.CENTER);

        Panel yPanel = new Panel(new BorderLayout());
        YLabel.setForeground(new Color(49, 95, 166));
        YLabel.setFont(new Font("Arial", Font.BOLD, 20));
        YLabel.setAlignment(Label.LEFT);
        Y.setFont(new Font("Arial", Font.BOLD, 20));
        yPanel.add(YLabel, BorderLayout.NORTH);
        yPanel.add(Y, BorderLayout.CENTER);

        row2.add(xPanel);
        row2.add(yPanel);

        //DUGME
        Panel row3 = new Panel(new BorderLayout());
        addAirport.setFont(new Font("Arial", Font.BOLD, 20));
        addAirport.setBackground(new Color(105, 161, 236));
        row3.add(addAirport, BorderLayout.CENTER);

        //Akcija
        addAirport.addActionListener((ae) -> {
            try {
                String xText = X.getText().trim();
                String yText = Y.getText().trim();

                if (!xText.matches("-?\\d+") || !yText.matches("-?\\d+")) {
                    throw new CoordsMustBeNumbers();
                }

                aerodroms.add(new Aerodrom(
                        name.getText().trim(),
                        code.getText().trim(),
                        Integer.parseInt(xText),
                        Integer.parseInt(yText)
                ));

                consoleArea.append("UPDATE: Added airport " + name.getText() +
                        " (" + code.getText() + ") - (" + xText + ", " + yText + ")\n");

                aerodromsPanel.refreshTable();
                simulator.refresh();

            } catch (InvalidYCoordinate | InvalidXCoordinate | InvalidName | InvalidCode |
                     CodeMustBeUnique | CoordsMustBeNumbers e) {
                System.err.println(e.getMessage());
                consoleArea.append(e.getMessage());
                consoleArea.append("\n");
            }

            // Reset polja
            name.setText("");
            code.setText("");
            X.setText("");
            Y.setText("");
        });

        //Dodavanje redova u glavni panel
        this.add(row1);
        this.add(row2);
        this.add(row3);
    }
}