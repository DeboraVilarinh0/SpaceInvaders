package spaceInvanders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BulletTest {

    @Test
    public void bulletMovementUPTest(){
        Bullet initialBullet = new Bullet(10, 10);
        Bullet expected = new Bullet(10,9);
        Position actual = initialBullet.bulletMovementUP();
        Assertions.assertEquals(actual.getY(), expected.getPosition().getY());
    }
}
