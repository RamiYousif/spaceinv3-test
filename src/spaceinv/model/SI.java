package spaceinv.model;

import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;
import spaceinv.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  SI (Space Invader) class representing the overall
 *  data and logic of the game
 *  (nothing about the look/rendering here)
 */
public class SI {

	// Default values (not to use directly). Make program adaptable
	// by letting other programmers set values if they wish.
	// If not, set default values (as a service)
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 800;
	public static final int SHIP_WIDTH = 50;
	public static final int SHIP_HEIGHT = 50;
	public static final int LEFT_LIMIT = SHIP_WIDTH;
	public static final int RIGHT_LIMIT = GAME_WIDTH - SHIP_WIDTH;
	public static final int SHIP_MAX_DX = 1;
	public static final int SHIP_MAX_DY = 0;
	public static final int GUN_WIDTH = 20;
	public static final int GUN_HEIGHT = 20;
	public static final int GUN_Y = GAME_HEIGHT - 80;
	public static final double GUN_MAX_DX = 7;
	public static final double PROJECTILE_WIDTH = 10;
	public static final double PROJECTILE_HEIGHT = 20;
	public static final double GUN_PROJECTILE_DY = 0.5;
	public static final int GROUND_HEIGHT = 20;
	public static final int OUTER_SPACE_HEIGHT = 10;


	public static final long ONE_SEC = 1_000_000_000;
	public static final long HALF_SEC = 500_000_000;
	public static final long TENTH_SEC = 100_000_000;

	private static final Random rand = new Random();

	// TODO More references here
	private final Gun gun;
	private final List<Projectile> shipBombs = new ArrayList<>();
	private Projectile gunProjectile;
	private int points;
	private Ground ground;
	private OuterSpace space;
	private List<AbstractShips> ships;
	private boolean bounce = false;
	private boolean shooterfound = false;
	private int ydiff = SHIP_HEIGHT;

	// TODO Constructor here
	public SI(Gun gun, List<AbstractShips> ships, Ground ground, OuterSpace space) {
		this.gun = gun;
		gun.setX(GAME_WIDTH / 2.0 - GUN_WIDTH / 2.0);
		gun.setY(GUN_Y);
		this.ships = ships;
		this.ground = ground;
		this.space = space;
	}

	// Timing. All timing handled here!
	private long lastTimeForMove;
	private long lastTimeForFire;
	private int shipToMove = 0;
	// ------ Game loop (called by timer) -----------------


	public void update(long now) {

        if( ships.size() == 0){
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
        }

        /*
             Movement
         */

		for(AbstractShips ship : ships){
			ship.move();
			if(ship.getX() + SHIP_WIDTH >= GAME_WIDTH || ship.getX() <= 0){
				bounce = true;
			}
		}

		if (bounce){
			for (AbstractShips alien : ships) {
				alien.setDx(-alien.getDx());
				alien.setY(alien.getY() + 10);
				bounce = false;
			}
		}


		// Move projectile if present

		// Speed up or slow down Gun in the x axis
		switch (Gun.getGunDirection()) {
			case LEFT:
				accelerateGun(-0.2);
				break;
			case RIGHT:
				accelerateGun(0.2);
				break;
			case STOP:
				decelerate();
		}
		// Bounces Gun off of map edge (negates acceleration)
		if (!validLocation(gun.getX() + gun.getDx() + GUN_WIDTH, gun.getY())) {
			gun.setDx(-gun.getDx() / 2);
		}
		// Moves and accelerates projectile
		if (gunProjectile != null) {
			gunProjectile.move();
			gunProjectile.accelerate(0, GUN_PROJECTILE_DY);
		}
		// Moves gun
		gun.move();
		// Update y position of Gun if recoil
		if (gun.getDy() != 0 || gun.getY() != GUN_Y) { recoilGun(); }

        /*
            Ships fire
         */
		lastTimeForFire++;

		if (!shooterfound && lastTimeForFire >= 30 ){
			AbstractShips potentialshooter = ships.get(rand.nextInt(ships.size()));
			if(potentialshooter.canshoot(ships, ydiff)){
				shooterfound = true;
				shootershoot(potentialshooter);
				lastTimeForFire = 0;
			}
		}

		for (Projectile bomb:shipBombs) {
			bomb.move();
		}


        /*
             Collisions
         */
		// Removes bullet if collision with outer space


			if (gunProjectile != null){

				for (int i = 0; i < ships.size(); i ++) {
					AbstractShips ship = ships.get(i);
					if (gunProjectile != null && ship != null && gunProjectile.collide(ship)){
						EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_HIT_SHIP, gunProjectile));
						gunProjectile = null;
						points += ship.getpoints();
						ships.remove(ships.indexOf(ship));
						if(Math.abs(ship.getDx()) <= 5){
							for (AbstractShips alien: ships) {
								if (alien.getDx() > 0){
									alien.setDx(alien.getDx() + 0.2);
								}
								else if (alien.getDx() < 0){
									alien.setDx(alien.getDx() - 0.3);
								}
						}
						}
					}
				}
				if (gunProjectile != null && gunProjectile.collide(space)){
					gunProjectile = null;
				}
			}
/*
			if (shipBombs.size() > 0){
				for (int i = 0; i< shipBombs.size(); i ++) {
					if (shipBombs.get(i).collide(gun)){
						EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GUN, gun));
					}
				}
			}

 */




	}

	private void shootershoot (AbstractShips shooter){
		shipBombs.add(shooter.fire());
		shooterfound = false;
	}

	public enum Direction {
		LEFT,
		RIGHT,
		STOP
	}

	public void accelerateGun(double df) {
		// Moves gun to max velocity while arrow-keys are pressed
		double currentSpeed = gun.getDx();
		if (-GUN_MAX_DX <= currentSpeed && currentSpeed <= GUN_MAX_DX) {
			gun.accelerate(df,0);
		}
	}

	private void recoilGun() {
		/* Moves Gun up and down until back at original position
		 through initially setting dy to positive and then
		 decreasing it past zero*/
		if (gun.getY() != GUN_Y) {
			gun.accelerate(0, -1);
		} else {
			gun.setDy(0);
		}
	}

	public void decelerate() {
		// Decelerates Gun until 0 if key not pressed
		double dx = gun.getDx();
		if (dx >= 0.05) {
			gun.accelerate(-0.15,0);
		} else if (dx <= -0.05) {
			gun.accelerate(0.15,0);
		} else { gun.setDx(0); } // Gun stops
	}

	private boolean validLocation(double x, double y) {
		return (x <= RIGHT_LIMIT && LEFT_LIMIT <= x && space.spaceColission(y) && ground.groundColission(y));
	}


	// ---------- Interaction with GUI  -------------------------
	static public void setGunDirection(Direction direction)
	{
		Gun.setGunDirection(direction);
	}

	public void stopGun() {
		gun.setDx(gun.getDx() / 5);
		gun.accelerate(0, 4);
	}

	public void shootGun() {
		// If projectile doesn't already exist create a new one
		if (gunProjectile == null) {
			gunProjectile = gun.fire();
			stopGun();
		}
	}

	// TODO More methods called by GUI

	public List<AbstractPositionable> getPositionables() {
		List entities = new ArrayList();
		entities.addAll(ships);
		entities.addAll(shipBombs);
		entities.add(gun);
		entities.add(ground);
		entities.add(space);

		if (gunProjectile != null) { entities.add(gunProjectile); }
		return entities;
	}

	public int getPoints() {
		return points;
	}


}
