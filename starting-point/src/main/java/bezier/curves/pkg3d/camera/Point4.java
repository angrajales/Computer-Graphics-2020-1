package bezier.curves.pkg3d.camera;


public class Point4 {
     double x, y, z,w;
   
   public Point4(double x, double y, double z, double w){
       this.x=x;
       this.y=y;
       this.z=z;
       this.w=w;
   
   }
    public  double magnitude(){
        return Math.sqrt(  Math.pow(x,2) +  Math.pow(y,2) +  Math.pow(z,2) + Math.pow(w,2));
    }
    public void normalize(){
       
       x = x/w;
       y = y/w;
       z = z/w;
       w = 1;       
    }
    
    
    public void normalize2(){
        double m= magnitude();
        x = x/m;
        y= y/m;
        z= z/m;
    }
    
     public static Point4 crossProduct(Point4 v1, Point4 v2){
        double x1, y1,z1; 
        x1 = ((v1.y*v2.z)-(v2.y*v1.z));
        y1 =-((v1.x*v2.z)-(v2.x*v1.z));
        z1 =((v1.x*v2.y)-(v2.x*v1.y));
        Point4 vector = new Point4(x1, y1, z1,1);
        
    return vector;
    
    }
     
     public static double dotProduct(Point4 v1, Point4 v2){
        return ((v1.x*v2.x)+(v1.y*v2.y)+(v1.z*v2.z));
    }
     
}
