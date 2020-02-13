/**
 * @author Anderson, Stiven
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    private static final String C_OUT_FILE_NAME = "classwork.png";
    private static final String H_OUT_FILE_NAME = "homework.png";
    private static final String OUT_FILE_FORMAT = "png";

    public static void printImage(boolean classwork, JFrame currentFrame) {
        String fileName = H_OUT_FILE_NAME;
        if (classwork) fileName = C_OUT_FILE_NAME;
        if (!new File(fileName).exists()) {
            BufferedImage img = new BufferedImage(currentFrame.getWidth(), currentFrame.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            currentFrame.printAll(g2d);
            g2d.dispose();
            try {
                ImageIO.write(img, OUT_FILE_FORMAT, new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
