import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

/**
 * Class EatableStationary is a class that represents all the eatable stationary objects in the game.
 * EatableStationary can be eaten by player to increase player's scores.
 * Abstract Class
 * Parent Class: Entity
 * Child Classes: Dot, Cherry, Pellet
 */
public abstract class EatableStationary extends Entity {

    public static final int ZIndex = 0;

    private int pointValue;
    private boolean isActive = true;

    public EatableStationary(double x, double y, Image image, int pointValue) {
        super(x, y, image, ZIndex);
        this.pointValue = pointValue;
    }

    // Getter
    public int getPointValue() {
        return pointValue;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * inactivate the EatableStationary so that it would be treated as disappeared from the game
     */
    public void inactivate()
    {
        this.isActive = false;
    }

    /**
     * update the eatable stationary accordingly at each call
     */
    @Override
    public void update(Input input, ShadowPac gameObject)
    {
        // render the EatableStationary if it is active
        if (isActive){
            render();
        }
    }

    /**
     * render the eatable stationary on screen
     */
    @Override
    public void render(){
        getImage().drawFromTopLeft(getX(), getY());
    }

    /**
     * set EatableStationary to frenzy mode (do nothing)
     */
    @Override
    public void setFrenzy()
    {
        // do nothing
    }

    /**
     * set EatableStationary back to normal mode (do nothing)
     */
    @Override
    public void setBackToNormal()
    {
        // do nothing
    }

}
