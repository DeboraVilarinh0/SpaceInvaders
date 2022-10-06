import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {

    public int width;
    public int height;
    private SpaceShip spaceShip;
    private List<Bullet> bullets;
    private final List<BadGuys> badGuys;
    private boolean moveRight = true;
    private boolean moveLeft = false;


    Arena(int width, int height) {
        this.width = width;
        this.height = height;
        spaceShip = new SpaceShip(width / 2, height - 1);
        this.badGuys = CreateBadGuys(20, 5);
    }

    public void draw(TextGraphics graphics) throws IOException {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        spaceShip.draw(graphics, "#FFAE42", "A");
        for (int i = 0; i < badGuys.size(); i++) badGuys.get(i).draw(graphics, "#F02727", "X");
        for (int i = 0; i < bullets.size(); i++) bullets.get(i).draw(graphics, "#FFFF00", "|");
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowLeft) {
            moveSpaceShip(spaceShip.moveLeft());
            System.out.println("ESQUERDA");
        }

        if (key.getKeyType() == KeyType.ArrowRight) moveSpaceShip(spaceShip.moveRight());

        if (key.getKeyType() == KeyType.ArrowUp) {
            CreateBullets();

            }
        }

    private boolean canMove(Position position) {
        return position.getY() <= height - 1 && position.getY() >= 1 && position.getX() <= width - 1 && position.getX() >= 0;
    }

    private void moveSpaceShip(Position position) {
        if (canMove(position)) spaceShip.setPosition(position);
    }

    private void moveBullets(Position position) {
        for (int indexBulletList = 0; indexBulletList < bullets.size(); indexBulletList++) {
            if (canMove(bullets.get(indexBulletList).bulletMovementUP())) {

                bullets.get(indexBulletList).setPosition(bullets.get(indexBulletList).bulletMovementUP());
            }
        }
    }

    public List<Bullet> CreateBullets() {
        bullets.add(new Bullet(spaceShip.getPosition().getX(), spaceShip.getPosition().getY()-1));
        return bullets;
    }

    private boolean canSpaceShipMove(Position position) {
        return position.getY() >= 1 && position.getX() <= width - 2 && position.getX() >= 1;

    }

    public void moveBadGuys() {
        int maxX = 0;
        int minX = 30;
        for (BadGuys badguy : badGuys) {
            if (badguy.getPosition().getX() > maxX) maxX = badguy.getPosition().getX();
            if (badguy.getPosition().getX() < minX) minX = badguy.getPosition().getX();
        }

        if (moveRight) {
            if (maxX < width - 1) {
                for (BadGuys badguy : badGuys) {
                    Position badguyPosition = badguy.moveRight();
                    badguy.setPosition(badguyPosition);
                }
            }
            if (maxX == width - 1) {
                moveRight = false;
                moveLeft = true;
            }
        }

        if (moveLeft) {
            if (minX > 0) {
                for (BadGuys badguy : badGuys) {
                    Position badguyPosition = badguy.moveLeft();
                    badguy.setPosition(badguyPosition);
                }
            }
            if (minX == 1) {
                moveLeft = false;
            }
        }

        if (!moveLeft && !moveRight) {
            for (BadGuys badguy : badGuys) {
                Position badguyPosition = badguy.moveDown();
                badguy.setPosition(badguyPosition);
            }
            moveRight=true;
        }

    }


    public void verifyBadGuysCollision() {
        for (BadGuys badGuy : badGuys) {
            if (spaceShip.getPosition().equals(badGuy.getPosition())) {
                System.out.println("You died!!!");
                System.exit(0);
            }
        }
    }

    public List<BadGuys> CreateBadGuys(int Width, int Height) {

        List<BadGuys> badGuys2 = new ArrayList<>();

        for (int linha = 0; linha < Height; linha++) {
            for (int coluna = 0; coluna < Width; coluna += 3) {
                badGuys2.add(new BadGuys(coluna + (width - Width) / 2, linha));
            }
        }
        return badGuys2;
    }
    public boolean verifyBulletColision() {

        for (int i = 0; i < bullets.size(); i++)
            if (bullets.get(i).getPosition().equals(badGuys.get(i).getPosition())) {
                badGuys.remove(i);
                return true;
            }
        return false;
    }
    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public List<BadGuys> getBadGuys() {
        return badGuys;
    }

}
