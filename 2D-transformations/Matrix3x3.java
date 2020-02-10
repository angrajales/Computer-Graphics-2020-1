/**
 * @author Anderson, Stiven
 */
public class Matrix3x3 {
    
    public double [][] matrix;
    
    public Matrix3x3 () {
        this.matrix = new double[3][3];
    }
    
    public static Point3 times(Matrix3x3 m, Point3 p) {
        Point3 result = new Point3(0, 0, 0);
        result.x = (m.matrix[0][0] * p.x) + (m.matrix[0][1] * p.y) + (m.matrix[0][2] * p.w);
        result.y = (m.matrix[1][0] * p.x) + (m.matrix[1][1] * p.y) + (m.matrix[1][2] * p.w);
        result.w = (m.matrix[2][0] * p.x) + (m.matrix[2][1] * p.y) + (m.matrix[2][2] * p.w);
        return result;
    }
    
    public static Matrix3x3 times(Matrix3x3 m1, Matrix3x3 m2) {
        Matrix3x3 solution = new Matrix3x3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                double r1 = m1.matrix[i][0] * m2.matrix[0][j];
                double r2 = m1.matrix[i][1] * m2.matrix[1][j];
                double r3 = m1.matrix[i][2] * m2.matrix[2][j];
                double value = r1 + r2 + r3;
                solution.matrix[i][j] = value;
            }
        }
        return solution;
    }
 
}
