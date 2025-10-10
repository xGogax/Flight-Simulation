package gui;

import gui.L1Panels.LowerPanel;
import gui.L1Panels.UpperPanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends Frame {

    UpperPanel UP = new UpperPanel();
    LowerPanel LP = new LowerPanel();

    private void populateWindow(){
        this.add(UP, BorderLayout.NORTH);
        this.add(LP, BorderLayout.SOUTH);
    }

    public Window() {
        setResizable(false);
        setTitle("Flight Simulator");

        populateWindow();
        pack();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
    public static void main(String[] args) {
        new Window();
    }
}
