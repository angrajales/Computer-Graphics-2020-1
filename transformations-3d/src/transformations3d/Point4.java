package transformations3d;
/**
 * 
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 14/02/2020
 * @version 1.0
 */
public class Point4 extends Point3 {
    
    double z;
    
    public Point4(double x, double y, double z,  double w) {
        super(x, y, w);
        this.z = z;
    }
    
}