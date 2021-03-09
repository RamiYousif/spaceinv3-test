package spaceinv.model;

import static spaceinv.model.SI.Direction;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun extends AbstractMovable implements Shootable {
	private static Direction gunDirection = SI.Direction.STOP;

	public static void setGunDirection(Direction direction) {
		gunDirection = direction;
	}

	public static Direction getGunDirection() {
		return gunDirection;
	}

	@Override
	public Projectile fire() { return Shooter.fire(this, 0.5); }

	public Gun(int width, int height){
		setWidth(width);
		setHeight(height);
	}

}
