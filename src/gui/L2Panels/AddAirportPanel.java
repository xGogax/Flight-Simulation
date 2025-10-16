package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import gui.AppContext;

import java.awt.*;

public class AddAirportPanel extends Panel {
    TextField name = new TextField();
    TextField code = new TextField();
    TextField X = new TextField();
    TextField Y = new TextField();
    Button addAirport = new Button("Add Airport");

    Label nameLabel = new Label("Name");
    Label codeLabel = new Label("Code");
    Label XLabel = new Label("X");
    Label YLabel = new Label("Y");

    public AddAirportPanel() {
        populateAddAirportPanel();
    }

    private void populateAddAirportPanel() {
        this.setLayout(new BorderLayout(5, 5));
        this.setBackground(new Color(206, 237, 249));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.BOLD, 14);
        Dimension fieldSize = new Dimension(80, 25);

        // --- Panel sa poljima ---
        Panel fieldsPanel = new Panel(new GridLayout(2, 2, 5, 5));
        fieldsPanel.setBackground(new Color(206, 237, 249));

        // NAME
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(new Color(49, 95, 166));
        name.setFont(fieldFont);
        name.setPreferredSize(fieldSize);
        Panel namePanel = new Panel(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(name, BorderLayout.CENTER);

        // CODE
        codeLabel.setFont(labelFont);
        codeLabel.setForeground(new Color(49, 95, 166));
        code.setFont(fieldFont);
        code.setPreferredSize(fieldSize);
        Panel codePanel = new Panel(new BorderLayout());
        codePanel.add(codeLabel, BorderLayout.NORTH);
        codePanel.add(code, BorderLayout.CENTER);

        // X
        XLabel.setFont(labelFont);
        XLabel.setForeground(new Color(49, 95, 166));
        X.setFont(fieldFont);
        X.setPreferredSize(fieldSize);
        Panel xPanel = new Panel(new BorderLayout());
        xPanel.add(XLabel, BorderLayout.NORTH);
        xPanel.add(X, BorderLayout.CENTER);

        // Y
        YLabel.setFont(labelFont);
        YLabel.setForeground(new Color(49, 95, 166));
        Y.setFont(fieldFont);
        Y.setPreferredSize(fieldSize);
        Panel yPanel = new Panel(new BorderLayout());
        yPanel.add(YLabel, BorderLayout.NORTH);
        yPanel.add(Y, BorderLayout.CENTER);

        fieldsPanel.add(namePanel);
        fieldsPanel.add(codePanel);
        fieldsPanel.add(xPanel);
        fieldsPanel.add(yPanel);

        // --- Dugme ---
        addAirport.setFont(fieldFont);
        addAirport.setBackground(new Color(105, 161, 236));
        addAirport.setPreferredSize(new Dimension(100, 25));

        this.add(fieldsPanel, BorderLayout.CENTER);
        this.add(addAirport, BorderLayout.SOUTH);

        // --- Action ---
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
                ctx.getConsole().append(
                        "UPDATE: Added airport " + a.getName() + " (" + a.getCode() +
                                ") - (" + xText + ", " + yText + ")\n"
                );

            } catch (Exception e) {
                ctx.getConsole().append(e.getMessage() + "\n");
            }

            name.setText("");
            code.setText("");
            X.setText("");
            Y.setText("");
        });
    }
}