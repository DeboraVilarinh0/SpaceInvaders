
public class SpaceShip extends Element{
private boolean isInvencible = false;
private int shootFaster = 3;

    SpaceShip(int x, int Height) {
        super(x, Height);
    }

    public void setIsInvencible(boolean isInvencible) {
        this.isInvencible = isInvencible;
    }
    public boolean getIsInvencible () {
        return isInvencible;
    }

    public void setShootFaster (int shootFaster){
        this.shootFaster=shootFaster;
    }
    public int getShootFaster () {
        return shootFaster;
    }



    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }
}

