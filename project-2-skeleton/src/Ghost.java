import bagel.*;
import bagel.util.Rectangle;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Class Ghost is a class that represents all the ghost objects in the game.
 * Ghosts are stationary at level-0, and move in level-1.
 * Ghosts can attack player, or be attacked by the player at frenzy mode.
 * Abstract Class
 * Parent Class: Entity
 * Child Classes: RedGhost, BlueGhost, GreenGhost, PinkGhost
 */
public abstract class Ghost extends Entity {

    public static final int ZIndex = 1;
    protected final Image GHOST_FRENZY = new Image("res/ghostFrenzy.png");
    public final int FRENZY_POINT_VALUE = 30;


    private double speed;
    private double initialX;
    private double initialY;
    private double previousX;
    private double previousY;
    private boolean isActive = true;

    public Ghost(double x, double y, Image image, double speed) {
        super(x, y, image, ZIndex);
        this.speed = speed;

        // set the initial position for the ghost
        initialX = x;
        initialY = y;
    }

    // Getter

    public double getInitialX() {
        return initialX;
    }

    public double getInitialY() {
        return initialY;
    }

    public double getPreviousX() {
        return previousX;
    }

    public double getPreviousY() {
        return previousY;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isActive() {
        return isActive;
    }

    //Setter

    public void setPreviousX(double previousX) {
        this.previousX = previousX;
    }

    public void setPreviousY(double previousY) {
        this.previousY = previousY;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    /**
     * move the ghost accordingly, an abstract method needed to be implemented
     * @param input
     * @param gameObject
     */
    @Override
    public abstract void update(Input input, ShadowPac gameObject);

    /**
     * update the direction of the corresponding ghost, an abstract method needed to be implemented
     */
    public abstract void updateDirection();

    /**
     * reset the ghost to any position
     * @param x
     * @param y
     */
    public void resetPosition(double x, double y)
    {
        setX(x);
        setY(y);
    }

    /**
     * render the ghost on screen
     */
    @Override
    public void render()
    {
        getImage().drawFromTopLeft(getX(), getY());
    }

    /**
     * set the ghost to frenzy mode, change the image and speed of ghosts correspondingly.
     * An abstract method needed to be implemented
     */
    @Override
    public abstract void setFrenzy();

    /**
     * set the ghost back to normal mode, an abstract method needed to be implemented.
     */
    @Override
    public abstract void setBackToNormal();

}
