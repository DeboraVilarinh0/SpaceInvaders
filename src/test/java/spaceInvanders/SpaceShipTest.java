package spaceInvanders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpaceShipTest {
    SpaceShip spaceShip = new SpaceShip(10, 10);
    boolean actual = false;

    @Test
    public void setIsInvincibleTest() {
        spaceShip.setIsInvincible(actual=true);
        Assertions.assertTrue(actual);
    }
    @Test
     public void getIsInvincible() {
        boolean result = spaceShip.getIsInvincible();
        boolean expected = false;
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void setShootFasterTest(){
        int shootFaster = 5;
        spaceShip.setShootFaster(shootFaster);
        int expected = 5;
        Assertions.assertEquals(expected, spaceShip.getShootFaster());
    }
}
