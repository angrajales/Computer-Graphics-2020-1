/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene;

/**
 *
 * @author htrefftz
 */
public class Colour {
    double r;
    double g;
    double b;

    /**
     * Constructor
     * @param r red
     * @param g green
     * @param b blue
     */
    public Colour(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
 
    /**
     * Add two colors
     * @param c1 First color
     * @param c2 Second color
     * @return 
     */
    public static Colour add(Colour c1, Colour c2) {
        double rNew = c1.r + c2.r;
        double gNew = c1.g + c2.g;
        double bNew = c1.b + c2.b;
        return new Colour(rNew, gNew, bNew);
    }
    
    /**
     * Multiply two colors component by component
     * @param c1 First color
     * @param c2 Second color
     * @return 
     */
    public static Colour multiply(Colour c1, Colour c2) {
        double rNew = c1.r * c2.r;
        double gNew = c1.g * c2.g;
        double bNew = c1.b * c2.b;
        return new Colour(rNew, gNew, bNew);
    }
    
    /**
     * Multiply a color times a scalar
     * @param c1 Colour to be multiplied
     * @param s Scalar to multiply the color with
     * @return 
     */
    public static Colour multiply(Colour c1, double s) {
        double rNew = c1.r * s;
        double gNew = c1.g * s;
        double bNew = c1.b * s;
        return new Colour(rNew, gNew, bNew);
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }
 
    public Colour clonar() {
        Colour nuevo = new Colour(r, g, b);
        return nuevo;
    }

}
