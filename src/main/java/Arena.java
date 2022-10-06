import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Arena {

    public int width;
    public int height;
    private SpaceShip spaceShip;
    private List<Bullet> bullets = new ArrayList<>();
    private final List<BadGuys> badGuys;
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
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
        if (bullets.size() != 0) for (int i = 0; i < bullets.size(); i++) bullets.get(i).draw(graphics, "#FFFF00", "|");
        if (enemyBullets.size() != 0)
            for (int i = 0; i < enemyBullets.size(); i++) enemyBullets.get(i).draw(graphics, "#FFFF00", "|");
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowLeft) {
            moveSpaceShip(spaceShip.moveLeft());
            System.out.println("ESQUERDA");
        }

        if (key.getKeyType() == KeyType.ArrowRight) moveSpaceShip(spaceShip.moveRight());

        if (key.getKeyType() == KeyType.ArrowUp) {
            CreateBullets(spaceShip.getPosition());


        }
    }

    private boolean canMove(Position position) {
        return position.getY() <= height - 1 && position.getY() >= 0 && position.getX() <= width - 1 && position.getX() >= 0;
    }

    private void moveSpaceShip(Position position) {
        if (canMove(position)) spaceShip.setPosition(position);
    }

    public void moveBullets() {
        for (int indexBulletList = 0; indexBulletList < bullets.size(); indexBulletList++) {

            bullets.get(indexBulletList).setPosition(bullets.get(indexBulletList).bulletMovementUP());

        }

        for (int indexEnemyBulletList = 0; indexEnemyBulletList < enemyBullets.size(); indexEnemyBulletList++) {

            enemyBullets.get(indexEnemyBulletList).setPosition(enemyBullets.get(indexEnemyBulletList).bulletMovementDOWN());

        }
    }

    public List<Bullet> CreateBullets(Position position) {
        bullets.add(new Bullet(position.getX(), position.getY() - 1));
        return bullets;
    }

    public List<EnemyBullet> CreateEnemyBullets(Position position) {
        enemyBullets.add(new EnemyBullet(position.getX(), position.getY()));
        return enemyBullets;
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
            moveRight = true;
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

    public void verifyBulletCollision() {
        for (int indexBullets = 0; indexBullets < bullets.size(); indexBullets++) {

            for (int indexBadGuys = 0; indexBadGuys < badGuys.size(); indexBadGuys++) {

                System.out.println("Balas iniciais:");
                System.out.println(bullets.size());

                if (bullets.get(indexBullets).getPosition().equals(badGuys.get(indexBadGuys).getPosition())) {
                    badGuys.remove(indexBadGuys);
                    bullets.remove(indexBullets);
                    break;
                } else if (!canMove(bullets.get(indexBullets).getPosition())) bullets.remove(indexBullets);
                System.out.println(bullets.size());
            }
            System.out.println("Tamanho lista balas: " + bullets.size() + " tamanho lista aliens: " + badGuys.size());
            break;


        }

    }




    public void shootBullet() {
        for (int i = 0; i < 1; i++) {
            Random rand = new Random();
            int rand_int1 = rand.nextInt(badGuys.size());

            CreateEnemyBullets(badGuys.get(rand_int1).getPosition());
        }
    }

}





