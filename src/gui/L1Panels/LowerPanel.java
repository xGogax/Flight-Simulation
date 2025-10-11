package gui.L1Panels;

import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import gui.L2Panels.AddAirportPanel;
import gui.L2Panels.AirportsPanel;

import java.awt.*;

public class LowerPanel extends Panel {
    AerodromContainer aerodrom;
    AddAirportPanel abp;
    AirportsPanel ap;

    private void populateLowerPanel(){
        this.setBackground(new Color(206, 237, 249));

        //ADD FLIGHT PANEL
        this.add(abp, BorderLayout.NORTH);
    }

    public LowerPanel(AerodromContainer aerodrom, AirportsPanel aerodromPanel) {
        ap = aerodromPanel;
        this.aerodrom = aerodrom;
        abp = new AddAirportPanel(aerodrom, aerodromPanel);
        populateLowerPanel();
    }
}
