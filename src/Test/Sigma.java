package Test;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Sigma extends Frame {
    private int scale = 4;
    private int canvasSize = 181 * 4;
    private int circleSize = 8;

    private Double startX = null, startY = null;
    private Double endX = null, endY = null;

    private double currX, currY;
    private boolean animating = false;

    public Sigma() {
        setBounds(700, 200, canvasSize, canvasSize);
        setResizable(false);
        setTitle("Sigma");
        setBackground(new Color(82, 176, 221));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(animating) return;

                double x = e.getX() / (double) scale - 90;
                double y = 90 - e.getY() / (double) scale;

                if(startX == null){
                    startX = x;
                    startY = y;
                    currX = x;
                    currY = y;
                    repaint();
                    System.out.println("Start: (" + startX + ", " + startY + ")");
                } else if (endX == null){
                    endX = x;
                    endY = y;
                    System.out.println("End: (" + endX + ", " + endY + ")");
                    animateCircle();
                }
            }
        });

        setVisible(true);
    }

    private void animateCircle(){
        if(startX == null || endX == null) return;

        animating = true;

        new Thread(() -> {
            int steps = 50;//TODO
            double dx = (endX - startX) / steps;
            double dy = (endY - startY) / steps;

            try {
                for(int i = 0; i <= steps; i++){
                    currX = startX + dx * i;
                    currY = startY + dy * i;
                    repaint();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startX = null;
            startY = null;
            endX = null;
            endY = null;
            animating = false;
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        if(startX != null){
            int px = (int) ((currX + 90) * scale);
            int py = (int) ((90 - currY) * scale);
            g.setColor(Color.BLUE);
            g.fillOval(px, py, circleSize, circleSize);
        }
    }

    public static void main(String[] args) {
        new Sigma();
    }
}
