package spaceinv.model.ships;

import spaceinv.model.*;

/*
 *   Type of space ship
 */
public class Frigate extends AbstractShips implements Shootable {

    // Default value
    public static final int FRIGATE_POINTS = 300;

/*
    @Override
    public Projectile fire() { return Shooter.fire(this, 0); }
 */

    public int getpoints(){
        return FRIGATE_POINTS;
    }

    public Frigate(int xpos, int ypos, int scale,double dx){

        setWidth(scale);
        setHeight(scale);

        setX(xpos);
        setY(ypos);

        setDx(dx);

    }


}
