package spaceinv.model;
import java.util.List;

public abstract class AbstractShips extends  AbstractMovable implements Shootable{

    public abstract int getpoints();

    public Projectile fire() { return Shooter.fire(this, -1); }

    public boolean canshoot( List<AbstractShips> ships, int ydiff){

        double middlex = getX() + getWidth() / 2;

       for (AbstractShips ship : ships){

           if (this != ship) {

               double middley1 = getY() + getHeight() / 2 + ydiff;

               boolean horizontal = (ship.getXBound() >= middlex && middlex >= ship.getX());
               boolean vertical1 = (ship.getYBound() >= middley1 && middley1 >= ship.getY());

               if(vertical1 && horizontal){
                   return false;
               }

           }
       }

       for (AbstractShips ship : ships){

           if (this != ship) {
               double middley2 = getY() + getHeight() / 2 + ydiff * 2;

               boolean horizontal = (ship.getXBound() >= middlex && middlex >= ship.getX());
               boolean vertical2 = (ship.getYBound() >= middley2 && middley2 >= ship.getY());


               if(vertical2 && horizontal){
                   return false;
               }
           }

       }
       return true;
    }
}