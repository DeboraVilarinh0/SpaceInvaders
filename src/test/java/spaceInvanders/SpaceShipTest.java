package spaceInvanders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SpaceShipTest {
    SpaceShip spaceShip = new SpaceShip(10, 10);
    boolean actual = false;
    Arena arena =  new Arena(50,50);

    public SpaceShipTest() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    @Test
    public void setIsInvincibleTest() {
        arena.setIsInvincible(actual=true);
        Assertions.assertTrue(actual);
    }
    @Test
     public void getIsInvincible() {
        boolean result = arena.getIsInvincible();
        boolean expected = false;
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void setShootFasterTest(){
        int shootFaster = 5;
        arena.setShootFaster(shootFaster);
        int expected = 5;
        Assertions.assertEquals(expected, arena.getShootFaster());
    }
}
