package transformations;

public class Transformation {

    public EdgeDrawer.PairInt translate(Point3 p, int dx, int dy) {
        Point3 newPoint = Matrix3x3.times(Matrix3x3.translationMatrix(dx, dy), p);
        return new EdgeDrawer.PairInt((int) newPoint.x, (int) newPoint.y);
    }

    public EdgeDrawer.PairInt rotate(Point3 p, double theta) {
        double radians = Math.toRadians(theta);
        Matrix3x3 m1 = Matrix3x3.times(Matrix3x3.translationMatrix((int) 0, (int) 0), Matrix3x3.rotationMatrix(radians));
        Matrix3x3 m2 = Matrix3x3.times(m1, Matrix3x3.translationMatrix((int) 0, (int) 0));
        Point3 origin = Matrix3x3.times(m2, p);
        System.out.printf("(%.0f,%.0f)\n", origin.x, origin.y);
        return new EdgeDrawer.PairInt((int) origin.x, (int) origin.y);
    }

    public void scale(int sx, int sy) {
    }
}
