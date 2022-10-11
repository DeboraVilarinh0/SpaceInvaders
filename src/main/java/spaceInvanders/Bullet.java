package spaceInvanders;

public class Bullet extends Element{
    Bullet(int x, int y) {
        super(x, y);
    }

    public Position bulletMovementUP() {
        return new Position(position.getX(), position.getY() - 1);




    }
}
