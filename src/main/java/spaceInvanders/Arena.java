package spaceInvanders;

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
    private List<SpaceShipHP> spaceShipHP = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    final List<EnemyBullet> enemyBullets = new ArrayList<>();
    private final List<PowerUps> powerUps = new ArrayList<>();
    private boolean moveRight = true;
    private boolean moveLeft = false;
    SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();
    public int level = 1;
    private List<Monsters> monsters = new ArrayList<>();
    int powerUpType;
    boolean fireMultipleBullets = false;
    private boolean isInvincible = false;
    private int shootFaster = 6;
    long runTimer=0;



    Arena(int width, int height) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.width = width;
        this.height = height;
        spaceShip = new SpaceShip(width / 2, height - 1);
        CreateMonsters(20, 5);
        createHP();

    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        if (spaceShipHP.get(1).getHP() == 1) {
            spaceShipHP.get(0).draw(graphics, "#ff0000", "v");
            spaceShipHP.get(1).draw(graphics, "#ff0000", "v");
        }else spaceShipHP.get(0).draw(graphics, "#ff0000", "v");

        spaceShip.draw(graphics, "#FFAE42", "&");
        if (bullets.size() != 0) for (int indexBulletsList = 0; indexBulletsList < bullets.size(); indexBulletsList++) {
            bullets.get(indexBulletsList).draw(graphics, "#FFFFFF", "_");
        }
        if (enemyBullets.size() != 0)
            for (EnemyBullet enemyBullet : enemyBullets) enemyBullet.draw(graphics, "#FFFFFF", "u");

        if (!powerUps.isEmpty()) {
            for (int i = 0; i < powerUps.size(); i++) {
                if (powerUps.get(i).getPowerUpType() == 0) powerUps.get(i).draw(graphics, "#bff8ff", "~");
                if (powerUps.get(i).getPowerUpType() == 1) powerUps.get(i).draw(graphics, "#f5dc00", "~");
                if (powerUps.get(i).getPowerUpType() == 2) powerUps.get(i).draw(graphics, "#FDCAFF", "~");
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
            ;
            if (bullets.size() == 0) CreateBullets(spaceShip.getPosition(), fireMultipleBullets);
            else {
                if (bullets.get(bullets.size() - 1).getPosition().getY() < height - (getShootFaster())) {
                    CreateBullets(spaceShip.getPosition(), fireMultipleBullets);
                }
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
        for (Bullet bullet : bullets) {

            bullet.setPosition(bullet.bulletMovementUP());
        }

        for (EnemyBullet enemyBullet : enemyBullets) {

            enemyBullet.setPosition(enemyBullet.bulletMovementDOWN());

        }
    }

    public List<Bullet> CreateBullets(Position position, boolean fireMultipleBullets) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (!fireMultipleBullets) {
            bullets.add(new Bullet(position.getX(), position.getY() - 1));
            audioPlayer.restartBulletAudio();
        } else {
            bullets.add(new Bullet(position.getX(), position.getY() - 1));
            bullets.add(new Bullet(position.getX() - 1, position.getY() - 1));
            bullets.add(new Bullet(position.getX() + 1, position.getY() - 1));

        }
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

    public void verifySpaceShipCollision() throws InterruptedException, UnsupportedAudioFileException, IOException {

        for (Monsters monsters : monsters) {
            if (spaceShip.getPosition().equals(monsters.getPosition())) {
                if (spaceShipHP.get(1).getHP() == 1) {
                    spaceShipHP.get(1).setHP(0);
                } else{
                    audioPlayer.stopBackgroundAudio();
                    audioPlayer.stopLastLevelAudio();
                    audioPlayer.playDeathAudio();
                System.out.println("You died!!!");
                Thread.sleep(2000);
                System.exit(0);}
            }
        }

        for (EnemyBullet enemyBullet : enemyBullets) {
            if (spaceShip.getPosition().equals(enemyBullet.getPosition())) {
                if (spaceShipHP.get(1).getHP() == 1) {
                    spaceShipHP.get(1).setHP(0);
                } else{
                    audioPlayer.stopBackgroundAudio();
                    audioPlayer.stopLastLevelAudio();
                    audioPlayer.playDeathAudio();
                System.out.println("You died!!!");
                Thread.sleep(2000);
                System.exit(0);}
            }
        }
    }

    public void CreateMonsters(int Width, int Height) {

        boolean drawFatGuy = true;
        for (int linha = 0; linha < Height; linha++) {
            for (int coluna = 0; coluna < Width; coluna += 3) {
                if (drawFatGuy) {
                    monsters.add(new FatGuy(coluna + (width - Width) / 2, linha + 1));
                    drawFatGuy = false;
                } else {
                    monsters.add(new BadGuys(coluna + (width - Width) / 2, linha + 1));
                    drawFatGuy = true;
                }
            }
        }
    }

    public void createHP() {
        spaceShipHP.add(0,new SpaceShipHP(width-1, 0));
        spaceShipHP.add(1,new SpaceShipHP(width - 2, 0));
    }

    public void setMonsters(List<Monsters> monsters) {
        this.monsters = monsters;
    }

    public int level() {
        if (monsters.isEmpty() && runTimer==80){
            level += 1;
            System.out.print("o nivel ??   ");
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

    public void CreatePowerUps() {    //power up na casa da esquerda n??o ?? poss??vel apanhar
        Random rand = new Random();
        Random rand1 = new Random();
        int randPos = rand.nextInt(width - 2);
        randPos += 1;
        int randPowerUp = rand1.nextInt(3);
        System.out.println(randPowerUp);
        powerUps.add(new PowerUps(randPos, height - 1, randPowerUp));

    }


    public void verifyPowerUpCollision() {
        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i).getPosition().equals(spaceShip.getPosition())) {

                powerUpType = powerUps.get(i).getPowerUpType();
                powerUps.remove(i);


                switch (powerUpType) {
                    case 0:
                        setShootFaster(0);
                        break;

                    case 1:
                        setIsInvincible(true);
                        break;

                    case 2:
                        setFireMultipleBullets(true);
                        break;

                }
            }
        }
    }

    public void setIsInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public boolean getIsInvincible() {
        return isInvincible;
    }

    public void setShootFaster(int shootFaster) {
        this.shootFaster = shootFaster;
    }

    public int getShootFaster() {
        return shootFaster;
    }

    public void setFireMultipleBullets(boolean fireMultipleBullets) {
        this.fireMultipleBullets = fireMultipleBullets;
    }

    public boolean getFireMultipleBullets() {
        return fireMultipleBullets;
    }
    public long getRunTimer() {
        return runTimer;
    }

    public void setRunTimer(long runTimer) {
        this.runTimer = runTimer;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public boolean monsterIsEmpty(){
        if(monsters.isEmpty())return true;
        else return false;
    }
}
