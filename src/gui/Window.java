package gui;

import body.aerodrom.AerodromContainer;
import body.let.LetContainer;

import java.awt.*;

public class Window extends Frame {

    //<editor-fold desc="Colors">
    Color Blue1 = new Color(206, 237, 249);
    Color Blue2 = new Color(82, 176, 221);
    Color Blue3 = new Color(105, 161, 236);
    Color Blue4 = new Color(157, 220, 245);
    Color Blue5 = new Color(51, 160, 209);
    Color Blue6 = new Color(129, 167, 207);
    //</editor-fold>

    //<editor-fold desc="Panels">
    Panel mainPanel = new Panel(new GridLayout(2, 1));
    Panel upperPanel = new Panel(new GridLayout(1, 3));
    Panel lowerPanel = new Panel(new GridLayout(1, 2));

    Panel simulatorPanel = new Panel(new BorderLayout());
    Panel airportsPanel = new Panel(new BorderLayout());
    Panel flightsPanel = new Panel(new BorderLayout());

    Panel buttonPanel = new Panel(new GridLayout(2, 1));
    Panel consolePanel = new Panel(new BorderLayout());

    Panel mapPanel = new Panel(new GridLayout(181*2, 181*2));
    Panel tablePanel1 = new Panel(new BorderLayout());
    Panel tablePanel2 = new Panel(new BorderLayout());

    Panel upperButtonPanel = new Panel(new GridLayout(1, 2));
    Panel lowerButtonPanel = new Panel(new GridLayout(1, 1));

    Panel manualFlightPanel = new Panel(new GridLayout(3, 1));
    Panel manualAirportPanel = new Panel(new GridLayout(3, 1));

    Panel nameFieldPanel = new Panel(new BorderLayout());
    Panel middleFieldPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    Panel manualAirportAddButtonPanel = new Panel(new BorderLayout());

    Panel codePanel = new Panel(new BorderLayout());
    Panel xPanel = new Panel(new BorderLayout());
    Panel yPanel = new Panel(new BorderLayout());

    Panel leftMargin = new Panel();
    //</editor-fold>

    //<editor-fold desc="Labels">
    Label simulatorl = new Label("Simulator", Label.CENTER);
    Label airportsl = new Label("Airports", Label.CENTER);
    Label flightsl = new Label("Flights", Label.CENTER);

    Label namel = new Label("Name",  Label.LEFT);
    Label codel = new Label("Code",  Label.LEFT);
    Label xl = new Label("X",  Label.LEFT);
    Label yl = new Label("Y",  Label.LEFT);
    //</editor-fold>

    //<editor-fold desc="TextFields">
    TextField nametf = new TextField();
    TextField starttf = new TextField(3);
    TextField durationtf = new TextField(3);
    TextField codetf = new TextField();
    TextField xtf = new TextField();
    TextField ytf = new TextField();
    TextField takeofftf = new TextField(5);
    TextField endtf = new TextField(4);
    //</editor-fold>

    private void skelet(){
        // Wrappers za TextField da se poštuje preferredSize
        Panel nameWrapper = new Panel(new FlowLayout(FlowLayout.LEFT,0,0));
        nameWrapper.add(nametf);
        Panel codeWrapper = new Panel(new FlowLayout(FlowLayout.LEFT,0,0));
        codeWrapper.add(codetf);
        Panel xWrapper = new Panel(new FlowLayout(FlowLayout.LEFT,0,0));
        xWrapper.add(xtf);
        Panel yWrapper = new Panel(new FlowLayout(FlowLayout.LEFT,0,0));
        yWrapper.add(ytf);

        // Name field
        nameFieldPanel.add(namel, BorderLayout.NORTH);
        nameFieldPanel.add(nameWrapper, BorderLayout.CENTER);

        // Middle fields
        codePanel.add(codel, BorderLayout.NORTH);
        codePanel.add(codeWrapper, BorderLayout.CENTER);
        xPanel.add(xl, BorderLayout.NORTH);
        xPanel.add(xWrapper, BorderLayout.CENTER);
        yPanel.add(yl, BorderLayout.NORTH);
        yPanel.add(yWrapper, BorderLayout.CENTER);
        middleFieldPanel.add(codePanel);
        middleFieldPanel.add(xPanel);
        middleFieldPanel.add(yPanel);

        // Manual airport panel
        manualAirportPanel.add(nameFieldPanel);
        manualAirportPanel.add(middleFieldPanel);
        manualAirportPanel.add(manualAirportAddButtonPanel);

        // Upper button panel
        buttonPanel.add(upperButtonPanel);
        buttonPanel.add(lowerButtonPanel);
        upperButtonPanel.add(manualAirportPanel);
        upperButtonPanel.add(manualFlightPanel);

        // Left margin + simulator
        simulatorPanel.add(leftMargin, BorderLayout.WEST);
        simulatorPanel.add(simulatorl, BorderLayout.NORTH);
        simulatorPanel.add(mapPanel, BorderLayout.CENTER);

        // Airports & flights
        flightsPanel.add(flightsl, BorderLayout.NORTH);
        flightsPanel.add(tablePanel2, BorderLayout.CENTER);
        airportsPanel.add(airportsl, BorderLayout.NORTH);
        airportsPanel.add(tablePanel1, BorderLayout.CENTER);

        // Upper & lower main panel
        mainPanel.add(upperPanel);
        mainPanel.add(lowerPanel);
        upperPanel.add(simulatorPanel);
        upperPanel.add(airportsPanel);
        upperPanel.add(flightsPanel);
        lowerPanel.add(buttonPanel);
        lowerPanel.add(consolePanel);

        add(mainPanel);
    }

    public void frontEnd(){
        int mapSize = 181*2; // 362x362
        int offset = 10;

        // Map panel
        mapPanel.setPreferredSize(new Dimension(mapSize, mapSize));

        // Name field - puna širina kvadrata
        nametf.setPreferredSize(new Dimension(mapSize, 35));
        nametf.setFont(new Font("Arial", Font.PLAIN, 24)); // smanjen font

        // Code/X/Y fields
        int smallWidth = mapSize / 3 - offset;
        codetf.setPreferredSize(new Dimension(smallWidth, 35));
        codetf.setFont(new Font("Arial", Font.PLAIN, 24)); // smanjen font
        xtf.setPreferredSize(new Dimension(smallWidth, 35));
        xtf.setFont(new Font("Arial", Font.PLAIN, 24)); // smanjen font
        ytf.setPreferredSize(new Dimension(smallWidth, 35));
        ytf.setFont(new Font("Arial", Font.PLAIN, 24)); // smanjen font

        // Left margin
        leftMargin.setPreferredSize(new Dimension(20,0));

        // Labels
        simulatorl.setForeground(Blue6);
        simulatorl.setFont(new Font("Arial", Font.BOLD, 36));
        airportsl.setForeground(Blue6);
        airportsl.setFont(new Font("Arial", Font.BOLD, 36));
        flightsl.setForeground(Blue6);
        flightsl.setFont(new Font("Arial", Font.BOLD, 36));

        // Panel backgrounds
        mainPanel.setBackground(Blue1);
        mapPanel.setBackground(Blue2);
    }

    private void populateWindow(){
        frontEnd();
        skelet();
    }

    public Window() {
        AerodromContainer aerodromContainer = new AerodromContainer();
        LetContainer letContainer = new LetContainer();

        populateWindow();
        pack();

        setLocationRelativeTo(null); // centriranje prozora
        setResizable(false);
        setTitle("Flight Simulator");

        setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }
}
