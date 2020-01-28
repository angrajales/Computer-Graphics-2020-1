package co.com.computergraphics.homework;

import co.com.computergraphics.utils.Brensenham;
import co.com.computergraphics.utils.FileUtils;
import co.com.computergraphics.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Homework extends JPanel {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 400;
    private static final int DELTA = 10;
    // Use as many colors as you want
    private static final Color[] colors = {Color.GREEN, Color.WHITE};
    private Brensenham brensenham = new Brensenham();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);

        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        paintLines(g2d, w, h);
    }

    private void paintLines(Graphics2D g2d, int w, int h) {
        Brensenham brensenham = new Brensenham();
        int color = 0;
        for (int dx = 0; dx < w; dx += DELTA) {
            g2d.setColor(colors[(color++) % colors.length]);
            doStep(g2d, dx, 0, w, dx, w, h);
            g2d.setColor(colors[(color++) % colors.length]);
            doStep(g2d, w, dx, w - dx, h, w, h);
            g2d.setColor(colors[(color++) % colors.length]);
            doStep(g2d, w - dx, h, 0, h - dx, w, h);
            g2d.setColor(colors[(color++) % colors.length]);
            doStep(g2d, 0, h - dx, dx, 0, w, h);
        }
    }

    private void doStep(Graphics2D g2d, int x0, int y0, int x1, int y1, int w, int h) {
        Point p0 = new Point(x0, y0);
        Point p1 = new Point(x1, y1);
        paintLine(g2d, p0, p1, w, h);
    }

    private void paintLine(Graphics2D g2d, Point p1, Point p2, int w, int h) {
        List<Point> points = brensenham.getPoints(p1, p2);
        for (Point p : points) {
            int x = p.getX(), y = h - p.getY();
            g2d.drawLine(x, y, x, y);
        }
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Points");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Homework());
        // Asignarle tamaño
        frame.setSize(MAX_WIDTH, MAX_HEIGHT);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
        FileUtils.printImage(false, frame);
    }
}
