package transformations3d;
/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 08/03/2020
 * @version 1.0
 */

import static java.lang.Math.*;
public class Constants {
	public static final int MAX_WIDTH = 1000;
	public static final int MAX_HEIGHT = 1000;
	public static final String TITLE = "Transformations 3D";
	public static final String FILE_PATH = "./";
	public static final String FILE_NAME = "edges";
	public static final String FILE_EXTENSION = ".txt";
	private static Matrix4x4 projectionMatrix = new Matrix4x4();
	public static final Matrix4x4 getProjectionMatrix(double d) {
		projectionMatrix.matrix[0][0] = 1;
		projectionMatrix.matrix[1][1] = 1;
		projectionMatrix.matrix[2][2] = 1;
		projectionMatrix.matrix[3][2] = 1 / d;
		return projectionMatrix;
	}
	private static Matrix4x4 zRotationMatrix = new Matrix4x4();
	public static final Matrix4x4 getRotationMatrixZ(double thetap) {
		double theta = toRadians(thetap);
		zRotationMatrix.matrix[0][0] = cos(theta);
		zRotationMatrix.matrix[0][1] = -sin(theta);
		zRotationMatrix.matrix[1][0] = sin(theta);
		zRotationMatrix.matrix[1][1] = cos(theta);
		zRotationMatrix.matrix[2][2] = 1;
		zRotationMatrix.matrix[3][3] = 1;
		return zRotationMatrix;
	}
	private static Matrix4x4 zRotationMatrix_ = new Matrix4x4();
	public static final Matrix4x4 getRotationMatrixZ_(double thetap) {
		double theta = toRadians(thetap);
		zRotationMatrix_.matrix[0][0] = cos(theta);
		zRotationMatrix_.matrix[0][1] = sin(theta);
		zRotationMatrix_.matrix[1][0] = -sin(theta);
		zRotationMatrix_.matrix[1][1] = cos(theta);
		zRotationMatrix_.matrix[2][2] = 1;
		zRotationMatrix_.matrix[3][3] = 1;
		return zRotationMatrix_;
	}
	private static Matrix4x4 xRotationMatrix = new Matrix4x4();
	public static final Matrix4x4 getRotationMatrixX(double thetap) {
		double theta = toRadians(thetap);
		xRotationMatrix.matrix[1][1] = cos(theta);
		xRotationMatrix.matrix[1][2] = sin(theta);
		xRotationMatrix.matrix[2][1] = -sin(theta);
		xRotationMatrix.matrix[2][2] = cos(theta);
		xRotationMatrix.matrix[0][0] = 1;
		xRotationMatrix.matrix[3][3] = 1;
		return xRotationMatrix;
	}
	private static Matrix4x4 xRotationMatrix_ = new Matrix4x4();
	public static final Matrix4x4 getRotationMatrixX_(double thetap) {
		double theta = toRadians(thetap);
		xRotationMatrix_.matrix[1][1] = cos(theta);
		xRotationMatrix_.matrix[1][2] = -sin(theta);
		xRotationMatrix_.matrix[2][1] = sin(theta);
		xRotationMatrix_.matrix[2][2] = cos(theta);
		xRotationMatrix_.matrix[0][0] = 1;
		xRotationMatrix_.matrix[3][3] = 1;
		return xRotationMatrix_;
	}
	private static Matrix4x4 yRotationMatrix_ = new Matrix4x4();
	public static final Matrix4x4 getRotationMatrixY_(double thetap) {
		double theta = toRadians(thetap);
		yRotationMatrix_.matrix[1][1] = 1;
		yRotationMatrix_.matrix[0][2] = sin(theta);
		yRotationMatrix_.matrix[2][2] = cos(theta);
		yRotationMatrix_.matrix[3][3] = 1;
		yRotationMatrix_.matrix[0][0] = cos(theta);
		yRotationMatrix_.matrix[2][0] = -sin(theta);
		return yRotationMatrix_;
	}
	private static Matrix4x4 yRotationMatrix = new Matrix4x4();
	public static final Matrix4x4 getRotationMatrixY(double thetap) {
		double theta = toRadians(thetap);
		yRotationMatrix.matrix[1][1] = 1;
		yRotationMatrix.matrix[0][2] = -sin(theta);
		yRotationMatrix.matrix[2][2] = cos(theta);
		yRotationMatrix.matrix[3][3] = 1;
		yRotationMatrix.matrix[0][0] = cos(theta);
		yRotationMatrix.matrix[2][0] = sin(theta);
		return yRotationMatrix;
	}
	private static Matrix4x4 scalingMatrix = new Matrix4x4();
	public static final Matrix4x4 getScalingMatrix(double tx, double ty, double tw, Point4 pivot) {
		scalingMatrix.matrix[0][0] = tx;
		scalingMatrix.matrix[1][1] = ty;
		scalingMatrix.matrix[2][2] = tw;
		scalingMatrix.matrix[3][3] = 1;
		scalingMatrix.matrix[0][3] = (1 - tx) * pivot.x;
		scalingMatrix.matrix[1][3] = (1 - ty) * pivot.y;
		scalingMatrix.matrix[2][3] = (1 - tw) * pivot.w;
		return scalingMatrix;
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
