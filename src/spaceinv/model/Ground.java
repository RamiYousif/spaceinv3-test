package spaceinv.model;


import static spaceinv.model.SI.*;

/*
    The ground where the Gun lives.
    Used to check if projectiles from ships have hit the ground
 */
public class Ground extends AbstractPositionable {

    public Ground(int width, int height, int yPos){

        setWidth(width);
        setHeight(height);
        setY(yPos);

    }

    public boolean groundColission(double yPos){

        return yPos <= getY();

    }
}
