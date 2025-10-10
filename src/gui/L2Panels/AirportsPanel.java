package gui.L2Panels;

import java.awt.*;

public class AirportsPanel extends Panel {
    private Label airPanel = new Label("Airports");
    private Panel tablePanel = new Panel(null);
    private Panel tableContainer = new Panel();
    private ScrollPane scrollPane;

    private int currentY = 0; // y koordinata gde sledeci red ide
    private final int rowHeight = 30; // visina jednog reda

    public void addAirport(String name, String code, String x, String y, String shown) {
        Color rowColor = (currentY / rowHeight % 2 == 0) ? new Color(157, 220, 245)  : new Color(206, 237, 249);

        Panel row = createRow(new String[]{name, code, x, y, shown}, rowColor);
        row.setBounds(0, currentY, 720, rowHeight);

        tablePanel.add(row);
        currentY += rowHeight;

        tablePanel.setPreferredSize(new Dimension(720, Math.max(currentY, 400)));
        tablePanel.repaint();
        tablePanel.validate();
    }

    private Panel createRow(String[] texts, Color bgColor){
        Panel row = new Panel(new GridLayout(1, texts.length));
        row.setBackground(bgColor);

        for(String text : texts){
            Label label = new Label(text, Label.CENTER);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            row.add(label);
        }

        return row;
    }

    private void populateAirportsPanel(){
        this.setLayout(new BorderLayout());

        // title
        this.add(airPanel, BorderLayout.NORTH);
        airPanel.setForeground(new Color(49, 95, 166));
        airPanel.setFont(new Font("Arial", Font.BOLD, 20));
        airPanel.setAlignment(Label.CENTER);

        // table container
        tableContainer.setLayout(new BorderLayout());

        // header
        Panel headerPanel = createRow(new String[]{"Name", "Code", "X", "Y", "Shown"}, new Color(51, 160, 209));
        tableContainer.add(headerPanel, BorderLayout.NORTH);

        // table body
        tablePanel.setBackground(new Color(124, 174, 194));
        tablePanel.setPreferredSize(new Dimension(750, 400)); // fiksa velicina praznine

        //scroll
        scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(tablePanel);
        scrollPane.setBackground(new Color(82, 176, 221));
        scrollPane.setPreferredSize(new Dimension(725, 181 * 4 - 25));
        tablePanel.setPreferredSize(new Dimension(715, 400));


        tableContainer.add(scrollPane, BorderLayout.WEST);
        this.add(tableContainer, BorderLayout.CENTER);
    }

    public AirportsPanel() {
        populateAirportsPanel();
    }
}