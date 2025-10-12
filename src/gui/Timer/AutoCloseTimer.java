package gui.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class AutoCloseTimer {
    private int timeout = 60;        // vreme do zatvaranja u sekundama
    private int warningTime = 5;     // vreme kada prikazuje upozorenje
    private int elapsed = 0;

    private Timer timer;
    private Dialog warningDialog;
    private Label warningLabel;

    public AutoCloseTimer(Frame parent) {
        // kreiraj warning dijalog
        warningDialog = new Dialog(parent, "Upozorenje", true);
        warningDialog.setLayout(new BorderLayout());
        warningLabel = new Label("", Label.CENTER);
        warningLabel.setFont(new Font("Arial", Font.BOLD, 16));
        Button continueBtn = new Button("Nastavi");
        continueBtn.addActionListener(e -> {
            warningDialog.setVisible(false);
            resetTimer();
        });
        warningDialog.add(warningLabel, BorderLayout.CENTER);
        warningDialog.add(continueBtn, BorderLayout.SOUTH);
        warningDialog.setSize(300, 120);

        // dodaj listener-e na sve komponente unutar parent
        addListenersRecursively(parent);

        // startuj tajmer
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                elapsed++;
                int remaining = timeout - elapsed;

                if (remaining <= warningTime && remaining > 0) {
                    EventQueue.invokeLater(() -> {
                        warningLabel.setText("Program će se zatvoriti za " + remaining + " sekundi.");
                        if (!warningDialog.isVisible()) {
                            warningDialog.setLocationRelativeTo(parent);
                            warningDialog.setVisible(true);
                        }
                    });
                } else if (remaining <= 0) {
                    System.exit(0);
                }
            }
        }, 1000, 1000);
    }

    private void addListenersRecursively(Component comp) {
        if (comp == null) return;

        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) { resetTimer(); }
            public void mouseDragged(MouseEvent e) { resetTimer(); }
            public void mouseClicked(MouseEvent e) { resetTimer(); }
        };
        KeyAdapter keyAdapter = new KeyAdapter() {
            public void keyPressed(KeyEvent e) { resetTimer(); }
        };

        comp.addMouseListener(mouseAdapter);
        comp.addMouseMotionListener(mouseAdapter);
        comp.addKeyListener(keyAdapter);

        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                addListenersRecursively(child);
            }
        }
    }

    public void resetTimer() {
        elapsed = 0;
        if (warningDialog.isVisible()) {
            warningDialog.setVisible(false);
        }
    }

    public void stopTimer() {
        timer.cancel();
    }
}