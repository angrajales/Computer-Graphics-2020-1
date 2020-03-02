/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 14/02/2020
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EdgeDrawer extends JPanel implements KeyListener {

    private static final String FILE_NAME = "edges.txt";
    private static final String FILE_PATH = "";
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 800;
    private Logger logger = Logger.getLogger(EdgeDrawer.class.getName());
    private ArrayList<Pair<Integer>> edges;
    private ArrayList<Pair<Integer>> vertices;
    private Transformation transformation;
    private char currentChar = '\0';

    public EdgeDrawer() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
        readFile();
        Point3 fixed = new Point3(vertices.get(0).x, vertices.get(0).y);
        NormingParameters normingParameters = new NormingParameters.Builder()
                .withTranslateX(10)
                .withTranslateY(15)
                .withScaleX(0.5)
                .withScaleY(0.5)
                .withRotationAngle(30)
                .withFixedPoint(fixed)
                .build();
        transformation = new Transformation(normingParameters);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
    }

    private void readFile() {
        File file = new File(FILE_PATH + FILE_NAME);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                int nPoints = Integer.parseInt(reader.readLine());
                for (int i = 0; i < nPoints; ++i) {
                    String[] points = reader.readLine().split(" ");
                    int x = Integer.parseInt(points[0]);
                    int y = Integer.parseInt(points[1]);
                    vertices.add(new Pair<>(x, y));
                }
                int nEdges = Integer.parseInt(reader.readLine());
                for (int i = 0; i < nEdges; ++i) {
                    String[] edgs = reader.readLine().split(" ");
                    edges.add(new Pair<>(Integer.parseInt(edgs[0]), Integer.parseInt(edgs[1])));
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            throw new RuntimeException("No se pudo encontrar el archivo");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        // size es el tamaño de la ventana.
        Dimension size = getSize();
        // Insets son los bordes y los títulos de la ventana.
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        g2d.setColor(Color.YELLOW);
        g2d.drawLine(w / 2, 0, w / 2, h);
        g2d.drawLine(0, h / 2, w, h / 2);
        char[] allowedChars = {'a', 'A', 'd', 'D', 'w', 'W', 's', 'S', 'i', 'I', 'k', 'K', 'l', 'L', 'o', 'O'};
        if (currentChar == 'A' || currentChar == 'a')
            translate(w, h, g2d, true, true);
        if (currentChar == 'd' || currentChar == 'D')
            translate(w, h, g2d, true, false);
        if (currentChar == 'w' || currentChar == 'W')
            translate(w, h, g2d, false, false);
        if (currentChar == 's' || currentChar == 'S')
            translate(w, h, g2d, false, true);
        if (currentChar == 'i' || currentChar == 'I')
            rotate(w, h, g2d, false);
        if (currentChar == 'k' || currentChar == 'K')
            rotate(w, h, g2d, true);
        if (currentChar == 'o' || currentChar == 'O')
            scale(w, h, g2d, true);
        if (currentChar == 'l' || currentChar == 'L')
            scale(w, h, g2d, false);
        for (int i = 0; i < allowedChars.length; ++i)
            if (currentChar != allowedChars[i])
                paintEdges(g2d, w, h);
    }

    public void translate(int w, int h, Graphics2D g2d, boolean onX, boolean opposite) {
        int transX = w / 2;
        int transY = h / 2;
        Pair<Integer>[] tmpVertices = new Pair[vertices.size()];
        for (Pair<Integer> edge : edges) {
            Pair<Integer> from = vertices.get(edge.x);
            Pair<Integer> to = vertices.get(edge.y);
            Pair<Integer> newFrom = transformation.translate(new Point3(from.x, from.y), onX, opposite);
            Pair<Integer> newTo = transformation.translate(new Point3(to.x, to.y), onX, opposite);
            tmpVertices[edge.x] = newFrom;
            tmpVertices[edge.y] = newTo;
            g2d.drawLine(transX + newFrom.x, transY - newFrom.y, transX + newTo.x, transY - newTo.y);
        }
        for (int i = 0; i < tmpVertices.length; ++i) vertices.set(i, tmpVertices[i]);
    }

    public void rotate(int w, int h, Graphics2D g2d, boolean clockwise) {
        int transX = w / 2;
        int transY = h / 2;
        Pair<Integer>[] tmpVertices = new Pair[vertices.size()];
        for (Pair<Integer> edge : edges) {
            Pair<Integer> from = vertices.get(edge.x);
            Pair<Integer> to = vertices.get(edge.y);
            Pair<Integer> newFrom = transformation.rotate(new Point3(from.x, from.y), clockwise);
            Pair<Integer> newTo = transformation.rotate(new Point3(to.x, to.y), clockwise);
            tmpVertices[edge.x] = newFrom;
            tmpVertices[edge.y] = newTo;
            g2d.drawLine(transX + newFrom.x, transY - newFrom.y, transX + newTo.x, transY - newTo.y);
        }
        for (int i = 0; i < tmpVertices.length; ++i) vertices.set(i, tmpVertices[i]);
    }

    public void scale(int w, int h, Graphics2D g2d, boolean bigger) {
        int transX = w / 2;
        int transY = h / 2;
        Pair<Integer>[] tmpVertices = new Pair[vertices.size()];
        for (Pair<Integer> edge : edges) {
            Pair<Integer> from = vertices.get(edge.x);
            Pair<Integer> to = vertices.get(edge.y);
            Pair<Integer> newFrom = transformation.scale(new Point3(from.x, from.y), bigger);
            Pair<Integer> newTo = transformation.scale(new Point3(to.x, to.y), bigger);
            tmpVertices[edge.x] = newFrom;
            tmpVertices[edge.y] = newTo;
            g2d.drawLine(transX + newFrom.x, transY - newFrom.y, transX + newTo.x, transY - newTo.y);
        }
        for (int i = 0; i < tmpVertices.length; ++i) vertices.set(i, tmpVertices[i]);
    }

    public void paintEdges(Graphics2D g2d, int w, int h) {
        readFile();
        int transX = w / 2;
        int transY = h / 2;
        for (Pair<Integer> edge : edges) {
            Pair<Integer> from = vertices.get(edge.x);
            Pair<Integer> to = vertices.get(edge.y);
            g2d.drawLine(transX + from.x, transY - from.y, transX + to.x, transY - to.y);

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.currentChar = e.getKeyChar();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transformations 2D");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.getContentPane().add(new EdgeDrawer());
        // Asignarle tamaño
        frame.setSize(MAX_WIDTH, MAX_HEIGHT);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
        FileUtils.printImage(false, frame);
    }
}
