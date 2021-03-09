package spaceinv.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import spaceinv.event.EventBus;
import spaceinv.event.EventHandler;
import spaceinv.event.ModelEvent;
import spaceinv.model.*;
import spaceinv.model.ships.BattleCruiser;
import spaceinv.model.ships.Bomber;
import spaceinv.model.ships.Frigate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static java.lang.System.out;
import static spaceinv.model.SI.*;


/*
 *  The GUI for the SpaceInv game (the view of the model).
 *  Run this to run the game
 *
 *  No application logic here just look/rendering, event handling
 *  and input to model
 */
public class SIGUI extends Application implements EventHandler {


    private SI spaceInv;                // Reference to the OO-model
    private boolean running = false;    // Is game running?

    // ------- Keyboard handling ----------------------------------

    private void keyPressed(KeyEvent event) {
        if (!running) { return; }
        KeyCode kc = event.getCode();
        switch (kc) {
            case LEFT:
               // TODO
                SI.setGunDirection(Direction.LEFT);
                break;
            case RIGHT:
               // TODO
                SI.setGunDirection(Direction.RIGHT);
                break;
            case SPACE:
                spaceInv.shootGun();
                break;
            default:  // Nothing
        }
    }

    private void keyReleased(KeyEvent event) {
        if (!running) { return; }
        KeyCode kc = event.getCode();//test
        // Breaks at shooting as well to counteract windows
        // functionality.
        switch (kc) {
            case LEFT:
            case RIGHT:

                SI.setGunDirection(Direction.STOP);
                break;
            default: // Nothing
        }
    }

    // ---- Menu handling -----------------

    private void handleMenu(ActionEvent e) {
        MenuItem mi = (MenuItem) e.getSource();
        switch (mi.getText()) {
            case "New":
                newGame();
                break;
            case "Stop":
                stopGame();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Play":
                toggleMusic();
                break;
            default: // Nothing
        }
    }

    // ---------- Menu actions ---------------------

    private void newGame() {




        List<AbstractShips> ships = new ArrayList<>();
        int nShips = 10 * (SHIP_WIDTH + 10);
        for (int i = LEFT_LIMIT + 1; i < nShips; i += SHIP_WIDTH * 1.2) {
            ships.add(new Frigate(i, SHIP_HEIGHT, SHIP_WIDTH,2));
        }
        for (int i = LEFT_LIMIT + 1; i < nShips; i += SHIP_WIDTH * 1.2) {
            ships.add(new BattleCruiser(i, SHIP_HEIGHT*2, SHIP_WIDTH, 2));
        }

        for (int i = LEFT_LIMIT + 1; i < nShips; i += SHIP_WIDTH * 1.2) {
            ships.add(new Bomber(i, SHIP_HEIGHT*3, SHIP_WIDTH, 2));
        }




        // TODO Build model

        // NOTE: Declared at top of class

        Ground ground = new Ground(GAME_WIDTH, GROUND_HEIGHT, GAME_HEIGHT - GROUND_HEIGHT);
        OuterSpace space = new OuterSpace(GAME_WIDTH, 40);

        spaceInv = new SI(new Gun(40,40), ships,ground , space);

        renderBackground();
        timer.start();
        running = true;
    }

    private void stopGame() {
        running = false;
        timer.stop();
    }

    private void toggleMusic() {
        // TODO Optional
        out.println("toggle");
       /* AudioClip music = FileService.getSound("music");
        if (music.isPlaying()) {
            music.stop();
            //setCheckMenuItemSelected("Music", "Play", false);
        } else {
            music.play(0.1);
            //setCheckMenuItemSelected("Music", "Play", true);
        }*/
    }

    // --- Handling events coming form the model -----
    // Implements EventHandler
    @Override
    public void onModelEvent(ModelEvent evt) {
        AbstractPositionable p;
        switch (evt.type) {
            case GUN_HIT_SHIP:
                p = (AbstractPositionable) evt.data;
                new Explosion(p.getX(), p.getY(), fg).start();
                Assets.INSTANCE.rocketHitShip.play();
                break;
            case GUN_SHOOT:
                // TODO Optional
                break;
            case BOMB_HIT_GROUND:
                // TODO Optional
                break;
            case BOMB_HIT_GUN:
                p = (AbstractPositionable) evt.data;
                new Explosion(p.getX(), p.getY(), fg).start();
                timer.stop();
                running = false;
                break;
            case HAS_WON:
                timer.stop();
                running = false;
                break;
            default:
        }
    }

    // ************* Rendering and JavaFX below (nothing to do)  *************

    private void render() {
        fg.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        for (AbstractPositionable d : spaceInv.getPositionables()) {
            Image i = Assets.INSTANCE.get(d.getClass());
            fg.drawImage(i, d.getX(), d.getY(), d.getWidth(), d.getHeight());
        }
        //out.println(spaceInv.getPoints());
        fg.setFont(Assets.INSTANCE.font);
        fg.setFill(Assets.INSTANCE.colorFgText);
        fg.fillText(String.valueOf(spaceInv.getPoints()),
                10 , GAME_HEIGHT - Assets.INSTANCE.font.getSize());

    }

    private void renderBackground() {
        bg.drawImage(Assets.INSTANCE.background, 0, 0, GAME_WIDTH, GAME_HEIGHT);
    }

    private AnimationTimer timer;
    private GraphicsContext fg;   // Foreground
    private GraphicsContext bg;   // Background
    private SIMenu menuBar;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        // Menu
        menuBar = new SIMenu(this::handleMenu);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        // Drawing areas
        Canvas backGround = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        bg = backGround.getGraphicsContext2D();
        Canvas foreGround = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        fg = foreGround.getGraphicsContext2D();

        Pane pane = new Pane(backGround, foreGround);
        root.setCenter(pane);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                spaceInv.update(now);
                render();
            }
        };

        bg.drawImage(Assets.INSTANCE.background, 0, 0, GAME_WIDTH, GAME_HEIGHT);
        EventBus.INSTANCE.register(this);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::keyPressed);
        scene.setOnKeyReleased(this::keyReleased);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Invaders");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
