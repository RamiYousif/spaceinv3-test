package spaceinv.model;

/*
    A utility to shoot projectiles
    Pure functionality (static)

    *** Nothing to do here ***

 */
public class Shooter {

    // Will create vertical moving projectile starting
    // at the firing objects "top/bottom-center"
    // Handle the projectile over to the "game loop" to move it.
    public static Projectile fire(AbstractPositionable positionable, double dy) {
        Projectile p = new Projectile(dy);

        p.setX(positionable.getX() + positionable.getWidth() / 2 - p.getWidth() / 2);

        if(dy > 0){
            p.setY(positionable.getY() - p.getHeight());
        }
        else if (dy <0){
            p.setY(positionable.getY() + positionable.getHeight() / 2 + p.getHeight());
        }

        return p;
    }

    private Shooter() {
    }
}
