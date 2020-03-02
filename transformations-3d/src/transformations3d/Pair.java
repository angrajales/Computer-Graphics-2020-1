package transformations3d;
/**
 * @author Anderson Grajales Alzate
 * @author Stiven Ramírez Arango
 * @version 1.0
 * @date 14/02/2020
 */
public class Pair<Type> {

    public Type x;
    public Type y;

    public Pair(Type x, Type y) {
        this.x = x;
        this.y = y;
    }

    public Type getX() {
        return x;
    }

    public void setX(Type x) {
        this.x = x;
    }

    public Type getY() {
        return y;
    }

    public void setY(Type y) {
        this.y = y;
    }

}
