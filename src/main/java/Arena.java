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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Arena {

    public int width;
    public int height;
    private SpaceShip spaceShip;
    private List<Bullet> bullets = new ArrayList<>();
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
    private boolean moveRight = true;
    private boolean moveLeft = false;
    SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();
    public int level = 1;
    private List<Monsters> monsters;

    Arena(int width, int height) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.width = width;
        this.height = height;
        spaceShip = new SpaceShip(width / 2, height - 1);
        this.monsters = CreateMonsters(20, 5);
    }

    public void draw(TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        spaceShip.draw(graphics, "#FFAE42", "Ã");
        //for (int i = 0; i < badGuys.size(); i++) badGuys.get(i).draw(graphics, "#F62817", "X");
        if (bullets.size() != 0) for (int i = 0; i < bullets.size(); i++) bullets.get(i).draw(graphics, "#FFFFFF", "|");
        if (enemyBullets.size() != 0)
            for (int i = 0; i < enemyBullets.size(); i++) enemyBullets.get(i).draw(graphics, "#FFFFFF", "|");
        for (int i = 0; i < monsters.size(); i++) {

           /* if (monsters.contains()) {
                monsters.get(i).draw(graphics, "#e3c205", "X");
                System.out.println("fatGuys encontrado na lista e desenhado");

            }
            if (monsters.contains(BadGuys.class)) {
                monsters.get(i).draw(graphics, "#F62817", "X");
            }



            */
        }
    }
        ;




    public void processKey(KeyStroke key) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (key.getKeyType() == KeyType.ArrowLeft) moveSpaceShip(spaceShip.moveLeft());

        if (key.getKeyType() == KeyType.ArrowRight) moveSpaceShip(spaceShip.moveRight());

        if (key.getKeyType() == KeyType.ArrowUp) {
            CreateBullets(spaceShip.getPosition());
            if (bullets.size() == 0) CreateBullets(spaceShip.getPosition());
            else if (bullets.get(bullets.size() - 1).getPosition().getY() < height - 3) {
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
      if (bullets.size()==0){
          bullets.add(new Bullet(position.getX(), position.getY() - 1));
          audioPlayer.restart();

      }

       if (bullets.get(bullets.size()-1).getPosition().getY()<height-3 ) {bullets.add(new Bullet(position.getX(), position.getY() - 1));
        audioPlayer.restart();}

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


    public void verifyBadGuysCollision() {
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

    public List<BadGuys> CreateBadGuys(int Width, int Height) {

        List<BadGuys> badGuys2 = new ArrayList<>();

        for (int linha = 0; linha < Height; linha++) {
            for (int coluna = 0; coluna < Width; coluna += 3) {
                badGuys2.add(new BadGuys(coluna + (width - Width) / 2, linha));
            }
        }
        return badGuys2;
    }


    public List<Monsters> CreateMonsters(int Width, int Height) {

        int a = 0;
        List<Monsters> monsters2 = new ArrayList<>();


        for (int linha = 0; linha < Height; linha++) {
            for (int coluna = 0; coluna < Width; coluna += 3) {
                if (a == 0) {
                    monsters2.add(new FatGuy(coluna + (width - Width) / 2, linha));
                    a += 1;
                    System.out.println("FatGuy criado");

                }
                if (a == 1) {
                    monsters2.add(new BadGuys(coluna + (width - Width) / 2, linha));
                    a -= 1;
                    System.out.println("BadGuys criado");

                }
            }
        }
        System.out.println("Monster list criada");
return monsters2;

    }



    public void setMonsters(List<Monsters> badGuys) {
        this.monsters = monsters;

    }


    public int isBadGuysEmpty() {
        if (monsters.isEmpty()) {
            level += 1;
            System.out.print("o nivel é   ");
            System.out.println(level);
            return level;
        }
        ;
        return 0;
    }


    public void verifyBulletCollisionEnemy() {
        for (int indexBullets = 0; indexBullets < bullets.size(); indexBullets++) {

            for (int indexMonsters = 0; indexMonsters < monsters.size(); indexMonsters++) {

                if (bullets.get(indexBullets).getPosition().equals(monsters.get(indexMonsters).getPosition())) {

                   if(monsters.isEmpty()){
                        int hitPoints=2;
                    if( hitPoints==0) monsters.remove(indexMonsters);
                    hitPoints -=1 ;

                    } else {

                    monsters.remove(indexMonsters);
                    bullets.remove(indexBullets);}
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

    public void shootBullet(int shotNumb) {
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
                    enemyBullets.remove(indexBulletsAliens);
                    bullets.remove(indexBullets);

                    break;
                }
            }
        }

    }
}







