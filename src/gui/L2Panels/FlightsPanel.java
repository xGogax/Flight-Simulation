package gui.L2Panels;

import body.let.Let;
import body.let.LetContainer;

import java.awt.*;

public class FlightsPanel extends Panel {
    private Label flightsL = new Label("Flights");
    private Panel tablePanel = new Panel(null);
    private Panel tableContainer = new Panel();
    private ScrollPane scrollPane;
    private final int rowHeight = 30;
    private LetContainer letContainer;

    private void populateFlightsPanel() {
        this.setLayout(new BorderLayout());

        //LABELA
        this.add(flightsL, BorderLayout.NORTH);
        flightsL.setForeground(new Color(49, 95, 166));
        flightsL.setFont(new Font("Arial", Font.BOLD, 20));
        flightsL.setAlignment(Label.CENTER);

        //TABELA
        // Container
        tableContainer.setLayout(new BorderLayout());
        Panel headerPanel = createRow(
                new String[]{"Start", "End", "Takeoff", "Duration"},
                new Color(51, 160, 209)
        );
        tableContainer.add(headerPanel, BorderLayout.NORTH);

        // Scroll
        tablePanel.setBackground(new Color(124, 174, 194));
        scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(tablePanel);
        scrollPane.setBackground(new Color(82, 176, 221));
        scrollPane.setPreferredSize(new Dimension(500, 181 * 4 - 25));

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        this.add(tableContainer, BorderLayout.CENTER);
    }

    public FlightsPanel(LetContainer letContainer) {
        this.letContainer = letContainer;
        populateFlightsPanel();
        refreshTable();
    }

    public void refreshTable() {
        tablePanel.removeAll();
        int currentY = 0;
        int index = 0;

        for (Let l : letContainer.getFlights()) {
            Color rowColor = (index % 2 == 0) ? new Color(157, 220, 245) : new Color(206, 237, 249);

            String fullTakeOff = String.format("%02d:%02d", l.getSat(), l.getMinut());

            Panel row = createRow(
                    new String[]{l.getStart().getCode(), l.getEnd().getCode(), fullTakeOff, String.valueOf(l.getTrajanjeMin())},
                    rowColor
            );

            row.setBounds(0, currentY, 500, rowHeight);
            tablePanel.add(row);
            currentY += rowHeight;
            index++;
        }

        tablePanel.setPreferredSize(new Dimension(500, Math.max(currentY, scrollPane.getHeight())));
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private Panel createRow (String[] texts, Color bgColor) {
        Panel row = new Panel(new GridLayout(1, texts.length));
        row.setBackground(bgColor);

        for(String text : texts) {
            Label label = new Label(text, Label.CENTER);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            row.add(label);
        }
        return row;
    }
}