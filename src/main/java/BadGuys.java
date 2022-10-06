import java.util.Random;

public class BadGuys extends Element {
    public BadGuys(int x, int y) {
        super(x, y);

    }
    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());

    }

    public Position moveLeft () {
        return new Position(position.getX() - 1, position.getY());

    }

    public Position moveDown () {
        return new Position(position.getX(), position.getY()+1);
    }
}
