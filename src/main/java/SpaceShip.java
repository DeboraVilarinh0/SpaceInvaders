public class SpaceShip extends Element{

    SpaceShip(int x, int Height) {
        super(x, Height);
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }



}
