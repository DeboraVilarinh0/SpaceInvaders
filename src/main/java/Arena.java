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
        this.badGuys = CreateBadGuys(40, 5);

    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        spaceShip.draw(graphics, "#FFAE42", "A");
        for (int i = 0; i < badGuys.size(); i++) badGuys.get(i).draw(graphics, "#F02727", "X");
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowLeft) moveSpaceShip(spaceShip.moveLeft());

        if (key.getKeyType() == KeyType.ArrowRight) moveSpaceShip(spaceShip.moveRight());
    }

    private boolean canSpaceShipMove(Position position) {
        return position.getY() <= height - 2 && position.getY() >= 1 && position.getX() <= width - 2 && position.getX() >= 1;
    }

    private void moveSpaceShip (Position position){
        if (canSpaceShipMove(position)) spaceShip.setPosition(position);
    }


    public List<BadGuys> CreateBadGuys(int Width, int Height) {

        List<BadGuys> badGuys2 = new ArrayList<>();

        for (int linha = 0; linha < Height; linha++) {
            for (int coluna = 0; coluna < Width; coluna+=3) {
                badGuys2.add(new BadGuys(coluna+ (width-Width)/2, linha));
            }
        }
        return badGuys2;
    }
}