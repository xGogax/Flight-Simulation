package gui.L1Panels;

import body.aerodrom.AerodromContainer;
import body.let.LetContainer;
import gui.L2Panels.AddAirportPanel;
import gui.L2Panels.AddFlightPanel;
import gui.L2Panels.AirportsPanel;
import gui.L2Panels.FlightsPanel;

import java.awt.*;

public class LowerPanel extends Panel {
    private AerodromContainer aerodrom;
    private AddAirportPanel abp;
    private AirportsPanel ap;
    private AddFlightPanel afp;
    private Button addFromFile;
    private TextArea consoleArea; // konzola
    private LetContainer letContainer;
    private FlightsPanel flightsPanel;

    public LowerPanel(AerodromContainer aerodrom, AirportsPanel aerodromPanel, LetContainer letContainer, FlightsPanel flightsPanel) {
        this.flightsPanel = flightsPanel;
        this.aerodrom = aerodrom;
        this.ap = aerodromPanel;
        this.letContainer = letContainer;

        consoleArea = new TextArea();
        consoleArea.setFont(new Font("Monospaced", Font.BOLD, 20));
        consoleArea.setEditable(false);
        consoleArea.setBackground(new Color(70, 108, 159));
        consoleArea.setForeground(Color.WHITE);
        consoleArea.setColumns(120);
        consoleArea.setRows(17);

        abp = new AddAirportPanel(aerodrom, aerodromPanel, consoleArea);
        afp = new AddFlightPanel(letContainer, aerodrom, consoleArea, flightsPanel);
        addFromFile = new Button("Add From File");

        populateLowerPanel();
    }

    private void populateLowerPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(206, 237, 249));

        //LEVA KOLONA
        Panel leftColumn = new Panel();
        leftColumn.setLayout(new GridLayout(0, 1, 0, 10)); // vertikalni razmak između panela
        leftColumn.setBackground(new Color(206, 237, 249));

        leftColumn.add(abp);
        leftColumn.add(afp);

        // BUTTON
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setBackground(new Color(206, 237, 249));

        Dimension flightSize = afp.getPreferredSize();
        addFromFile.setPreferredSize(new Dimension(500, 40)); // visina 40px
        addFromFile.setFont(new Font("Arial", Font.BOLD, 18));
        addFromFile.setBackground(new Color(105, 161, 236));

        buttonPanel.add(addFromFile);
        leftColumn.add(buttonPanel);

        // WRAPPER
        Panel leftWrapper = new Panel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        leftWrapper.setBackground(new Color(206, 237, 249));
        leftWrapper.add(leftColumn);

        this.add(leftWrapper, BorderLayout.WEST);

        //DESNA KOLONA
        Panel rightWrapper = new Panel(new BorderLayout());
        rightWrapper.setBackground(new Color(206, 237, 249));

        // Labela iznad konzole
        Label consoleLabel = new Label("Console Log");
        consoleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        consoleLabel.setForeground(new Color(49, 95, 166));
        consoleLabel.setAlignment(Label.LEFT);

        Panel consolePanel = new Panel(new BorderLayout());
        consolePanel.setBackground(new Color(206, 237, 249));
        consolePanel.add(consoleLabel, BorderLayout.NORTH);
        consolePanel.add(consoleArea, BorderLayout.CENTER);

        // Dodaj marginu oko konzole
        Panel consoleWrapper = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        consoleWrapper.setBackground(new Color(206, 237, 249));
        consoleWrapper.add(consolePanel);

        this.add(consoleWrapper, BorderLayout.CENTER);
    }

    public TextArea getConsoleArea() {
        return consoleArea;
    }
}