import java.awt.geom.AffineTransform;

public class Transformation {
    private Matrix3x3 rotationMatrix;
    private Matrix3x3 rotationMatrixClockwise;
    private Matrix3x3 scalingMatrix;
    private Matrix3x3 scalingMatrixBigger;
    private NormingParameters normingParameters;

    public Transformation(NormingParameters normingParameters) {
        rotationMatrix = new Matrix3x3();
        rotationMatrixClockwise = new Matrix3x3();
        scalingMatrix = new Matrix3x3();
        scalingMatrixBigger = new Matrix3x3();
        this.normingParameters = normingParameters;
        fillRotationMatrix();
        fillScalingMatrix();
    }

    public Pair<Integer> translate(Point3 p, boolean onX, boolean down) {
        Point3 newPoint = Matrix3x3.times(translationMatrix(normingParameters.getTranslateX(), normingParameters.getTranslateY(), false, onX, down), p);
        return new Pair<>((int) Math.round(newPoint.x), (int) Math.round(newPoint.y));
    }

    public Pair<Integer> rotate(Point3 p, boolean clockwise) {
        Point3 origin = Matrix3x3.times(clockwise ? rotationMatrixClockwise : rotationMatrix, p);
        return new Pair<>((int) Math.round(origin.x), (int) Math.round(origin.y));
    }

    public Pair<Integer> scale(Point3 p, boolean bigger) {
        Point3 origin = Matrix3x3.times(bigger ? scalingMatrixBigger : scalingMatrix, p);
        return new Pair<>((int) Math.round(origin.x), (int) Math.round(origin.y));
    }

    private Matrix3x3 translationMatrix(double dx, double dy, boolean complete, boolean onX, boolean down) {
        Matrix3x3 matrix = new Matrix3x3();
        matrix.matrix[0][0] = 1;
        matrix.matrix[0][1] = 0;
        matrix.matrix[0][2] = (onX ? dx : 0);
        matrix.matrix[0][2] *= (down ? -1 : 1);
        if (complete) matrix.matrix[0][2] = dx;
        matrix.matrix[1][0] = 0;
        matrix.matrix[1][1] = 1;
        matrix.matrix[1][2] = (onX ? 0 : dy);
        matrix.matrix[1][2] *= (down ? -1 : 1);
        if (complete) matrix.matrix[1][2] = dy;
        matrix.matrix[2][0] = 0;
        matrix.matrix[2][1] = 0;
        matrix.matrix[2][2] = 1;
        return matrix;
    }

    private Matrix3x3 rotationMatrix(double theta, boolean clockwise) {
        Matrix3x3 matrix = new Matrix3x3();
        matrix.matrix[0][0] = Math.cos(theta);
        matrix.matrix[0][1] = (clockwise ? -1 : 1) * -Math.sin(theta);
        matrix.matrix[0][2] = 0;
        matrix.matrix[1][0] = (clockwise ? -1 : 1) * Math.sin(theta);
        matrix.matrix[1][1] = Math.cos(theta);
        matrix.matrix[1][2] = 0;
        matrix.matrix[2][0] = 0;
        matrix.matrix[2][1] = 0;
        matrix.matrix[2][2] = 1;
        return matrix;
    }

    private Matrix3x3 scalingMatrix(double sx, double sy, boolean bigger) {
        Matrix3x3 matrix = new Matrix3x3();
        matrix.matrix[0][0] = bigger ? 1 + sx : (1 + sx) * (1 - sx);
        matrix.matrix[0][1] = 0;
        matrix.matrix[0][2] = 0;
        matrix.matrix[1][0] = 0;
        matrix.matrix[1][1] = bigger ? 1 + sy : (1 + sy) * (1 - sy);
        matrix.matrix[1][2] = 0;
        matrix.matrix[2][0] = 0;
        matrix.matrix[2][1] = 0;
        matrix.matrix[2][2] = 1;
        return matrix;
    }

    private void fillRotationMatrix() {
        double radians = Math.toRadians(normingParameters.getRotationAngle());
        Matrix3x3 m1 = Matrix3x3.times(translationMatrix(normingParameters.getFixed().x,
                normingParameters.getFixed().y, true, true, true),
                rotationMatrix(radians, true));
        rotationMatrix = Matrix3x3.times(m1,
                translationMatrix(-normingParameters.getFixed().x, -normingParameters.getFixed().y, true, true, true));
        Matrix3x3 m1p = Matrix3x3.times(translationMatrix(normingParameters.getFixed().x,
                normingParameters.getFixed().y, true, true, true),
                rotationMatrix(radians, false));
        rotationMatrixClockwise = Matrix3x3.times(m1p,
                translationMatrix(-normingParameters.getFixed().x, -normingParameters.getFixed().y, true, true, true));

    }

    private void fillScalingMatrix() {
        Matrix3x3 m1 = Matrix3x3.times(
                translationMatrix(normingParameters.getFixed().x, normingParameters.getFixed().y, true, true, true),
                scalingMatrix(normingParameters.getScaleX(), normingParameters.getScaleY(), false));
        scalingMatrix = Matrix3x3.times(m1,
                translationMatrix(-normingParameters.getFixed().x, -normingParameters.getFixed().y, true, true, true));
        Matrix3x3 m1p = Matrix3x3.times(
                translationMatrix(normingParameters.getFixed().x, normingParameters.getFixed().y, true, true, true),
                scalingMatrix(normingParameters.getScaleX(), normingParameters.getScaleY(), true));
        scalingMatrixBigger = Matrix3x3.times(m1p,
                translationMatrix(-normingParameters.getFixed().x, -normingParameters.getFixed().y, true, true, true));
    }
}
