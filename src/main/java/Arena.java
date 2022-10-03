import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Arena {

    public int width;
    public int height;
    private SpaceShip spaceShip;


    Arena (int width, int height){
        this.width=width;
        this.height=height;
        spaceShip = new SpaceShip(width/2, height-1);

    }
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        SpaceShip.draw(graphics, "#FFAE42", "A");
    }
}
