package spaceInvanders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ArenaTest {
    @Mock
    SpaceShip spaceShip;
    @Mock
    List<Bullet> bullets;
    @Mock
    List<EnemyBullet> enemyBullets;
    @Mock
    List<PowerUps> powerUps;
    @Mock
    SimpleAudioPlayer audioPlayer;
    @Mock
    List<Monsters> monsters;
    @InjectMocks
    Arena arena = new Arena(50, 50);

    ArenaTest() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
//Should set the firemultiplebullets to true
    void setFireMultipleBulletsShouldSetTheFireMultipleBulletsToTrue() {
        arena.setFireMultipleBullets(true);
        assertTrue(arena.fireMultipleBullets);
    }

    @Test
//Should create a new enemy bullet in the position passed as parameter
    void createEnemyBulletsShouldCreateANewEnemyBulletInThePositionPassedAsParameter() {
        Position position = new Position(10, 10);
        arena.CreateEnemyBullets(position);
        int expected = 1;
        assertEquals(expected, arena.enemyBullets.size());
        assertEquals(position, arena.enemyBullets.get(0).getPosition());
    }

    @Test
//Should move the enemy bullets down
    void moveBulletsShouldMoveTheEnemyBulletsDown() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Arena arena2 = new Arena(50, 50);
        Position p  = new Position (2,26);
        arena2.CreateEnemyBullets(p);
        arena2.moveBullets();
        Position p2  = new Position (2,27);
        Assertions.assertEquals(p2, arena2.getEnemyBullets().get(0).getPosition());
       //  Assertions.assertEquals(p2.getY(), arena2.getBullets().get(0).getPosition().getY());
        // Assertions.assertEquals(p2.getX(), arena2.getBullets().get(0).getPosition().getX());

    }
    @Test
        //Should move the bullets up
    void moveBulletsShouldMoveTheBulletsUp() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Arena arena2 = new Arena(50, 50);
        Position p  = new Position (2,29);
        arena2.CreateBullets(p, false);
        arena2.moveBullets();
        Position p2  = new Position (2,27);
        Assertions.assertEquals(p2, arena2.getBullets().get(0).getPosition());
       // Assertions.assertEquals(p2.getY(), arena2.getBullets().get(0).getPosition().getY());
       // Assertions.assertEquals(p2.getX(), arena2.getBullets().get(0).getPosition().getX());

    }


    @Test
    void CreateBulletsTest() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        List<Bullet> result = arena.CreateBullets(new Position(0, 0), true);
        Assertions.assertEquals(List.of(new Bullet(0, 0)).get(0).getPosition().getY(), result.get(0).getPosition().getY());
    }

    @Test
    public void CreateEnemyBulletsTest() {
        List<EnemyBullet> result = arena.CreateEnemyBullets(new Position(0, 0));
        Assertions.assertEquals(List.of(new EnemyBullet(0, 0)).get(0).getPosition(), result.get(0).getPosition());
    }

    @Test
    void testCreateMonsters() {
        arena.CreateMonsters(0, 0);
    }

    @Test
    void testIsMonsterEmpty() {
        int result = arena.isMonsterEmpty();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testVerifyMonsterCollision() {
        when(spaceShip.getPosition()).thenReturn(new Position(0, 0));

        arena.verifyMonsterCollision();
    }

    @Test
    void testCleanBullet() {
        when(spaceShip.getPosition()).thenReturn(new Position(0, 0));

        arena.cleanBullet();
    }

    @Test
    void testShootBullet() {
        when(spaceShip.getPosition()).thenReturn(new Position(0, 0));

        arena.shootBullet(0L);
    }

    @Test
    void testVerifyCollisionBetweenBullets() {
        when(spaceShip.getPosition()).thenReturn(new Position(0, 0));

        arena.verifyCollisionBetweenBullets();
    }

    @Test
    void testCreatePowerUps() {
        arena.CreatePowerUps();
    }

    @Test
    void testVerifyPowerUpCollision() {
        when(spaceShip.getPosition()).thenReturn(new Position(0, 0));
        arena.verifyPowerUpCollision();
    }

    @Test
    void testGetIsInvincible() {
        when(spaceShip.getIsInvincible()).thenReturn(true);
        boolean result = arena.getIsInvincible();
        boolean expected = true;
        Assertions.assertEquals(expected, result);
    }


    @Test
    void testSetIsInvincible() {
        arena.setIsInvincible(true);
    }

    @Test
    void testSetShootFaster() {
        arena.setShootFaster(0);
    }
}
