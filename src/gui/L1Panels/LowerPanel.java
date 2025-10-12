package gui.L1Panels;

import body.FileLoader;
import body.aerodrom.AerodromContainer;
import body.let.LetContainer;
import gui.L2Panels.*;
import gui.AppContext;

import java.awt.*;

public class LowerPanel extends Panel {
    private AerodromContainer aerodrom;
    private AddAirportPanel abp;
    private AirportsPanel ap;
    private AddFlightPanel afp;
    private Button addFromFile;
    private TextArea consoleArea;
    private LetContainer letContainer;
    private FlightsPanel flightsPanel;
    private SimulationPanel simulator;

    public LowerPanel() {
        AppContext ctx = AppContext.getInstance();
        this.simulator = ctx.getSimulator();
        this.flightsPanel = ctx.getFlightsPanel();
        this.aerodrom = ctx.getAerodromContainer();
        this.ap = ctx.getAirportsPanel();
        this.letContainer = ctx.getLetContainer();

        consoleArea = new TextArea();
        consoleArea.setFont(new Font("Monospaced", Font.BOLD, 20));
        consoleArea.setEditable(false);
        consoleArea.setBackground(new Color(70, 108, 159));
        consoleArea.setForeground(Color.WHITE);
        consoleArea.setColumns(120);
        consoleArea.setRows(18);

        ctx.setConsole(consoleArea);

        abp = new AddAirportPanel();
        afp = new AddFlightPanel();
        addFromFile = new Button("Add From File");

        populateLowerPanel();
    }

    private void populateLowerPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(206, 237, 249));

        //--- LEVA STRANA ---
        Panel leftColumn = new Panel(new GridLayout(0, 1, 0, 10));
        leftColumn.add(abp);
        leftColumn.add(afp);

        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addFromFile.setPreferredSize(new Dimension(500, 40));
        addFromFile.setFont(new Font("Arial", Font.BOLD, 18));
        addFromFile.setBackground(new Color(105, 161, 236));
        buttonPanel.add(addFromFile);
        leftColumn.add(buttonPanel);

        Panel leftWrapper = new Panel(new BorderLayout());
        leftWrapper.add(leftColumn, BorderLayout.NORTH);
        this.add(leftWrapper, BorderLayout.WEST);

        //--- DESNA STRANA ---
        Label consoleLabel = new Label("Console Log");
        consoleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        consoleLabel.setForeground(new Color(49, 95, 166));

        Panel consolePanel = new Panel(new BorderLayout());
        consolePanel.add(consoleLabel, BorderLayout.NORTH);
        consolePanel.add(consoleArea, BorderLayout.CENTER);

        Panel consoleWrapper = new Panel(new BorderLayout());
        consoleWrapper.add(consolePanel, BorderLayout.NORTH);
        this.add(consoleWrapper, BorderLayout.CENTER);

        addFromFile.addActionListener((ae) -> {
            try {
                FileDialog fd = new FileDialog((Frame) this.getParent(), "Open File", FileDialog.LOAD);
                fd.setVisible(true);

                String directory = fd.getDirectory();
                String file = fd.getFile();

                if (file != null) {
                    String fullPath = (directory + file).trim();
                    FileLoader loader = new FileLoader(aerodrom, letContainer, consoleArea);
                    loader.loadFile(fullPath);

                    consoleArea.append("INFO: File loaded: " + fullPath + "\n");
                    ap.refreshTable();
                    flightsPanel.refreshTable();
                    simulator.refresh();
                } else {
                    consoleArea.append("INFO: File not opened.\n");
                }
            } catch (Exception e) {
                consoleArea.append("ERROR: " + e.getMessage() + "\n");
            }
        });
    }
}