package gui.L2Panels;

import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;

import java.awt.*;

public class AirportsPanel extends Panel {
    private Label airPanel = new Label("Airports");
    private Panel tablePanel = new Panel(null);
    private Panel tableContainer = new Panel();
    private ScrollPane scrollPane;
    private final int rowHeight = 30;
    private AerodromContainer container;

    private void populateAirportsPanel() {
        this.setLayout(new BorderLayout());

        // LABELA
        this.add(airPanel, BorderLayout.NORTH);
        airPanel.setForeground(new Color(49, 95, 166));
        airPanel.setFont(new Font("Arial", Font.BOLD, 20));
        airPanel.setAlignment(Label.CENTER);

        //TABELA
        // Container
        tableContainer.setLayout(new BorderLayout());
        Panel headerPanel = createRow(
                new String[]{"Name", "Code", "X", "Y", "Shown"},
                new Color(51, 160, 209)
        );
        tableContainer.add(headerPanel, BorderLayout.NORTH);

        // Scroll
        tablePanel.setBackground(new Color(124, 174, 194));
        scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(tablePanel);
        scrollPane.setBackground(new Color(82, 176, 221));
        scrollPane.setPreferredSize(new Dimension(735, 181 * 4 - 25));

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        this.add(tableContainer, BorderLayout.CENTER);
    }

    public AirportsPanel(AerodromContainer container) {
        this.container = container;
        populateAirportsPanel();
        refreshTable();
    }

    public void refreshTable() {
        tablePanel.removeAll();
        int currentY = 0;
        int index = 0;

        for (Aerodrom a : container.getAerodroms()) {
            Color rowColor = (index % 2 == 0) ? new Color(157, 220, 245) : new Color(206, 237, 249);

            Panel row = createRow(new String[]{a.getName(), a.getCode(), String.valueOf(a.getX()), String.valueOf(a.getY()), "Yes"}, rowColor);

            row.setBounds(0, currentY, 735, rowHeight);
            tablePanel.add(row);
            currentY += rowHeight;
            index++;
        }

        tablePanel.setPreferredSize(new Dimension(735, Math.max(currentY, 400)));
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private Panel createRow(String[] texts, Color bgColor) {
        Panel row = new Panel(new GridLayout(1, texts.length));
        row.setBackground(bgColor);

        for (String text : texts) {
            Label label = new Label(text, Label.CENTER);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            row.add(label);
        }
        return row;
    }
}