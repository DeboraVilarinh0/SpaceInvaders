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
        actual = true;
        spaceShip.getIsInvincible();
        Assertions.assertTrue(spaceShip.getIsInvincible());
    }
}
