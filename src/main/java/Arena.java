import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Arena {

    public int width;
    public int height;
    private final SpaceShip spaceShip;
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<EnemyBullet> enemyBullets = new ArrayList<>();
    private final List<PowerUps> powerUps = new ArrayList<>();
    private boolean moveRight = true;
    private boolean moveLeft = false;
    SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();
    public int level = 1;
    private List<Monsters> monsters = new ArrayList<>();
    int fireRate = 3;
    int powerUpType;


    Arena(int width, int height) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.width = width;
        this.height = height;
        spaceShip = new SpaceShip(width / 2, height - 1);
        CreateMonsters(20, 5);

    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        spaceShip.draw(graphics, "#FFAE42", "&");
        if (bullets.size() != 0) for (int indexBulletsList = 0; indexBulletsList < bullets.size(); indexBulletsList++) {
            bullets.get(indexBulletsList).draw(graphics, "#FFFFFF", "_");
        }
        if (enemyBullets.size() != 0)
            for (EnemyBullet enemyBullet : enemyBullets) enemyBullet.draw(graphics, "#FFFFFF", "'");

        if (!powerUps.isEmpty()) {
            for (int i = 0; i < powerUps.size(); i++) {
                switch (powerUps.get(i).getPowerUpType()) {
                    case 1:
                        powerUps.get(i).draw(graphics, "", "o");
                    case 2:
                        powerUps.get(i).draw(graphics, "#dbd800", "o");
                        //  case 3:
                }
            }
        }
        for (Monsters monster : monsters) {
            if (monster.getClass() == FatGuy.class) {
                if (monster.getHitPoints() == 1) {
                    monster.draw(graphics, "#ff0000", "/");
                } else {
                    monster.draw(graphics, "#b57777", "/");
                }
            }
            if (monster.getClass() == BadGuys.class) {
                monster.draw(graphics, "#e3c205", "/");
            }
        }
    }

    public void processKey(KeyStroke key) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (key.getKeyType() == KeyType.ArrowLeft) moveSpaceShip(spaceShip.moveLeft());

        if (key.getKeyType() == KeyType.ArrowRight) moveSpaceShip(spaceShip.moveRight());

        if (key.getKeyType() == KeyType.ArrowUp) {
            if (bullets.size() == 0) CreateBullets(spaceShip.getPosition());
            else if (bullets.get(bullets.size() - 1).getPosition().getY() < height - fireRate) {
                CreateBullets(spaceShip.getPosition());
            }
        }
    }


    private boolean canSpaceshipMove(Position position) {
        return position.getX() <= width - 2 && position.getX() >= 1;
    }

    private void moveSpaceShip(Position position) {
        if (canSpaceshipMove(position)) spaceShip.setPosition(position);
    }

    public void moveBullets() {
        for (int indexBulletList = 0; indexBulletList < bullets.size(); indexBulletList++) {

            bullets.get(indexBulletList).setPosition(bullets.get(indexBulletList).bulletMovementUP());
        }

        for (int indexEnemyBulletList = 0; indexEnemyBulletList < enemyBullets.size(); indexEnemyBulletList++) {

            enemyBullets.get(indexEnemyBulletList).setPosition(enemyBullets.get(indexEnemyBulletList).bulletMovementDOWN());

        }
    }

    public List<Bullet> CreateBullets(Position position) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        bullets.add(new Bullet(position.getX(), position.getY() - 1));
        audioPlayer.restart();
        return bullets;
    }

    public List<EnemyBullet> CreateEnemyBullets(Position position) {
        enemyBullets.add(new EnemyBullet(position.getX(), position.getY()));
        return enemyBullets;
    }

    public void moveMonsters() {
        int maxX = 0;
        int minX = 30;
        for (Monsters monsters : monsters) {
            if (monsters.getPosition().getX() > maxX) maxX = monsters.getPosition().getX();
            if (monsters.getPosition().getX() < minX) minX = monsters.getPosition().getX();
        }

        if (moveRight) {
            if (maxX < width - 1) {
                for (Monsters monsters : monsters) {
                    Position monsterPosition = monsters.moveRight();
                    monsters.setPosition(monsterPosition);
                }
            }
            if (maxX == width - 1) {
                moveRight = false;
                moveLeft = true;
            }
        }

        if (moveLeft) {
            if (minX > 0) {
                for (Monsters monsters : monsters) {
                    Position monsterPosition = monsters.moveLeft();
                    monsters.setPosition(monsterPosition);
                }
            }
            if (minX == 1) {
                moveLeft = false;
            }
        }

        if (!moveLeft && !moveRight) {
            for (Monsters monsters : monsters) {
                Position monsterPosition = monsters.moveDown();
                monsters.setPosition(monsterPosition);
            }
            moveRight = true;
        }
    }

    public void verifySpaceShipCollision() {

        for (Monsters monsters : monsters) {
            if (spaceShip.getPosition().equals(monsters.getPosition())) {
                System.out.println("You died!!!");
                System.exit(0);
            }
        }

        for (EnemyBullet enemyBullet : enemyBullets) {
            if (spaceShip.getPosition().equals(enemyBullet.getPosition())) {
                System.out.println("You died!!!");
                System.exit(0);
            }
        }
    }

    public void CreateMonsters(int Width, int Height) {

        boolean drawFatGuy = true;
        for (int linha = 0; linha < Height; linha++) {
            for (int coluna = 0; coluna < Width; coluna += 3) {
                if (drawFatGuy) {
                    monsters.add(new FatGuy(coluna + (width - Width) / 2, linha));
                    drawFatGuy = false;
                } else {
                    monsters.add(new BadGuys(coluna + (width - Width) / 2, linha));
                    drawFatGuy = true;
                }
            }
        }
    }

    public void setMonsters(List<Monsters> monsters) {
        this.monsters = monsters;
    }

    public int isMonsterEmpty() {
        if (monsters.isEmpty()) {
            level += 1;
            System.out.print("o nivel é   ");
            System.out.println(level);
            return level;
        }
        return 0;
    }


    public void verifyMonsterCollision() {
        for (int indexBullets = 0; indexBullets < bullets.size(); indexBullets++) {

            for (int indexMonsters = 0; indexMonsters < monsters.size(); indexMonsters++) {
                if (bullets.get(indexBullets).getPosition().equals(monsters.get(indexMonsters).getPosition())) {

                    if (monsters.get(indexMonsters).getHitPoints() >= 1) {

                        monsters.get(indexMonsters).setHitPoints(monsters.get(indexMonsters).getHitPoints() - 1);
                        bullets.remove(indexBullets);

                    } else {
                        bullets.remove(indexBullets);
                        monsters.remove(indexMonsters);
                    }
                    break;
                }
            }
        }
    }


    public void cleanBullet() {
        for (int indexBullets = 0; indexBullets < bullets.size(); indexBullets++) {
            if (bullets.get(indexBullets).getPosition().getY() <= 0) {
                bullets.remove(indexBullets);
            }
        }
    }

    public void shootBullet(long shotNumb) {
        if (monsters.size() != 0) {
            for (int i = 0; i < shotNumb; i++) {

                Random rand = new Random();
                int rand_int1 = rand.nextInt(monsters.size());
                CreateEnemyBullets(monsters.get(rand_int1).getPosition());
            }
        }
    }

    public void verifyCollisionBetweenBullets() {
        for (int indexBullets = 0; indexBullets < bullets.size(); indexBullets++) {

            for (int indexBulletsAliens = 0; indexBulletsAliens < enemyBullets.size(); indexBulletsAliens++) {

                if (bullets.get(indexBullets).getPosition().equals(enemyBullets.get(indexBulletsAliens).getPosition()) ||
                        bullets.get(indexBullets).bulletMovementUP().equals(enemyBullets.get(indexBulletsAliens).getPosition())) {
                    bullets.remove(indexBullets);
                    enemyBullets.remove(indexBulletsAliens);
                    break;
                }
            }
        }
    }

    public void CreatePowerUps() {
        Random rand = new Random();
        int rand_pos = rand.nextInt(width - 2);
        powerUps.add(new PowerUps(rand_pos, height - 1, 2));
        System.out.print("PowerUp Size = ");
        System.out.println(powerUps.size());
    }


    public void verifyPowerUpCollision() {
        for (int i = 0; i < powerUps.size(); i++) {

            if (powerUps.get(i).getPosition() == (spaceShip.getPosition())) {
                powerUpType = powerUps.get(i).getPowerUpType();
                switch (powerUpType) {
                    case 2:
                        powerUps.remove(i);
                        spaceShip.setIsInvencible(true);
                }

                powerUps.remove(i);
                fireRate = 0;
                if (powerUps.get(i).getPosition().equals(spaceShip.getPosition())) {

                    powerUpType = powerUps.get(i).getPowerUpType();
                    powerUps.remove(i);

                    System.out.print("powerUps =  ");
                    System.out.println(powerUps);
                    System.out.print("powerUpType =  ");
                    System.out.println(powerUpType);

                    switch (powerUpType) {
                        case 1:
                            fireRate = 0;
                            break;

                        case 2:
                            spaceShip.setIsInvencible(true);
                            break;

                        case 3:
                    }

                    // powerUps.remove(i);
                    //fireRate = 0;

                }
                // powerUps.remove(i);
                //spaceShip.setIsInvencible(true);

            }
        }
    }


    public boolean getIsInvencible() {
        if (spaceShip.isInvencible()) return true;
        else return false;

    }
}