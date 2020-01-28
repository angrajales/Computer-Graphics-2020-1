package co.com.computergraphics.classwork;

import co.com.computergraphics.utils.Brensenham;
import co.com.computergraphics.utils.FileUtils;
import co.com.computergraphics.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Classwork extends JPanel {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 400;
    private static final Point p1 = new Point(50, 50);
    private static final Point p2 = new Point(150, 100);
    private Brensenham brensenham = new Brensenham();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);

        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        Point p1 = new Point(50, 50);
        Point p2 = new Point(150, 100);
        g2d.drawLine(w / 2, 0, w / 2, h - 1);
        g2d.drawLine(0, h / 2, w - 1, h / 2);
        paintLine(g2d, p1, p2, w, h);
    }

    void paintLine(Graphics2D g2d, Point p1, Point p2, int w, int h) {
        List<Point> points = brensenham.getPoints(p1, p2);
        for (Point p : points) {
            int x = w / 2 + p.getX(), y = h / 2 - p.getY();
            g2d.drawLine(x, y, x, y);
        }
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Points");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Classwork());
        // Asignarle tamaño
        frame.setSize(MAX_WIDTH, MAX_HEIGHT);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
        FileUtils.printImage(true, frame);

    }
}
