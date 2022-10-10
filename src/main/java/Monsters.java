public class Monsters extends Element {

    protected int hitPoints;
    public Monsters(int x, int y, int hitPoints) {
        super(x, y);
        this.hitPoints= hitPoints;

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

    public int getHitPoints (){
        return hitPoints;
    }

    public void setHitPoints (int hitPoints){
        this.hitPoints = hitPoints;

    }

}







