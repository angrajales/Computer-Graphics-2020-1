package transformations2d;

/**
 * 
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 14/02/2020
 * @version 1.0
 */
public class Point3 {
    
    double x;
    double y;
    double w;
    
    public Point3(double x, double y, double w){
        this.x = x;
        this.y = y;
        this.w = w;
    }
    
    public double magnitude(){
        return Math.sqrt(this.x * this.x + this.y * this.y + this.w * this.w);
    }
    
    public Point3 normalize(){
        double m = magnitude();
        return new Point3(this.x / m, this.y / m, this.w / m);
    }
    
    public static double dot(Point3 v1, Point3 v2){
        return v1.x * v2.x + v1.y * v2.y + v1.w * v2.w;
    }
    
    public static Point3 cross(Point3 v1, Point3 v2){
        return new Point3(v1.y * v2.w - v1.w * v2.y, v1.w * v2.x - v1.x * v2.w, v1.x * v2.y - v1.y * v2.x);
    }
    
}