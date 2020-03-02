package transformations3d;
public class NormingParameters {
    private double scaleX;
    private double scaleY;
    private double translateX;
    private double translateY;
    private double rotationAngle;
    private Point3 fixed;

    public NormingParameters(Point3 fixed, double scaleX, double scaleY, double translateX, double translateY, double rotationAngle) {
        this.fixed = fixed;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translateX = translateX;
        this.translateY = translateY;
        this.rotationAngle = rotationAngle;
    }

    public Point3 getFixed() {
        return fixed;
    }

    public void setFixed(Point3 fixed) {
        this.fixed = fixed;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public static class Builder {
        private NormingParameters normingParameters;

        public Builder() {
            normingParameters = new NormingParameters(null, -1, -1, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
        }

        public Builder withScaleX(double scaleX) {
            this.normingParameters.scaleX = scaleX;
            return this;
        }

        public Builder withScaleY(double scaleY) {
            this.normingParameters.scaleY = scaleY;
            return this;
        }

        public Builder withTranslateX(double translateX) {
            this.normingParameters.translateX = translateX;
            return this;
        }

        public Builder withTranslateY(double translateY) {
            this.normingParameters.translateY = translateY;
            return this;
        }
        public Builder withFixedPoint(Point3 fixed){
            this.normingParameters.fixed = fixed;
            return this;
        }

        public Builder withRotationAngle(double rotationAngle) {
            this.normingParameters.rotationAngle = rotationAngle;
            return this;
        }

        public NormingParameters build() {
            if (normingParameters.fixed == null ||
                    normingParameters.scaleX == -1 ||
                    normingParameters.scaleY == -1 ||
                    normingParameters.translateX == Double.MIN_VALUE ||
                    normingParameters.translateY == Double.MIN_VALUE ||
                    normingParameters.rotationAngle == Double.MIN_VALUE){
                throw new RuntimeException("Validate your parameters");
            }
            return this.normingParameters;
        }
    }

}
