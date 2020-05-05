/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

/**
 *
 * @author htrefftz
 */
public class Ray {
    // Starting point
    Point p0;
    // Direction
    Vector4 u;
    
    /**
     * Constructor
     * @param p0 Initial Point
     * @param u Direction vector
     */
    public Ray(Point p0, Vector4 u) {
        this.p0 = p0;
        this.u = new Vector4(u);
    }
    
    /**
     * Constructor
     * @param p0 tail
     * @param p1 head
     */
    public Ray(Point p0, Point p1) {
        this.p0 = p0;
        this.u = new Vector4(p0.x, p0.y, p0.z, p1.x, p1.y, p1.z);
    }
    
    /**
     * Evaluate a point in the ray, given parameter t
     * @param t parameter
     * @return 
     */
    public Point evaluate(double t) {
        double x = p0.x + t * u.getX();
        double y = p0.y + t * u.getY();
        double z = p0.z + t * u.getZ();
        return new Point(x, y, z);
    }

    public Vector4 getU() {
        return u;
    }    
    
    @Override
    public String toString() {
        return "Ray{" + "p0=" + p0 + ", u=" + u + '}';
    }

    
}
