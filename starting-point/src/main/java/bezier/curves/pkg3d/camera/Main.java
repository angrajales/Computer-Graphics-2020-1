package bezier.curves.pkg3d.camera;

import java.io.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

public class Main extends JPanel implements KeyListener{    
    public ArrayList <Edge> ed= new ArrayList<>();
    public Point4 [][] puntos  ;
    double d;
    double theta;
    double phi;
    public ArrayList <Edge> ed2= new ArrayList<>();
    
    public Main() {
        d=500;
        phi=0;
        theta=0;
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        leer("puntosdecontrol.txt" );
    }
    
  
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        Dimension size = getSize();
        Insets insets = getInsets();
        int w =  size.width - insets.left - insets.right;
        int h =  size.height - insets.top - insets.bottom;

        Matrix4x4 matp = new Matrix4x4();
        matp.x[0][0]=1;
        matp.x[1][1]=1;
        matp.x[2][2]=1;
        matp.x[3][2]= -1/d;

        for(double u =0; u <=1; u+=0.01){
          for(double v =0; v <=1; v+=0.01){
            Point4 point= P(u,v);
            Point4 point1= P(u+0.01,v+0.01);
            ed.add(new Edge(point,point1));
          }
        } 

        camera();

        for (Edge ed1 : ed2) { 
          Point4 a = ed1.point1;
          Point4 b = ed1.point2;
          Point4 a1 = Matrix4x4.times(matp, a);
          Point4 b1 = Matrix4x4.times(matp, b);
          a1.normalize();
          b1.normalize();
          g2d.drawLine((int) (a1.x)+(w/2),(h/2)- (int) (a1.y), (int) (b1.x)+(w/2),(h/2)- (int) (b1.y));
        } 
        
        ed2.clear();
    }
        
    
    public Point4 P(double u, double v){
        int n = puntos.length-1; 
        int m = puntos[0].length-1;
        Point4 res = new Point4(0,0,0,1);
        for(int j =0; j <=m; j++){
            for(int k = 0; k <=n; k++){
                res.x+=(puntos[k][j].x*BEZ(m, j, u)*BEZ(n, k, v));
                res.y+=(puntos[k][j].y*BEZ(m, j, u)*BEZ(n, k, v));
                res.z+=(puntos[k][j].z*BEZ(m, j, u)*BEZ(n, k, v));
            }
        }
        return res;
    }
    
    public double BEZ(double n, double k, double u){
        return ((combination(n,k)*Math.pow(u,k))*Math.pow(1-u, n-k));
}
    
    public double combination(double n, double k){
    return (factorial(n)/(factorial(k)*factorial(n-k)));
    } 
    
    public double factorial(double n){
        if(n==0){ return 1;
        } else{
           return n*factorial(n-1);
        }
    }
    
    public Point4 findCentre(){
    double xMax = Integer.MIN_VALUE;
    double yMax = Integer.MIN_VALUE;
    double zMax = Integer.MIN_VALUE;
    double xMin = Integer.MAX_VALUE;
    double yMin = Integer.MAX_VALUE;
    double zMin = Integer.MAX_VALUE;
    for(int i =0; i<ed.size(); i++){
        Edge e= ed.get(i);
        if(e.point1.x > xMax){
            xMax= e.point1.x;
        }
        if(e.point2.x > xMax){
            xMax = e.point2.x;
        }
        if(e.point1.y > yMax){
            yMax = e.point1.y;
        }
        if(e.point2.y > yMax){
            yMax = e.point2.y;
        }
        if(e.point1.z > zMax){
            zMax = e.point1.z;
        }
        if(e.point2.z > zMax){
            zMax = e.point2.z;
        }
        if(e.point1.x < xMin){
            xMin= e.point1.x;
        }
        if(e.point2.x < xMin){
            xMin = e.point2.x;
        }
        if(e.point1.y < yMin){
            yMin = e.point1.y;
        }
        if(e.point2.y < yMin){
            yMin = e.point2.y;
        }
        if(e.point1.z < zMin){
            zMin = e.point1.z;
        }
        if(e.point2.z < zMin){
            zMin = e.point2.z;
        }
    }
    Point4 centro = new Point4(xMax-((xMax-xMin)/2),yMax-((yMax-yMin)/2), zMax-((zMax-zMin)/2),1);
    return centro;
    }  
    
    
    public void leer(String archivo) {
        Scanner s;
        File f = new File(archivo);
        try {
            s = new Scanner(f);
            String tamaño = s.nextLine();
            int cont1 = 0;
            puntos = new Point4
                    [Integer.parseInt(Character.toString(tamaño.charAt(0)))]
                    [Integer.parseInt(Character.toString(tamaño.charAt(2)))];
            while (s.hasNextLine() && cont1 < 3) {
                for(int i = 0; i < 4; i++){
                String line = s.nextLine();
                        try (Scanner s1 = new Scanner(line)) {
                        s1.useDelimiter("\\s+");
                        puntos[cont1][i] = new Point4(Integer.parseInt(s1.next()), Integer.parseInt(s1.next()), Integer.parseInt(s1.next()),1);
                        s1.close();
                    }
                }

                cont1++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe");
        }
    }

    public void rotarCam(Matrix4x4 m){
         for (Edge e:ed){
          Point4 a = e.point1;
          Point4 b = e.point2;
          Point4 a1= Matrix4x4.times(m, a);
          Point4 b1= Matrix4x4.times(m, b);
          Point4 n1= new Point4(a1.x, a1.y, a1.z, a1.w);
          Point4 n2= new Point4(b1.x, b1.y, b1.z, b1.w);
          ed2.add(new Edge(a1, b1));
        }
    }
       
    public void camera(){
        double R=500;
        double r = R*Math.cos(Math.toRadians(phi));
        double x, y, z;
        Point4 centro= findCentre();
        double cx = centro.x, cy = centro.y, cz =centro.z;

        x= r*Math.sin(Math.toRadians(theta)); 
        y= R*Math.sin(Math.toRadians(phi));
        z= r*Math.cos(Math.toRadians(theta));
        x+=cx;
        y+=cy;
        z+=cz;
        Point4 cam = new Point4(x,y,z,1);
        Point4 lookAt= new Point4(-(cx-cam.x),-(cy-cam.y),-(cz-cam.z),1);

        Point4 n =lookAt;
        n.normalize2();
        Point4 up = new Point4(0,1,0,1);
        Point4 u = Point4.crossProduct(up, n);
        u.normalize2();
        Point4 v = Point4.crossProduct(n, u);
        Matrix4x4 rot = new Matrix4x4();
        rot.x[0][0] = u.x;
        rot.x[0][1] = u.y;
        rot.x[0][2] = u.z;
        rot.x[0][3] = -Point4.dotProduct(u, cam);
        rot.x[1][0] = v.x;
        rot.x[1][1] = v.y;
        rot.x[1][2] = v.z;
        rot.x[1][3] = -Point4.dotProduct(v, cam);
        rot.x[2][0] = n.x;
        rot.x[2][1] = n.y;
        rot.x[2][2] = n.z;
        rot.x[2][3] = -Point4.dotProduct(n, cam);
        rot.x[3][0] = 0;
        rot.x[3][1] = 0;
        rot.x[3][2] = 0;
        rot.x[3][3] = 1;
        rotarCam(rot);
    }
   
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        Point4 m = new Point4(0, 0, 0, 1);
       
         if(tecla == KeyEvent.VK_K){
        d +=50;
        }
        else if(tecla == KeyEvent.VK_J){
        d -=50;
        }
        else if(tecla == KeyEvent.VK_D){
                theta +=10;
            
        }
        else if(tecla == KeyEvent.VK_A){
                theta -=10;
                
        } 
        else if(tecla == KeyEvent.VK_W){
            if(phi<79){
                phi +=10;
            } else if(phi<89){
                phi+=(89-phi);
            }
        }
        else if(tecla == KeyEvent.VK_S){
            if(phi>-79){
                phi -=10;
            } else if(phi>-89){
                phi-=(89+phi);
            }
        }  
        repaint();
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bezier Curves");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Main());
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
