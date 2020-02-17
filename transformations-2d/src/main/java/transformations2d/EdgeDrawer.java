package transformations;

/**
 * 
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 14/02/2020
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EdgeDrawer extends JPanel {
    
    private static final String FILE_NAME = "edges.txt";
    private static final String FILE_PATH = "";
    private Logger logger = Logger.getLogger(EdgeDrawer.class.getName());
    private ArrayList<PairInt> edges;
    private ArrayList<PairInt> vertices;
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 800;

    private void readFile() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
        File file = new File(FILE_PATH + FILE_NAME);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                int nPoints = Integer.parseInt(reader.readLine());
                for (int i = 0; i < nPoints; ++i) {
                    String[] points = reader.readLine().split(" ");
                    int x = Integer.parseInt(points[0]);
                    int y = Integer.parseInt(points[1]);
                    vertices.add(new PairInt(x, y));
                }
                int nEdges = Integer.parseInt(reader.readLine());
                for (int i = 0; i < nEdges; ++i) {
                    String[] edgs = reader.readLine().split(" ");
                    edges.add(new PairInt(Integer.parseInt(edgs[0]), Integer.parseInt(edgs[1])));
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
        paintEdges(g2d, w, h);
        translate(w, h, 50, 25, g2d);
        rotate(w, h, 25.0, g2d);
    }
    public void translate(int w, int h, int dx, int dy, Graphics2D g2d){
        Transformation transformation = new Transformation();
        g2d.setColor(Color.WHITE);
        int transX = w / 2;
        int transY = h / 2;
        for (PairInt edge : edges) {
            PairInt from = vertices.get(edge.getX());
            PairInt to = vertices.get(edge.getY());
            PairInt newFrom = transformation.translate(new Point3(from.x, from.y), dx, dy);
            PairInt newTo = transformation.translate(new Point3(to.x, to.y), dx, dy);
            g2d.drawLine(transX + newFrom.getX(), transY - newFrom.getY(), transX + newTo.getX(), transY - newTo.getY());
        }
    }
    public void rotate(int w, int h, double theta, Graphics2D g2d){
        Transformation transformation = new Transformation();
        g2d.setColor(Color.GREEN);
        int transX = w / 2;
        int transY = h / 2;
        for (PairInt edge : edges) {
            PairInt from = vertices.get(edge.getX());
            PairInt to = vertices.get(edge.getY());
            PairInt newFrom = transformation.rotate(new Point3(from.x, from.y), theta);
            PairInt newTo = transformation.rotate(new Point3(to.x, to.y), theta);
            g2d.drawLine(transX + newFrom.getX(), transY - newFrom.getY(), transX + newTo.getX(), transY - newTo.getY());
        }
    }

    public void paintEdges(Graphics2D g2d, int w, int h) {
        readFile();
        int transX = w / 2;
        int transY = h / 2;
        for (PairInt edge : edges) {
            PairInt from = vertices.get(edge.getX());
            PairInt to = vertices.get(edge.getY());
            g2d.drawLine(transX + from.getX(), transY - from.getY(), transX + to.getX(), transY - to.getY());

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Points");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new EdgeDrawer());
        // Asignarle tamaño
        frame.setSize(MAX_WIDTH, MAX_HEIGHT);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
        FileUtils.printImage(false, frame);
    }

    public static class PairInt {
        
        public int x;
        public int y;
        
        public PairInt(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
