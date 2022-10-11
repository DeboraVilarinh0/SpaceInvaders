package spaceInvanders;

public class EnemyBullet extends Element{

        EnemyBullet(int x, int y) {
            super(x, y);
        }

        public Position bulletMovementDOWN() {
            return new Position(position.getX(), position.getY() + 1);
        }
    }




