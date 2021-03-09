package spaceinv.model;

import static spaceinv.model.SI.PROJECTILE_HEIGHT;
import static spaceinv.model.SI.PROJECTILE_WIDTH;

/*
       A projectile fired from the Gun or dropped by a spaceship

       TODO This class should later be refactored (and inherit most of what it needs)
 */
public class Projectile extends AbstractMovable {

	public Projectile(double dy) {
		setWidth(PROJECTILE_WIDTH);
		setHeight(PROJECTILE_HEIGHT);
		setDy(dy);
		// Below: HINT
		//super(0, 0, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, 0, dy);
	}

	@Override
	public void move() {
		setY(getY() - getDy());
		setDy(1.05 * getDy());  // Accelerate
	}


}
