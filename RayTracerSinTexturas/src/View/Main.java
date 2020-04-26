/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import Math.Point;
import Math.Sphere;

import Scene.Scene;
import Scene.Colour;
import Scene.AmbientLight;
import Scene.PointLight;
import Scene.Material;

import PPM.Texture;

/**
 *
 * @author htrefftz
 */
public class Main extends JPanel {
    Image image;
    private final boolean DEBUG = false;
    
    static int width;
    static int height;
    
    int l1x = 100;
    int l1y = 100;
    int l1z = 100;
    
    public void setImage(Image image) {
        this.image = image;
    }
    
    /**
     * Create all elements in the scene
     * Could be read from a text file
     */
    public void createScene() {
        AmbientLight al = new AmbientLight(new Colour(.2, .2, .2));
        Scene.setAmbientLight(al);
        
        PointLight pl1 = new PointLight(new Point(l1x, l1y, l1z), new Colour(1, 1, 1));
        //Scene.addPointLight(pl1);
        Scene.addPointLight(pl1);
        
        // Read a texture
        //Texture texture = new Texture("madera.ppm");
        Texture texture1 = new Texture("earth.ppm");
        
        // A yellow reflective sphere
        double Ka = .2;        // ambient
        double Kd = .8;        // difuse
        double Ks = .7;          // specular
        int n = 32;
        Colour color = new Colour(1, 0, 1);     // object's color
        double Ko = 1;          // Weight of this object's color
        double Kr = 0;          // Weight of the reflected color
        double Kt = 0;          // Weight of the refracted color
        Material material1 = new Material(Ka, Kd, Ks, n, color, Ko, Kr, Kt, texture1);
        
        Sphere sp1 = new Sphere(new Point(-20, 0, -100), 20, material1);
        Scene.addIntersectable(sp1);
        
        Texture texture2 = new Texture("madera.ppm");
        Material material2 = new Material(Ka, Kd, Ks, n, color, Ko, Kr, Kt, texture2);
        
        Sphere sp2 = new Sphere(new Point(20, 0, -100), 20, material2);
        Scene.addIntersectable(sp2);

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;

        Dimension size = getSize();

        int w = width;
        int h = height;      
        
        image = new Image(w, h);
        image.generateImage();      // Compute the colors of the scene
        this.setImage(image);

        
        Colour colour;
        for(int i = 0; i < image.height; i++) {
            for(int j = 0; j < image.width; j++) {
                colour = image.image[i][j];
                int red = (int) (colour.getR() * 255);
                int green = (int) (colour.getG() * 255);
                int blue = (int) (colour.getB() * 255);
                // Clamp out of range colors
                if(red > 255) red = 255;
                if(green > 255) green = 255;
                if(blue > 255) blue = 255;
                g2d.setColor(new Color(red, green, blue));
                if(DEBUG)
                    System.out.println(red + " " + green + " " + blue);
                g2d.drawLine(i, j, i, j);
            }
        }        
    }
    
    
    public static void main(String [] args) {
        JFrame frame = new JFrame("Ray Tracer 2019");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        width = 500;
        height = 500;
        
        // Create this JPanel
        Main main = new Main();
        main.createScene();         // Add lights, objects, etc.
        // Texture image = new Texture(width, height);
        // image.generateImage();      // Compute the colors of the scene
        // main.setImage(image);

        // Draw the result
        frame.add(main);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}
