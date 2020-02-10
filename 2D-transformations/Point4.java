/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vectors;

/**
 *
 * @author agrajal7
 */
public class Point4 extends Point3{
    double w;
    public Point4(double x, double y, double w, double z) {
        super(x, y, z);
        this.w = w;
    }
    
}
