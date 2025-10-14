package gui.L2Panels;

import Exceptions.*;
import body.aerodrom.Aerodrom;
import gui.AppContext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // ===== RED 1: Name i Code =====
        Panel row1 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Panel namePanel = new Panel(new BorderLayout());
        nameLabel.setForeground(new Color(49, 95, 166));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        namePanel.add(nameLabel, BorderLayout.NORTH);
        name.setFont(new Font("Arial", Font.BOLD, 20));
        namePanel.add(name, BorderLayout.CENTER);

        Panel codePanel = new Panel(new BorderLayout());
        codeLabel.setForeground(new Color(49, 95, 166));
        codeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        codePanel.add(codeLabel, BorderLayout.NORTH);
        code.setFont(new Font("Arial", Font.BOLD, 20));
        codePanel.add(code, BorderLayout.CENTER);

        row1.add(namePanel);
        row1.add(codePanel);

        row1.setPreferredSize(new Dimension(0, 70));

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(row1, gbc);

        // ===== RED 2: X i Y =====
        Panel row2 = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        Panel xPanel = new Panel(new BorderLayout());
        XLabel.setForeground(new Color(49, 95, 166));
        XLabel.setFont(new Font("Arial", Font.BOLD, 20));
        xPanel.add(XLabel, BorderLayout.NORTH);
        X.setFont(new Font("Arial", Font.BOLD, 20));
        xPanel.add(X, BorderLayout.CENTER);

        Panel yPanel = new Panel(new BorderLayout());
        YLabel.setForeground(new Color(49, 95, 166));
        YLabel.setFont(new Font("Arial", Font.BOLD, 20));
        yPanel.add(YLabel, BorderLayout.NORTH);
        Y.setFont(new Font("Arial", Font.BOLD, 20));
        yPanel.add(Y, BorderLayout.CENTER);

        row2.add(xPanel);
        row2.add(yPanel);

        row2.setPreferredSize(new Dimension(0, 70));

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(row2, gbc);

        // ===== RED 3: Dugme =====
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addAirport.setFont(new Font("Arial", Font.BOLD, 20));
        addAirport.setBackground(new Color(105, 161, 236));
        this.add(addAirport, gbc);

        addAirport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleAddAirport();
            }
        });
    }

    private void handleAddAirport() {
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
    }
}