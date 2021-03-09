package spaceinv.model;

public abstract class AbstractPositionable implements Positionable {
	private double x;
	private double y;
	private double width;
	private double height;


	public double getX() { return x;}


	public double getY() {
		return y;
	}


	public void setX(double x) { this.x = x; }


	public void setY(double y) { this.y = y; }


	public double getWidth() { return width; }


	public double getHeight() { return height; }


	public void setWidth(double width) { this.width = width; }


	public void setHeight(double height) { this.height = height; }

	public boolean collide(AbstractPositionable other){

		boolean above = other.getYBound() < getY();
		boolean below = other.getY() > getYBound();
		boolean leftOf = other.getXBound() < getX();
		boolean rightOf = other.getX() > getXBound();

		return !(above || below || leftOf || rightOf);

	}


 /*
	public boolean collide(AbstractPositionable other){

		double middlex = (getX() + getXBound()) / 2;
		double middley = (getY() + getYBound()) / 2;
		boolean horizontal = (other.getXBound() >= middlex && middlex >= other.getX());
		boolean vertical = (other.getYBound() >= middley && middley >= other.getY());

		System.out.println("hor = " + horizontal + " vert = " + vertical + " middle x: " + middlex + " middle y: " + middley + " other x,xb,y,yb : " + other.getX() + " " + other.getXBound() + " " + other.getY()+ " " + other.getYBound());

		return horizontal && vertical;

	}

 */

	public double getYBound () { return y + height;}

	public double getXBound () { return x + width;}

}
