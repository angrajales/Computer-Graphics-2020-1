package transformations3d;
/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 08/03/2020
 * @version 1.0
 */

public class Point4 {
    
    double x, y, w, z;
    
    public Point4(double x, double y, double w,  double z) {
    	this.x = x;
    	this.y = y;
    	this.w = w;
    	this.z = z;
    }
    public Point4 normalize() {
    	double x = this.x / z;
    	double y = this.y / z;
    	double w = this.w / z;
    	double z = 1;
    	return new Point4(x, y, w, z);
    }
    @Override
    public String toString() {
    	String format = "(%.0f, %.0f, %.0f, %.0f)";
    	return String.format(format, this.x, this.y, this.w, this.z);
    }
    
}