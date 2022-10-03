import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Arena {

    public int width;
    public int height;
    private final SpaceShip spaceShip;


    Arena(int width, int height) {
        this.width = width;
        this.height = height;
        spaceShip = new SpaceShip(width / 2, height - 1);

    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        SpaceShip.draw(graphics, "#FFAE42", "A");
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowLeft) canSpaceShipMove(spaceShip.moveLeft());

        if (key.getKeyType() == KeyType.ArrowRight) canSpaceShipMove(spaceShip.moveRight());
    }

    private void canSpaceShipMove(Position position) {
        boolean SpaceShipMove = true;
        if (position.getY() > height - 2 || position.getY() < 1 || position.getX() > width - 2 || position.getX() < 1) {
            SpaceShipMove = false;
        }
        spaceShip.setPosition(position);
    }
}
