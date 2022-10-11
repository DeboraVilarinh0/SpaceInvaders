package spaceInvanders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
        assertEquals(1, arena.enemyBullets.size());
        assertEquals(position, arena.enemyBullets.get(0).getPosition());
    }

    @Test
//Should move the enemy bullets down
    void moveBulletsShouldMoveTheEnemyBulletsDown() {
        arena.moveBullets();
        verify(enemyBullets, times(1)).get(anyInt());
    }

    @Test
        //Should move the bullets up
    void moveBulletsShouldMoveTheBulletsUp() {
        arena.moveBullets();
        verify(bullets, times(1)).get(anyInt());
        verify(enemyBullets, times(1)).get(anyInt());
    }

    @Test
    void testMoveBullets() {
        arena.moveBullets();
    }

    @Test
    void testCreateBullets() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        List<Bullet> result = arena.CreateBullets(new Position(0, 0), true);
        Assertions.assertEquals(List.of(new Bullet(0, 0)).get(0).getPosition().getY(), result.get(0).getPosition().getY());
    }

    @Test
    void testCreateEnemyBullets() {
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
    void testGetIsInvencible() {
        boolean result = arena.getIsInvencible();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSetIsInvencible() {
        arena.setIsInvencible(true);
        assertEquals(true, arena.getIsInvencible());
    }

    @Test
    void testSetShootFaster() {


    }

    @Test
    void testGetShootFaster(){
        arena.getShootFaster();

    }
}
