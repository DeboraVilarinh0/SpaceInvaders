public class PowerUps extends Element {
    int Type;
    PowerUps (int x, int y, int type){
        super(x,y);
        this.Type=type;
    }

    public int getPowerUpType () {
        return Type;
    }




}
