package objectviewing;

/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @version 1.0
 * @date 08/03/2020
 */

public class Point4 {

    double x, y, w, z;

    public Point4(double x, double y, double w, double z) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.z = z;
    }

    public Point4 normalize() {
        double m = magnitude();
        return new Point4(this.x / m, this.y / m, this.w / m, 1);
    }
    public Point4 normalizep(){
        return new Point4(this.x / this.z, this.y / this.z, this.w / this.z, 1);
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.w * this.w);
    }

    public static Point4 cross(Point4 p1, Point4 p2) {
        Point3 pp1 = new Point3(p1.x, p1.y, p1.w);
        Point3 pp2 = new Point3(p2.x, p2.y, p2.w);
        Point3 res = Point3.cross(pp1, pp2);
        return new Point4(res.x, res.y, res.w, 1);
    }

    public Point4 negative() {
        return new Point4(-this.x, -this.y, -this.w, 1);
    }

    public static double dot(Point4 p1, Point4 p2) {
        return p1.x * p2.x + p1.y * p2.y + p1.w * p2.w;
    }

    @Override
    public String toString() {
        String format = "(%.0f, %.0f, %.0f, %.0f)";
        return String.format(format, this.x, this.y, this.w, this.z);
    }

}