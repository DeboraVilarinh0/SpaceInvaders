import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {



    Element (int x, int y){
        this.position= new Position(x,y);
    }

    protected Position position;

    public static void draw(TextGraphics graphics, String colorCode, String character){
        graphics.setForegroundColor(TextColor.Factory.fromString(colorCode));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), character);}
}
