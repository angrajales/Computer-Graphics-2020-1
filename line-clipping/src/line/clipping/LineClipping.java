/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package main;

/**
 *
 * @author Anderson, Stiven
 */
package line.clipping;
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LineClipping  extends JPanel{

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 400;
    private static final int MAX_POINTS = 100;
    private static final Point p1 = new Point(50, 50);
    private static final Point p2 = new Point(150, 100);
    private static final String FILE_PATH = "./points.txt";
    private static final boolean ON_CLASS = true;
    private static final String C_OUT_FILE_NAME = "classwork.png";
    private static final String H_OUT_FILE_NAME = "homework.png";
    private static final String OUT_FILE_FORMAT = "png";
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
        if(!ON_CLASS) return generateRandomNubers();
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            
        }
        return points;
    }
    public int[][] generateRandomNubers(){
        int[][] points = new int[MAX_POINTS][4];
        for(int i = 0; i < MAX_POINTS; ++i){
            int x1 = getRandomX(), y1 = getRandomY();
            int x2 = getRandomX(), y2 = getRandomY();
            //System.out.printf("(%d, %d) -> (%d, %d)\n", x1, y1, x2, y2);
            points[i][0] = x1;
            points[i][1] = y1;
            points[i][2] = x2;
            points[i][3] = y2;
        }
        return points;
    }
    public int getRandomX(){
        double randomDouble = Math.random();
        randomDouble = (randomDouble * (MAX_WIDTH / 2)) % (MAX_WIDTH / 2);
        int randomInt = (int) randomDouble;
        double sign = Math.random();
        if(sign > 0.5)
            return randomInt;
        else
            return -randomInt;
    }
    public int getRandomY(){
        double randomDouble = Math.random();
        randomDouble = (randomDouble * (MAX_HEIGHT / 2)) % (MAX_HEIGHT / 2);
        int randomInt = (int) randomDouble;
        double sign = Math.random();
        if(sign > 0.5)
            return randomInt;
        else
            return -randomInt;
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
                continue;
            }
            int x, y;
            System.out.printf("(%d, %d), (%d, %d)\n", x1, y1, x2, y2);
            int prevx1 = x1, prevx2 = x2, prevy1 = y1, prevy2 = y2;
            boolean rej = false;
            do {
                int c;
                if(code1 != 0) c = code1;
                else c = code2;
                if((c & 1) != 0) {
                    y = y1 + (xmin - x1) * (y2 - y1) / (x2 - x1);
                    x = xmin;
                }else if((c & 2) != 0) {
                    y = y1 + (xmax - x1) * (y2 - y1) / (x2 - x1);
                    x = xmax;
                }else if((c & 4) != 0) {
                    x = x1 + (ymin - y1) * (x2 - x1) / (y2 - y1);
                    y = ymin;
                }else{
                    x = x1 + (ymax - y1) * (x2 - x1) / (y2 - y1);
                    y = ymax;
                }
                if(c == code1) {
                    x1 = x; 
                    y1 = y;
                    code1 = code(xmin, xmax, ymin, ymax, x1, y1);
                }else {
                    x2 = x;
                    y2 = y;
                    code2 = code(xmin, xmax, ymin, ymax, x2, y2);
                }
                System.out.printf("(x, y) = (%d, %d)\n", x, y);
                if((code1 & code2) != 0){
                    rej = true;
                    break;
                }
            }while((code1 | code2) != 0);
            g2d.setColor(Color.RED);
            g2d.drawLine(w /2 + prevx1, h /2 - prevy1, w /2 + x1, h/2-y1);
            if(!rej) g2d.setColor(Color.GREEN);
            g2d.drawLine( w / 2 + x1, h / 2 - y1, w / 2 + x2, h / 2 - y2);
            g2d.setColor(Color.RED);
            g2d.drawLine( w / 2 + prevx2, h / 2 - prevy2, w / 2 + x2, h / 2 - y2);
        }
    }
    
    public int code(int xmin, int xmax, int ymin, int ymax, int x, int y){
        int code = 0;
        if(x < xmin) code |= 1;
        else if(x > xmax) code |= 2;
        if(y < ymin) code |= 4;
        else if(y > ymax) code |= 8;
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
        printImage(ON_CLASS, frame);


    }

    public static void printImage(boolean classwork, JFrame currentFrame) {
        String fileName = H_OUT_FILE_NAME;
        if (classwork) fileName = C_OUT_FILE_NAME;
        if (!new File(fileName).exists()) {
            BufferedImage img = new BufferedImage(currentFrame.getWidth(), currentFrame.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            currentFrame.printAll(g2d);
            g2d.dispose();
            try {
                ImageIO.write(img, OUT_FILE_FORMAT, new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
