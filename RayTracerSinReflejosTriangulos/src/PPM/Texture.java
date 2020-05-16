/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PPM;

import Scene.Colour;

/**
 * This classs holds a PPM image to be used as a texture for the ray tracer
 * @author htrefftz
 */
public class Texture {
    private Colour[][] image;
    private int numRows;
    private int numColumns;
    private int maxValue;

    public Texture(String fileName) {
        PPMReader reader = new PPMReader(fileName);
        numRows = reader.getNumFilas();
        numColumns = reader.getNumColumnas();
        maxValue = reader.getMaxValor();
        image = reader.getImagen();
    }

    public Texture(int numFilas, int numColumnas, int maxValor) {
        this.numRows = numFilas;
        this.numColumns = numColumnas;
        this.maxValue = maxValor;
        image = new Colour[numFilas][numColumnas];
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

    public Colour getPixel(int fila, int col) {
        return image[fila][col];
    }
    
    public void setPixel(int fila, int col, Colour color) {
        image[fila][col] = color.clonar();
    }
    
    public Colour getPixel(double s, double t) {
        t = 1 - t;
        int col = (int)(s * numColumns);
        int row = (int)(t * numRows);
        if (col >= numColumns) col = numColumns - 1;
        if (col < 0) col = 0;
        if (row >= numRows) row = numRows - 1;
        if (row < 0) row = 0;
        return image[row][col];
    }

    public Texture volverBlancoYNegro() {
        Texture nueva = clonar();
        for (int fila = 0; fila < numRows; fila++) {
            for (int col = 0; col < numColumns; col++) {
                Colour color = nueva.getPixel(fila, col);
                double promedio = (color.getR() + color.getG() + color.getB())/3d;
                Colour nuevoColor = new Colour(promedio, promedio, promedio);
                nueva.setPixel(fila, col, nuevoColor);
            }
        }
        return nueva;
    }
    
    public Texture bestia() {
        Texture nueva = clonar();
        Colour nuevoColor;
        for (int fila = 0; fila < 100; fila++) {
            for (int col = 0; col < numColumns; col++) {
                // Estamos en las primeras 100 filas
                nuevoColor = new Colour(1, 1, 0);
                nueva.setPixel(fila, col, nuevoColor);
            }
        }
        return nueva;
    }
    
    
    public Texture clonar() {
        Texture nueva = new Texture(numRows, numColumns, maxValue);
        for (int fila = 0; fila < numRows; fila++) {
            for (int col = 0; col < numColumns; col++) {
                nueva.setPixel(fila, col, image[fila][col].clonar());
            }
        }
        return nueva;
    }
    
    public static void main(String [] args) {
        Texture i = new Texture("sargent.ppm");
        //Imagen i = new Texture("ositoOscuro.ppm");
        Texture cambiada = i.volverBlancoYNegro();
        //Imagen cambiada = i.bestia();
        //Imagen cambiada = i.espejoHorizontal();
        PPMWriter grabador = new PPMWriter(cambiada, "sargentBlancoNegro.ppm");
        //GrabadorPPM grabador = new PPMWriter(cambiada, "ositoClaro.ppm");
        //GrabadorPPM grabador = new PPMWriter(cambiada, "vandalismo.ppm");
        //GrabadorPPM grabador = new PPMWriter(cambiada, "sargentEspejo.ppm");
    }
}
