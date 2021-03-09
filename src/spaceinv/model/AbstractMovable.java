package spaceinv.model;

public abstract class AbstractMovable extends AbstractPositionable  {

    private double dx;
    private double dy;


    public void accelerate(double dx, double dy){
        this.dx += dx;
        this.dy += dy;
    }


    public void move(){
        setX(getX()+ this.dx);
        setY(getY()+ this.dy);
    }


    public double getDy() { return dy; }



    public void setDy(double dy) { this.dy = dy; }



    public void setDx(double dx) {this.dx = dx;}



    public double getDx() {
        return dx;
    }


}
