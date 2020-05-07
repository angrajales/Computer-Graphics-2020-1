package bezier.curves.pkg3d.camera;

public class Matrix4x4 {

    public int n = 4;
    double[][] x;

    public Matrix4x4() {
        x = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x[i][j] = 0.0;
            }
        }

    }

    public static Point4 times(Matrix4x4 matriz, Point4 point) {
        double[] a = new double[4];
        double[] pointarr = {point.x, point.y, point.z, point.w};

        for (int i = 0; i < matriz.n; i++) {
            for (int j = 0; j < matriz.n; j++) {
                a[i] += (matriz.x[i][j] * pointarr[j]);
            }
        }

        Point4 point2 = new Point4(a[0], a[1], a[2], a[3]);
        return point2;
        
    }

    public static Matrix4x4 times(Matrix4x4 matriz1, Matrix4x4 matriz2) {
        Matrix4x4 matrizf= new Matrix4x4();
        for(int i =0; i < matriz2.n; i++){
            for(int j=0; j<matriz1.n; j++){
                for(int k =0; k < matriz1.n; k++){
                     matrizf.x[i][j]+=(matriz1.x[j][k]*matriz2.x[k][i]);   
                    }
            }
        
        }
        
        return matrizf;
    }

}