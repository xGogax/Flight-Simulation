package gui.L2Panels;

import body.let.LetContainer;

import java.awt.*;

public class AddFlightPanel extends Panel {
    TextField start = new TextField(3);
    TextField end = new TextField(3);
    TextField takeOff = new TextField(5);
    TextField duration = new TextField(5);
    Button addFlight = new Button("Add Flight");
    LetContainer letovi;
    FlightsPanel flightsPanel;

    Label startLabel = new Label("Start Airport");
    Label endLabel = new Label("End Airport");
    Label takeOffLabel = new Label("Take Off");
    Label durationLabel = new Label("Duration");

    public void populateAddFlightPanel() {
        this.setLayout(new BorderLayout(10, 10));

        //START END
        Panel topPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //START
        Panel startPanel = new Panel(new BorderLayout());
        startLabel.setForeground(new Color(49, 95, 166));
        startLabel.setFont(new Font("Arial", Font.BOLD, 20));
        startLabel.setAlignment(Label.LEFT);
        start.setFont(new Font("Arial", Font.BOLD, 20));
        startPanel.add(startLabel, BorderLayout.NORTH);
        startPanel.add(start, BorderLayout.CENTER);
        //END
        Panel endPanel = new Panel(new BorderLayout());
        endLabel.setForeground(new Color(49, 95, 166));
        endLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endLabel.setAlignment(Label.LEFT);
        end.setFont(new Font("Arial", Font.BOLD, 20));
        endPanel.add(endLabel, BorderLayout.NORTH);
        endPanel.add(end, BorderLayout.CENTER);

        topPanel.add(startPanel);
        topPanel.add(endPanel);

        //TAKEOFF DURATION
        Panel midPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //TAKEOFF
        Panel takeOffPanel = new Panel(new BorderLayout());
        takeOffLabel.setForeground(new Color(49, 95, 166));
        takeOffLabel.setFont(new Font("Arial", Font.BOLD, 20));
        takeOffLabel.setAlignment(Label.LEFT);
        takeOff.setFont(new Font("Arial", Font.BOLD, 20));
        takeOffPanel.add(takeOffLabel, BorderLayout.NORTH);
        takeOffPanel.add(takeOff, BorderLayout.CENTER);
        //DURATION
        Panel durPanel = new Panel(new BorderLayout());
        durationLabel.setForeground(new Color(49, 95, 166));
        durationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        durationLabel.setAlignment(Label.LEFT);
        duration.setFont(new Font("Arial", Font.BOLD, 20));
        durPanel.add(durationLabel, BorderLayout.NORTH);
        durPanel.add(duration, BorderLayout.CENTER);

        midPanel.add(takeOffPanel);
        midPanel.add(durPanel);

        //BUTTON
        Panel botPanel = new Panel(new BorderLayout());
        addFlight.setFont(new Font("Arial", Font.BOLD, 20));
        addFlight.setBackground(new Color(105, 161, 236));
        botPanel.add(addFlight, BorderLayout.NORTH);

        this.add(botPanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);
    }

    public AddFlightPanel() {
        populateAddFlightPanel();
    }
}
