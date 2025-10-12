package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import gui.AppContext;

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

    public AddAirportPanel() {
        populateAddAirportPanel();
    }

    private void populateAddAirportPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Name & Code
        Panel row1 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        Panel namePanel = new Panel(new BorderLayout());
        nameLabel.setForeground(new Color(49, 95, 166));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        name.setFont(new Font("Arial", Font.BOLD, 20));
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(name, BorderLayout.CENTER);

        Panel codePanel = new Panel(new BorderLayout());
        codeLabel.setForeground(new Color(49, 95, 166));
        codeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        code.setFont(new Font("Arial", Font.BOLD, 20));
        codePanel.add(codeLabel, BorderLayout.NORTH);
        codePanel.add(code, BorderLayout.CENTER);

        row1.add(namePanel);
        row1.add(codePanel);

        // X & Y
        Panel row2 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Panel xPanel = new Panel(new BorderLayout());
        XLabel.setForeground(new Color(49, 95, 166));
        XLabel.setFont(new Font("Arial", Font.BOLD, 20));
        X.setFont(new Font("Arial", Font.BOLD, 20));
        xPanel.add(XLabel, BorderLayout.NORTH);
        xPanel.add(X, BorderLayout.CENTER);

        Panel yPanel = new Panel(new BorderLayout());
        YLabel.setForeground(new Color(49, 95, 166));
        YLabel.setFont(new Font("Arial", Font.BOLD, 20));
        Y.setFont(new Font("Arial", Font.BOLD, 20));
        yPanel.add(YLabel, BorderLayout.NORTH);
        yPanel.add(Y, BorderLayout.CENTER);

        row2.add(xPanel);
        row2.add(yPanel);

        // Button
        Panel row3 = new Panel(new BorderLayout());
        addAirport.setFont(new Font("Arial", Font.BOLD, 20));
        addAirport.setBackground(new Color(105, 161, 236));
        row3.add(addAirport, BorderLayout.CENTER);

        // Action
        addAirport.addActionListener((ae) -> {
            AppContext ctx = AppContext.getInstance();
            try {
                String xText = X.getText().trim();
                String yText = Y.getText().trim();

                if (!xText.matches("-?\\d+") || !yText.matches("-?\\d+")) {
                    throw new CoordsMustBeNumbers();
                }

                Aerodrom a = new Aerodrom(
                        name.getText().trim(),
                        code.getText().trim(),
                        Integer.parseInt(xText),
                        Integer.parseInt(yText)
                );

                ctx.getAerodromContainer().add(a);
                ctx.getAirportsPanel().refreshTable();
                ctx.getSimulator().refresh();
                ctx.getConsole().append("UPDATE: Added airport " + a.getName() +
                        " (" + a.getCode() + ") - (" + xText + ", " + yText + ")\n");

            } catch (InvalidYCoordinate | InvalidXCoordinate | InvalidName | InvalidCode |
                     CodeMustBeUnique | CoordsMustBeNumbers e) {
                ctx.getConsole().append("ERROR: " + e.getMessage() + "\n");
            }

            // Reset polja
            name.setText("");
            code.setText("");
            X.setText("");
            Y.setText("");
        });

        this.add(row1);
        this.add(row2);
        this.add(row3);
    }
}