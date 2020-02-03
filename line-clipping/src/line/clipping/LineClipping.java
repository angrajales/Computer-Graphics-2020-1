/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package line.clipping;

/**
 *
 * @author agrajal7
 */

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LineClipping extends JPanel{

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 400;
    private static final Point p1 = new Point(50, 50);
    private static final Point p2 = new Point(150, 100);
    private static final String FILE_PATH = "./points.txt";
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
        //g2d.drawLine(w / 2, 0, w / 2, h - 1);
        //g2d.drawLine(0, h / 2, w - 1, h / 2);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(w / 2 - 100, h / 2 + 100, w / 2 + 100, h / 2 + 100);
        g2d.drawLine(w / 2 - 100, h / 2 - 100, w / 2 + 100, h / 2 - 100);
        g2d.drawLine(w / 2 - 100, h / 2 - 100, w / 2 - 100, h / 2 + 100);
        g2d.drawLine(w / 2 + 100, h / 2 - 100, w / 2 + 100, h / 2 + 100);
        clipping(g2d, -100, 100, -100, 100, w, h, getPoints());
    }
    int [][] getPoints(){
        int[][] points = new int[2][4];
        File file = new File(FILE_PATH);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            int i = 0;
            while((line = br.readLine()) != null){
                String[] pts = line.split(" ");
                points[i][0] = Integer.parseInt(pts[0]);
                points[i][1] = Integer.parseInt(pts[1]);
                points[i][2] = Integer.parseInt(pts[2]);
                points[i][3] = Integer.parseInt(pts[3]);
                i++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LineClipping.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            
        }
        return points;
    }
    public void clipping(Graphics2D g2d, int xmin, int xmax, int ymin, int ymax, int w, int h, int[][] points){
        for(int i = 0; i < points.length; ++i){
            int x1 = points[i][0], y1 = points[i][1];
            int x2 = points[i][2], y2 = points[i][3];
            int code1 = code(xmin, xmax, ymin, ymax, x1, y1);
            int code2 = code(xmin, xmax, ymin, ymax, x2, y2);
            if( (code1 | code2) == 0) {
                g2d.setColor(Color.GREEN);
                g2d.drawLine( w / 2 + x1, h / 2 - y1, w / 2 + x2, h / 2 - y2);
                continue;
            }
            if((code1 & code2) != 0){
                g2d.setColor(Color.RED);
                g2d.drawLine( w / 2 + x1, h / 2 - y1, w / 2 + x2, h / 2 - y2);
            }
            int m = (y2 - y1) / (x2 - x1);
            int xp1 = x1, xp2 = x2, yp1 = y1, yp2 = y2;
            while((code1 | code2) != 0){
                if((code1 & 1) == 1){
                    int x = yp1 + m * (xmin - xp1);
                }
                if((code1 & 2) == 2){
                    int x = yp1 + m * (xmax - xp1);
                }
            }
        }
    }
    
    public int code(int xmin, int xmax, int ymin, int ymax, int x, int y){
        int code = 0;
        if(x < xmin) code |= 1;
        if(x > xmax) code |= 2;
        if(y < ymin) code |= 4;
        if(y > ymax) code |= 8;
        return code;
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Line Clipping");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new LineClipping());
        // Asignarle tamaño
        frame.setSize(MAX_WIDTH, MAX_HEIGHT);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);

    }
    
}
