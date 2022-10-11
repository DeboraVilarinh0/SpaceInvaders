import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ElementTest {

    @Test
    void moveRight() {
        SpaceShip original = new SpaceShip(14, 23);
        Position expected = new Position(15, 23);
        Assertions.assertEquals(expected, original.moveRight());
    }

    @Test
    void moveLeft() {
        SpaceShip original = new SpaceShip(14, 23);
        Position expected = new Position(13, 23);
        Assertions.assertEquals(expected, original.moveLeft());
    }

    @Test
    void moveDown() {
        SpaceShip original = new SpaceShip(14, 23);
        Position expected = new Position(14, 24);
        Assertions.assertEquals(expected, original.moveDown());
    }

    @Test
    void setPosition (Position newPos){
        SpaceShip obtained = new SpaceShip(0,0);
        newPos =  new Position(10,10);
        Position expected =  new Position(10,10);
        obtained.setPosition(newPos);
        Assertions.assertEquals(expected,obtained.getPosition());
    }







}
