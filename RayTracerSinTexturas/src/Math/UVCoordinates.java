/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

/**
 * Class to store the u, v coordinates of a surface
 * @author helmuthtrefftz
 */
public class UVCoordinates {
    double u;
    double v;

    public UVCoordinates(double u, double v) {
        this.u = u;
        this.v = v;
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    @Override
    public String toString() {
        return "UVCoordinates{" + "u=" + u + ", v=" + v + '}';
    }
    
}
