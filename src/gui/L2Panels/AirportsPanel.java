package gui.L2Panels;

import java.awt.*;

public class AirportsPanel extends Panel {
    private Label airPanel = new Label("Airports");
    private Panel tablePanel = new Panel();
    private Panel tableContainer = new Panel();

    private int currentRow = 0;
    public void addAirport(String name, String code, String x, String y, String shown) {
        Color rowColor = (currentRow % 2 == 0) ? new Color(157, 220, 245) : new Color(206, 237, 249);
        tablePanel.add(createRow(new String[]{name, code, x, y, shown}, rowColor));
        currentRow++;
        tablePanel.validate();
        tablePanel.repaint();
    }

    private Panel createEmptyRow(Color bgColor) {
        Panel row = new Panel(new GridLayout(1, 5));
        row.setBackground(bgColor);

        for (int i = 0; i < 5; i++) {
            Label lbl = new Label("");
            row.add(lbl);
        }

        return row;
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
        //TEXT
        this.add(airPanel, BorderLayout.NORTH);
        airPanel.setForeground(new Color(49, 95, 166));
        airPanel.setFont(new Font("Ariel", Font.BOLD, 20));
        airPanel.setAlignment(Label.CENTER);

        //TABLE
        tableContainer.setLayout(new BorderLayout());

        //header
        Panel headerPanel = createRow(new String[]{"Name", "Code", "X", "Y", "Shown"}, new Color(51, 160, 209));
        tableContainer.add(headerPanel, BorderLayout.NORTH);

        //tabela
        tablePanel.setLayout(new GridLayout(0,1));
        tablePanel.setBackground(new Color(124, 174, 194));

        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(tablePanel);
        scrollPane.setBackground(new Color(82, 176, 221));
        scrollPane.setSize(181*4 - 25, 181*4 - 25);

        tableContainer.add(scrollPane, BorderLayout.CENTER);

        this.add(tableContainer, BorderLayout.CENTER);
    }

    public AirportsPanel() {
        populateAirportsPanel();
    }
}
