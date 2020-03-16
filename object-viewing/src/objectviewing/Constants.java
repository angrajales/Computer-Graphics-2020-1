package objectviewing;
/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 08/03/2020
 * @version 1.0
 */

import static java.lang.Math.*;
public class Constants {
	public static final int MAX_WIDTH = 500;
	public static final int MAX_HEIGHT = 500;
	public static final double ANGLE_MOVE = 10.;
	public static final double MAXPHI = 90.;
	public static final String TITLE = "Transformations 3D";
	public static final String FILE_PATH = "./";
	public static final String FILE_NAME = "edges";
	public static final String FILE_EXTENSION = ".txt";
	private static Matrix4x4 projectionMatrix = new Matrix4x4();
	public static final double CAMERA_DISTANCE = 500;
	public static final Matrix4x4 getProjectionMatrix(double d) {
		projectionMatrix.matrix[0][0] = 1;
		projectionMatrix.matrix[1][1] = 1;
		projectionMatrix.matrix[2][2] = 1;
		projectionMatrix.matrix[3][2] = 1 / d;
		return projectionMatrix;
	}
	private static Matrix4x4 translationMatrix = new Matrix4x4();
	public static final Matrix4x4 getTranslationMatrix(double tx, double ty, double tw) {
		translationMatrix.matrix[0][0] = 1;
		translationMatrix.matrix[1][1] = 1;
		translationMatrix.matrix[2][2] = 1;
		translationMatrix.matrix[3][3] = 1;
		translationMatrix.matrix[0][3] = tx;
		translationMatrix.matrix[1][3] = ty;
		translationMatrix.matrix[2][3] = tw;
		return translationMatrix;
	}
	
}
