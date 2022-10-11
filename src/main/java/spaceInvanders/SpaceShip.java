package spaceInvanders;

public class SpaceShip extends Element{
private boolean isInvincible = false;
private int shootFaster = 3;

    SpaceShip(int x, int Height) {
        super(x, Height);
    }

    public void setIsInvincible(boolean isInvencible) {
        this.isInvincible = isInvencible;
    }
    public boolean getIsInvincible() {
        return isInvincible;
    }

    public void setShootFaster (int shootFaster){
        this.shootFaster=shootFaster;
    }
    public int getShootFaster () {
        return shootFaster;
    }

}

