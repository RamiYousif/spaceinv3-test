package spaceinv.model.ships;

import spaceinv.model.*;

/*
 *   Type of space ship
 */
public class Bomber extends AbstractShips implements Shootable {

    // Default value
    public static final int BOMBER_POINTS = 200;


/*
    @Override
    public Projectile fire() { return Shooter.fire(this, 0); }
 */

    public int getpoints(){
        return BOMBER_POINTS;
    }

    public Bomber(int xpos, int ypos, int scale, double dx){

        setWidth(scale);
        setHeight(scale);

        setX(xpos);
        setY(ypos);

        setDx(dx);


    }
}
