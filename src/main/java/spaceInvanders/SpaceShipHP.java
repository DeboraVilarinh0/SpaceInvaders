package spaceInvanders;

public class SpaceShipHP extends Element {

    protected int HP=1;

    SpaceShipHP(int x, int y) {
        super(x, y);
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }
}


