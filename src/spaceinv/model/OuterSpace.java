package spaceinv.model;

import static spaceinv.model.SI.GAME_WIDTH;
import static spaceinv.model.SI.OUTER_SPACE_HEIGHT;

/*
    Used to check if projectiles from gun have left our world
    Non visible class
 */
public class OuterSpace extends AbstractPositionable {

    public OuterSpace(int width, int height){

        setWidth(width);
        setHeight(height);
        setY(-20);

    }

    public boolean spaceColission(double yPos){

        return getY() <= yPos;

    }

}
