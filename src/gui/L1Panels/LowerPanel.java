package gui.L1Panels;

import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import gui.L2Panels.AddAirportPanel;
import gui.L2Panels.AddFlightPanel;
import gui.L2Panels.AirportsPanel;

import java.awt.*;

public class LowerPanel extends Panel {
    AerodromContainer aerodrom;

    //AERODROMI
    AddAirportPanel abp;
    AirportsPanel ap;

    //LETOVI
    AddFlightPanel afp = new AddFlightPanel();

    //PANELI
    Panel gornjiPanel;
    Panel donjiPanel;

    private void populateLowerPanel(){
        this.setBackground(new Color(206, 237, 249));

        //GORNJI PANEL
        gornjiPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        //AIRPORTS
        gornjiPanel.add(abp);
        //FLIGHTS
        gornjiPanel.add(afp);

        this.add(gornjiPanel, BorderLayout.NORTH);
    }

    public LowerPanel(AerodromContainer aerodrom, AirportsPanel aerodromPanel) {
        ap = aerodromPanel;
        this.aerodrom = aerodrom;
        abp = new AddAirportPanel(aerodrom, aerodromPanel);
        populateLowerPanel();
    }
}
