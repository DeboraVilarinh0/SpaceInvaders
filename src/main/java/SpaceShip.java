
public class SpaceShip extends Element {
    private boolean isInvencible = false;

    SpaceShip(int x, int Height) {
        super(x, Height);
    }

    public void setIsInvencible(boolean isInvencible) {
        this.isInvencible = isInvencible;
    }

    public boolean isInvencible() {
        return isInvencible;
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }
}

