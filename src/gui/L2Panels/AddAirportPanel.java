package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;

import java.awt.*;
import java.awt.event.ActionEvent;

public class AddAirportPanel extends Panel {
    TextField name = new TextField(20);
    TextField code = new TextField(3);
    TextField X = new TextField(2);
    TextField Y = new TextField(2);
    Button addAirport = new Button("Add Airport");
    AerodromContainer aerodroms;
    AirportsPanel aerodromsPanel;

    Label nameLabel = new Label("Name:");
    Label codeLabel = new Label("Code:");
    Label XLabel = new Label("X:");
    Label YLabel = new Label("Y:");

    public void populateAddAirportPanel(){
        this.setLayout(new BorderLayout(10, 10));

        // NAME FIELD
        Panel topPanel = new Panel(new BorderLayout());
        nameLabel.setForeground(new Color(49, 95, 166));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setAlignment(Label.LEFT);
        name.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(nameLabel, BorderLayout.NORTH);
        topPanel.add(name, BorderLayout.CENTER);

        // CODE, X, Y
        Panel middlePanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // CODE
        Panel codePanel = new Panel(new BorderLayout());
        codeLabel.setForeground(new Color(49, 95, 166));
        codeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        codeLabel.setAlignment(Label.LEFT);
        code.setFont(new Font("Arial", Font.BOLD, 20));
        middlePanel.add(codeLabel, BorderLayout.NORTH);
        middlePanel.add(code, BorderLayout.CENTER);

        // X
        Panel xPanel = new Panel(new BorderLayout());
        XLabel.setForeground(new Color(49, 95, 166));
        XLabel.setFont(new Font("Arial", Font.BOLD, 20));
        XLabel.setAlignment(Label.LEFT);
        X.setFont(new Font("Arial", Font.BOLD, 20));
        middlePanel.add(XLabel, BorderLayout.NORTH);
        middlePanel.add(X, BorderLayout.CENTER);

        // Y
        Panel yPanel = new Panel(new BorderLayout());
        YLabel.setForeground(new Color(49, 95, 166));
        YLabel.setFont(new Font("Arial", Font.BOLD, 20));
        YLabel.setAlignment(Label.LEFT);
        Y.setFont(new Font("Arial", Font.BOLD, 20));
        middlePanel.add(YLabel, BorderLayout.NORTH);
        middlePanel.add(Y,  BorderLayout.CENTER);

        // Button
        Panel bottomPanel = new Panel(new BorderLayout());
        addAirport.setFont(new Font("Arial", Font.BOLD, 20));
        addAirport.setBackground(new Color(105, 161, 236));
        bottomPanel.add(addAirport, BorderLayout.NORTH);

        addAirport.addActionListener((ae) -> {
            try{
                aerodroms.add(new Aerodrom(name.getText(), code.getText(), Integer.parseInt(X.getText()), Integer.parseInt(Y.getText())));
                aerodromsPanel.refreshTable();
            } catch (InvalidYCoordinate | InvalidXCoordinate | InvalidName | InvalidCode | CodeMustBeUnique e) {
                System.err.println(e.getMessage());
            }

            X.setText("");
            Y.setText("");
            name.setText("");
            code.setText("");
        });

        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
    }

    public AddAirportPanel(AerodromContainer aerodroms, AirportsPanel aerodromsPanel) {
        this.aerodroms =  aerodroms;
        this.aerodromsPanel = aerodromsPanel;
        populateAddAirportPanel();
    }
}