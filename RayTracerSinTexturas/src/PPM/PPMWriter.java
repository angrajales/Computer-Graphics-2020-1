

package PPM;

//import imagen.Texture;
import java.io.*;

/**
 * Esta clase permite escribir un archivo en formato PPM.
 * (ver: https://en.wikipedia.org/wiki/Netpbm_format)
 * @author htrefftz
 */
public class PPMWriter {
    PrintStream printStream = null;
    Texture imagen = null;

    public PPMWriter(Texture imagen, String fileName) {
        this.imagen = imagen;
        abrirArchivo(fileName);
        escribirArchivo(imagen);
    }

    private void abrirArchivo(String nombreArchivo) {
        try {
            printStream = new PrintStream(new File(nombreArchivo));
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo " + e.getMessage());
        }
    }

    private void escribirArchivo(Texture imagen) {
        System.out.println("Escribiendo la imagen");
        printStream.println("P3");
        printStream.println("# creado por GrabadorPPM");
        printStream.println(imagen.getNumColumnas() + " " + imagen.getNumFilas());
        printStream.println(imagen.getMaxValor());
        for(int fila = 0; fila < imagen.getNumFilas(); fila++) {
            for (int col = 0; col < imagen.getNumColumnas(); col++) {
                int r = (int)(imagen.getPixel(fila, col).getR() * imagen.getMaxValor() + 0.5) ;
                int g = (int)(imagen.getPixel(fila, col).getG() * imagen.getMaxValor() + 0.5) ;
                int b = (int)(imagen.getPixel(fila, col).getB() * imagen.getMaxValor() + 0.5) ;
                printStream.print(r + " ");
                printStream.print(g + " ");
                printStream.print(b + " ");
            }
            printStream.println();
        }
        System.out.println("Terminé de escribir la imagen");
    }

}
