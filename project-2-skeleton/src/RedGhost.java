import bagel.*;

/**
 * Class RedGhost is a class that represents all the red ghost objects in the game.
 * Parent Class: Ghost
 */
public class RedGhost extends Ghost{

    private static final Image GHOST_RED = new Image("res/ghostRed.png");
    private final static double SPEED_NORMAL = 1;
    private final static double SPEED_FRENZY = 0.5;

    private int direction = 1;    // initially move to right (positive horizontal direction)

    public RedGhost(double x, double y) {
        super(x, y, GHOST_RED, SPEED_NORMAL);
    }

    /**
     * update the red ghost
     * @param input
     * @param gameObject
     */
    @Override
    public void update(Input input, ShadowPac gameObject) {

        // record the position as previous position
        setPreviousX(getX());
        setPreviousY(getY());

        // implement ghost movement only under level 1
        if (gameObject.getLevelIndicator() == 1)
        {
            setX(getX() + this.getSpeed() * direction);    // red ghost moves horizontally
        }

        // implement collision behaviour of the ghosts
        gameObject.checkGhostCollision(this);

        // draw the ghost if it is active
        if (isActive()){
            render();
        }

    }

    /**
     * update the direction of red ghost (reverse the original direction)
     */
    @Override
    public void updateDirection() {
        direction = direction * -1;
    }

    /**
     * set the red ghost to frenzy mode, change the image and speed of red ghosts
     */
    @Override
    public void setFrenzy() {

        // set the image to frenzy ghost
        setImage(GHOST_FRENZY);

        // set speed to frenzy speed
        setSpeed(SPEED_FRENZY);
    }

    /**
     * Set the red ghost back to normal mode, change speed an image back
     * if been eaten at frenzy mode, set back to original position after frenzy mode
     */
    @Override
    public void setBackToNormal()
    {
        // set image to normal image
        setImage(GHOST_RED);

        // set speed back to normal
        setSpeed(SPEED_NORMAL);

        // if been eaten (inactive), set back to original position
        if (!isActive())
        {
            setActive(true);
            setX(getInitialX());
            setY(getInitialY());
        }
    }

}
