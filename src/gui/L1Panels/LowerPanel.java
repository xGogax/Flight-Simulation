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
    private Button exportToFile;
    private TextArea consoleArea;
    private LetContainer letContainer;
    private FlightsPanel flightsPanel;
    private SimulationPanel simulator;
    private ButtonSimulatePanel buttonSimulatePanel;

    public LowerPanel() {
        AppContext ctx = AppContext.getInstance();

        // --- Preuzimanje ili kreiranje ButtonSimulatePanel ---
        this.buttonSimulatePanel = ctx.getButtonSimulatePanel();
        if (this.buttonSimulatePanel == null) {
            this.buttonSimulatePanel = new ButtonSimulatePanel();
            ctx.setButtonSimulatePanel(this.buttonSimulatePanel);
        }

        this.simulator = ctx.getSimulator();
        this.flightsPanel = ctx.getFlightsPanel();
        this.aerodrom = ctx.getAerodromContainer();
        this.ap = ctx.getAirportsPanel();
        this.letContainer = ctx.getLetContainer();

        // --- CONSOLE ---
        consoleArea = new TextArea();
        consoleArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        consoleArea.setEditable(false);
        consoleArea.setBackground(new Color(70, 108, 159));
        consoleArea.setForeground(Color.WHITE);
        consoleArea.setColumns(80);
        consoleArea.setRows(18);
        ctx.setConsole(consoleArea);

        // --- PANNELS and BUTTONS ---
        abp = new AddAirportPanel();
        afp = new AddFlightPanel();

        // --- FILE BUTTONS ---
        addFromFile = new Button("Import File");
        exportToFile = new Button("Export File");

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color buttonColor = new Color(105, 161, 236);

        addFromFile.setFont(buttonFont);
        exportToFile.setFont(buttonFont);

        addFromFile.setBackground(buttonColor);
        exportToFile.setBackground(buttonColor);

        Dimension buttonSize = new Dimension(300, 40);
        addFromFile.setPreferredSize(buttonSize);
        exportToFile.setPreferredSize(buttonSize);

        populateLowerPanel();
    }

    private void populateLowerPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(206, 237, 249));

        // --- LEVA STRANA ---
        Panel leftColumn = new Panel();
        leftColumn.setLayout(new GridBagLayout());
        leftColumn.setBackground(new Color(206, 237, 249));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // AddAirportPanel
        gbc.gridy = 0;
        leftColumn.add(abp, gbc);

        // AddFlightPanel
        gbc.gridy = 1;
        leftColumn.add(afp, gbc);

        // ADD FROM FILE and EXPORT FILE
        Panel fileButtonsPanel = new Panel(new GridLayout(1, 2, 10, 0)); // 1 red, 2 kolone
        fileButtonsPanel.setBackground(new Color(206, 237, 249));
        fileButtonsPanel.add(addFromFile);
        fileButtonsPanel.add(exportToFile);

        gbc.gridy = 2;
        leftColumn.add(fileButtonsPanel, gbc);

        // ButtonSimulatePanel
        gbc.gridy = 3;
        leftColumn.add(buttonSimulatePanel, gbc);

        this.add(leftColumn, BorderLayout.WEST);

        // --- DESNA STRANA (Console) ---
        Label consoleLabel = new Label("Console Log");
        consoleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        consoleLabel.setForeground(new Color(49, 95, 166));

        Panel consolePanel = new Panel(new BorderLayout());
        consolePanel.add(consoleLabel, BorderLayout.NORTH);
        consolePanel.add(consoleArea, BorderLayout.CENTER);

        this.add(consolePanel, BorderLayout.CENTER);

        // --- ActionListener za "Add From File" ---
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

        // --- ActionListener za "Export File" ---
        exportToFile.addActionListener((ae) -> {
            try {
                FileDialog fd = new FileDialog((Frame) this.getParent(), "Save File", FileDialog.SAVE);
                fd.setVisible(true);

                String directory = fd.getDirectory();
                String file = fd.getFile();

                if (file != null) {
                    String fullPath = (directory + file).trim();
                    FileLoader loader = new FileLoader(aerodrom, letContainer, consoleArea);
                    loader.saveFile(fullPath);

                    consoleArea.append("INFO: File exported: " + fullPath + "\n");
                } else {
                    consoleArea.append("INFO: Export cancelled.\n");
                }

            } catch (Exception e) {
                consoleArea.append("ERROR: " + e.getMessage() + "\n");
            }
        });
    }

    public TextArea getConsoleArea() {
        return consoleArea;
    }
}