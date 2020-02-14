package transformations2d;

/**
 * 
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @date 14/02/2020
 * @version 1.0
 */
public class Matrix4x4 {
    
    public double [][] matrix;
    
    public Matrix4x4 () {
        this.matrix = new double[4][4];
    }
    
    public static Point4 times(Matrix4x4 m, Point4 p) {
        Point4 result = new Point4(0, 0, 0, 0);
        result.x = (m.matrix[0][0] * p.x) + (m.matrix[0][1] * p.y) + (m.matrix[0][2] * p.z) + (m.matrix[0][3] * p.w);
        result.y = (m.matrix[1][0] * p.x) + (m.matrix[1][1] * p.y) + (m.matrix[1][2] * p.z) + (m.matrix[1][3] * p.w);
        result.z = (m.matrix[2][0] * p.x) + (m.matrix[2][1] * p.y) + (m.matrix[2][2] * p.z) + (m.matrix[2][3] * p.w);
        result.w = (m.matrix[3][0] * p.x) + (m.matrix[3][1] * p.y) + (m.matrix[3][2] * p.z) + (m.matrix[3][3] * p.w);
        return result;
    }
    
    public static Matrix4x4 times(Matrix4x4 m1, Matrix4x4 m2) {
        Matrix4x4 solution = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double r1 = m1.matrix[i][0] * m2.matrix[0][j];
                double r2 = m1.matrix[i][1] * m2.matrix[1][j];
                double r3 = m1.matrix[i][2] * m2.matrix[2][j];
                double r4 = m1.matrix[i][3] * m2.matrix[3][j];
                double value = r1 + r2 + r3 + r4;
                solution.matrix[i][j] = value;
            }
        }
        return solution;
    }
 
}