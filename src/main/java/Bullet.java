import java.io.IOException;

public class Bullet extends Element{
    Bullet(int x, int y) {
        super(x, y);
    }
    //private int BulletWidth = 1;
    //private int BulletHeight = 3;


    public Position bulletMovementUP() {
        return new Position(position.getX(), position.getY() - 1);
    }


}
