package spaceinv.model;


import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;

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
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 1000;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int SHIP_MAX_DX = 5;
    public static final int SHIP_MAX_DY = 0;
    public static final int GUN_WIDTH = 20;
    public static final int GUN_HEIGHT = 20;
    public static final double GUN_MAX_DX = 5;
    public static final double PROJECTILE_WIDTH = 5;
    public static final double PROJECTILE_HEIGHT = 5;
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

    // TODO Constructor here

    public SI (Gun gun){

        this.gun = gun;

    }


    // Timing. All timing handled here!
    private long lastTimeForMove;
    private long lastTimeForFire;
    private int shipToMove = 0;

    // ------ Game loop (called by timer) -----------------

    public boolean decelerate = false;

    public void update(long now) {

        /*if( ships.size() == 0){
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
        }*/

        /*
             Movement
         */

        Gun.x += gun.dx;

        if(decelerate){
            if (Math.abs(gun.dx) < 0.05) {
                gun.dx = 0;
                decelerate = false;
            } else if (gun.dx < 0) {
                gun.dx += 0.05;
            } else if (gun.dx > 0) {
                gun.dx -= 0.05;
            }
            System.out.println("DOWN");
        }

        /*
            Ships fire
         */


        /*
             Collisions
         */

    }

    public void accL(boolean trigger){
        if (trigger) {
            if (Math.abs(gun.dx) < 0.5) {
                gun.dx = -2;
            } else if (gun.dx > -GUN_MAX_DX) {
                gun.dx -= 0.2;
            } else {
                gun.dx = -GUN_MAX_DX;
            }
            System.out.println("LEFT");
        }
    }
    public void accR(boolean trigger){
        if (trigger) {
            if (Math.abs(gun.dx) < 0.5) {
                gun.dx = 2;
            } else if (gun.dx < GUN_MAX_DX) {
                gun.dx += 0.2;
            } else {
                gun.dx = GUN_MAX_DX;
            }
            System.out.println("RIGHT");
        }
    }

    private boolean shipHitRightLimit() {
        // TODO
        return false;
    }

    private boolean shipHitLeftLimit() {
        // TODO
        return false;
    }


    // ---------- Interaction with GUI  -------------------------

    public void fireGun() {
        // TODO
    }

    // TODO More methods called by GUI

    public List<Positionable> getPositionables() {
        List<Positionable> ps = new ArrayList<>();
        ps.add(gun);
        return ps;
    }

    public int getPoints() {
        return points;
    }


}
