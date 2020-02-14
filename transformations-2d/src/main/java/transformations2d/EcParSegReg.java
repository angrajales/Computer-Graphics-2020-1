package transformations2d;

/**
 * 
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 14/02/2020
 * @version 1.0
 */
public class EcParSegReg {
    
    public double a1;
    public double a2;
    public double v1;
    public double v2;

    public EcParSegReg(double x1, double y1, double x2, double y2){
        v1 = x2 - x1;
        v2 = y2 - y1;
        a1 = x1;
        a2 = y1;
    }

    public static Pair solve (EcParSegReg a, EcParSegReg b){
        double arr = (a.v2 * b.a1) - (a.v2 * a.a1) - (b.a2 * a.v1) + (a.a2 * a.v1);
        double den = ((b.v2 * a.v1) - (a.v2 * b.v1));
        double u2 = arr / den;
        double u1 =(b.a1 - a.a1 + (u2 * b.v1)) / a.v1;
        Pair coordinate = new Pair(u1, u2);       
        return coordinate;
    }
   
}