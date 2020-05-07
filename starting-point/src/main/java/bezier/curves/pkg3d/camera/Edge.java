package bezier.curves.pkg3d.camera;

public class Edge {
    
 public Point4 point1;
 public Point4 point2;
 
 public Edge (Point4 point1, Point4 point2){
    this.point1=point1;
    this.point2=point2;
 }
 
 public String toString(){
    return "Punto 1: "+point1.x+", "+point1.y+"\nPunto 2: "+point2.x+", "+point2.y;
 }
 
}
