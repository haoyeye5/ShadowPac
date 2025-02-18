import bagel.*;

/**
 * Class BlueGhost is a class that represents all the blue ghost objects in the game.
 * Parent Class: Ghost
 */
public class BlueGhost extends Ghost{

    private static final Image GHOST_BLUE = new Image("res/ghostBlue.png");
    private final static double SPEED_NORMAL = 2;
    private final static double SPEED_FRENZY = 1.5;

    private int direction = 1;    // initially moving down (positive vertical direction)

    public BlueGhost(double x, double y) {
        super(x, y, GHOST_BLUE, SPEED_NORMAL);
    }

    /**
     * update the blue ghost
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
            setY(getY() + this.getSpeed() * direction);    // blue ghost moves vertically
        }

        // implement collision behaviour of the ghosts
        gameObject.checkGhostCollision(this);

        // draw the ghost if active
        if (isActive()){
            render();
        }

    }

    /**
     * update the direction of the blue ghost (reverse the original direction)
     */
    @Override
    public void updateDirection() {
        direction = direction * -1;
    }

    /**
     * set the blue ghost to frenzy mode, change the image and speed of blue ghosts
     */
    @Override
    public void setFrenzy() {

        // set the image to frenzy ghost
        setImage(GHOST_FRENZY);

        // set speed to frenzy speed
        setSpeed(SPEED_FRENZY);
    }

    /**
     * Set the blue ghost back to normal mode, change speed an image back
     * if been eaten at frenzy mode, set back to original position after frenzy mode
     */
    @Override
    public void setBackToNormal()
    {
        // set image to normal image
        setImage(GHOST_BLUE);

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
