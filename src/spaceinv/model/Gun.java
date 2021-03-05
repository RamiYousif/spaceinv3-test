package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun implements Positionable {

    double dx = 0;
    public static double x;
    public final static double y = 850;
    int width = 40;
    int height = 40;

    @Override
    public  double getX() {return x;}

    @Override
    public  double getY() {return y;}

    @Override
    public  double getWidth() {return width;}

    @Override
    public  double getHeight() {return height;}

}
