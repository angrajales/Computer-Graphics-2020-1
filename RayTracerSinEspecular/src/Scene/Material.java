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
public class Material {
    // How much ambient light is reflected
    double Ka;
    // How much diffuse light is reflected
    double Kd;
    // How much specular light is reflected
    double Ks;
    // Exponent of the specular light
    int n;
    // Colour of the object
    Colour color;
    // Weight of this object's color
    double Ko;
    // Weight of the refleciton
    double Kr;
    // Weight of the refraction
    double Kt;

    /**
     * Constructor
     * @param Ka How much Ambient Light is reflected
     * @param Kd How much Diffuse Light is reflected
     * @param Ks how much Specular Light is reflected
     * @param n Specular Light exponent
     * @param color Colour of the object
     * @param Ko Weight of the object color
     * @param Kr Weight of the reflected color
     * @param Kt Weight of the refracted color
     */
    public Material(double Ka, double Kd, double Ks, int n, Colour color, double Ko, double Kr, double Kt) {
        this.Ka = Ka;
        this.Kd = Kd;
        this.Ks = Ks;
        this.n = n;
        this.color = color;
        this.Ko = Ko;
        this.Kr = Kr;
        this.Kt = Kt;
    }
    
    
    /**
     * Test material
     */
    public Material() {
        Ka = 0.1;
        Kd = 0.7;
        Ks = 0.2;
        n = 16;
        color = new Colour(0, 1, 1);
        Ko = 0.5;
        Kr = 0.5;
        Kt = 0;
    }
}
