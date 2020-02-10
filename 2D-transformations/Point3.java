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
public class Point3 {
    double x;
    double y;
    double z;
    public Point3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double magnitude(){
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    public Point3 normalize(){
        double m = magnitude();
        return new Point3(this.x / m, this.y / m, this.z / m);
    }
    public static double dot(Point3 v1, Point3 v2){
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    public static Point3 cross(Point3 v1, Point3 v2){
        return new Point3(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
    }
}
