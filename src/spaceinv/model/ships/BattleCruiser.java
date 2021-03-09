package spaceinv.model.ships;

import spaceinv.model.*;

/*
 *   Type of space ship
 */
public class BattleCruiser extends AbstractShips implements Shootable {

    // Default value
    public static final int BATTLE_CRUISER_POINTS = 100;

/*
    @Override
    public Projectile fire() { return Shooter.fire(this, 0); }
 */

    public int getpoints(){
        return BATTLE_CRUISER_POINTS;
    }

    public BattleCruiser(int xpos, int ypos, int scale, double dx){

        setWidth(scale);
        setHeight(scale);

        setX(xpos);
        setY(ypos);

        setDx(dx);


    }
}
