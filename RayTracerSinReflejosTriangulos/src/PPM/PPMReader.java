/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PPM;

import Scene.Colour;

/**
 *
 * @author htrefftz
 */
import java.io.*;
import java.util.Scanner;

/**
 * The purpose of this class is to read into memory the contents
 * of a PPM file
 * (see: https://en.wikipedia.org/wiki/Netpbm_format)
 * @author htrefftz
 */
public class PPMReader {
    private Colour[][] imege;
    private int numRows;
    private int numColumns;
    private int maxValue;
    private String fileName;
    private boolean DEBUG = true;

    public PPMReader(String nombreArchivo) {
        this.fileName = nombreArchivo;
        leerArchivoPPM(nombreArchivo);
    }

    private void leerArchivoPPM(String nombre) {
        try{
            FileInputStream fstream = new FileInputStream(nombre);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            String strLine;
            // Número mágico
            strLine = br.readLine();
            if(DEBUG) System.out.println(strLine);
            // Comentario
            strLine = br.readLine();
            if(DEBUG) System.out.println(strLine);
            // Si la linea anterior es un comentario,
            while(strLine.charAt(0) == '#') {
                // NumColumnas, NumFilas
                strLine = br.readLine();
                if(DEBUG) System.out.println(strLine);
            }
            Scanner sc = new Scanner(strLine);
            numColumns = sc.nextInt();
            numRows = sc.nextInt();
            imege = new Colour[numRows][numColumns];
            // máxima profundidad
            strLine = br.readLine();
            if(DEBUG) System.out.println(strLine);
            sc = new Scanner(strLine);
            maxValue = sc.nextInt();
            strLine = br.readLine();
            int fila = 0;
            int columna = 0;
            while(strLine != null) {
                sc = new Scanner(strLine);
                while(sc.hasNextInt()) {
                    int r = sc.nextInt();
                    int g = sc.nextInt();
                    int b = sc.nextInt();
                    Colour color = new Colour(r/(double)maxValue,
                            g/(double)maxValue, b/(double)maxValue);
                    imege[fila][columna] = color;
                    columna++;
                    if (columna == numColumns) {
                        columna = 0;
                        fila++;
                    }
                }
                // leer siguiente linea
                strLine = br.readLine();
            }
            in.close();
        } catch (Exception e){
            System.err.println("Error al leer la escena: " + e.getMessage());
        }
        if(DEBUG) System.out.println("Termine de leer la textura");
    }

    public Colour getColor(double s, double t) {
        int fila = (int)(s * (numColumns - 1));
        int columna = (int)(t * (numRows - 1));
        return imege[fila][columna];
    }

    public Colour[][] getImagen() {
        return imege;
    }

    public int getMaxValor() {
        return maxValue;
    }

    public int getNumColumnas() {
        return numColumns;
    }

    public int getNumFilas() {
        return numRows;
    }

    @Override
    public String toString() {
        return "Textura{" + "textura=" + imege + "numFilas=" + numRows + "numColumnas=" + numColumns + "maxValor=" + maxValue + "nombreArchivo=" + fileName + '}';
    }



    public static void main(String [] args) {
        PPMReader lector = new PPMReader("earthmap1k.ppm");
        //Textura imege = new Textura("ensayo.ppm");
        System.out.println(lector);
    }


}
