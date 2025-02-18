import bagel.*;

/**
 * Class GreenGhost is a class that represents all the green ghost objects in the game.
 * Parent Class: Ghost
 */
public class GreenGhost extends Ghost{

    private static final Image GHOST_GREEN = new Image("res/ghostGreen.png");
    private final static double SPEED_NORMAL = 4;
    private final static double SPEED_FRENZY = 3.5;

    private int directionX;
    private int directionY;

    public GreenGhost(double x, double y) {
        super(x, y, GHOST_GREEN, SPEED_NORMAL);
        setDirection();    // set the direction of green ghost
    }

    /**
     * update the green ghost
     * @param input
     * @param gameObject
     */
    @Override
    public void update(Input input, ShadowPac gameObject) {

        // record the current position as previous position
        setPreviousX(getX());
        setPreviousY(getY());

        // implement ghost movement only under level 1
        if (gameObject.getLevelIndicator() == 1)
        {
            setX(getX() + this.getSpeed() * directionX);
            setY(getY() + this.getSpeed() * directionY);
        }

        // implement collision behaviour of the ghosts
        gameObject.checkGhostCollision(this);

        // draw the ghost if active
        if (isActive()){
            render();
        }

    }

    /**
     * update the direction of green ghost (reverses the original direction)
     */
    @Override
    public void updateDirection() {
        directionX *= -1;
        directionY *= -1;
    }

    /**
     * The method setDirection randomly choose one of the two directions: horizontal or vertical for the green ghost,
     * and set the initial movement as positive of that direction
     */
    private void setDirection()
    {
        // Generate a random number between 0 and 1
        int randomNum = (int)(Math.random() * 2);

        // Determine the direction of movement based on the random number
        if (randomNum == 0) {
            // Move vertically
            directionX = 0;
            directionY = 1;    // initially positive
        } else {
            // Move horizontally
            directionX = 1;    // initially positive
            directionY = 0;
        }
    }

    /**
     * set the green ghost to frenzy mode, change the image and speed of green ghosts
     */
    @Override
    public void setFrenzy() {

        // set the image to frenzy ghost
        setImage(GHOST_FRENZY);

        // set speed to frenzy speed
        setSpeed(SPEED_FRENZY);
    }

    /**
     * Set the green ghost back to normal mode, change speed an image back
     * if been eaten at frenzy mode, set back to original position after frenzy mode
     */
    @Override
    public void setBackToNormal()
    {
        // set image to normal image
        setImage(GHOST_GREEN);

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
