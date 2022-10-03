import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    public int width;
    public int height;
    private SpaceShip spaceShip;
    private final List<BadGuys> badGuys;


    Arena(int width, int height) {
        this.width = width;
        this.height = height;
        spaceShip = new SpaceShip(width / 2, height - 1);
        this.badGuys = CreateBadGuys(10, 4);

    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        spaceShip.draw(graphics, "#FFAE42", "A");
        for (int i = 0; i < badGuys.size(); i++) badGuys.get(i).draw(graphics, "#F02727", "X");
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


    public List<BadGuys> CreateBadGuys(int Width, int Height) {

        List<BadGuys> badGuys2 = new ArrayList<>();

        for (int j = 0; j < Height; j++) {
            for (int i = 0; i < Width; i++) {
                badGuys2.add(new BadGuys(i, j));
            }
        }
        return badGuys2;
    }
}