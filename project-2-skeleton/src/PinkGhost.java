import bagel.*;

/**
 * Class PinkGhost is a class that represents all the pink ghost objects in the game.
 * Parent Class: Ghost
 */
public class PinkGhost extends Ghost{

    private static final Image GHOST_PINK = new Image("res/ghostPink.png");
    private final static double SPEED_NORMAL = 3;
    private final static double SPEED_FRENZY = 2.5;

    private int directionX;
    private int directionY;

    public PinkGhost(double x, double y) {
        super(x, y, GHOST_PINK, SPEED_NORMAL);
        setDirection();    // set the direction of pink ghost
    }

    /**
     * update the pink ghost
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
     * The method updateDirection updates the pink ghost direction.
     * (randomly choose one of left, right, up, down again)
     */
    @Override
    public void updateDirection() {
        setDirection();
    }

    /**
     * The method setDirection randomly chooses one of the 4 directions: up, down, left, right for the pink ghost,
     * and set the movement direction of the pink ghost
     */
    private void setDirection()
    {
        // Generate a random number between 0 and 1
        int randomNum = (int)(Math.random() * 4);

        // Determine the direction of movement based on the random number
        if (randomNum == 0) {
            // Move vertically
            directionX = 0;
            directionY = 1;    // initial positive
        } else if (randomNum == 1) {
            // Move horizontally
            directionX = 1;    // initial positive
            directionY = 0;
        } else if (randomNum == 2) {
            // Move vertically
            directionX = 0;
            directionY = -1;    // initial negative
        }
        else {
            // Move horizontally
            directionX = -1;    // initial negative
            directionY = 0;
        }
    }

    /**
     * set the pink ghost to frenzy mode, change the image and speed of pink ghosts
     */
    @Override
    public void setFrenzy() {

        // set the image to frenzy ghost
        setImage(GHOST_FRENZY);

        // set speed to frenzy speed
        setSpeed(SPEED_FRENZY);
    }

    /**
     * Set the pink ghost back to normal mode, change speed an image back
     * if been eaten at frenzy mode, set back to original position after frenzy mode
     */
    @Override
    public void setBackToNormal()
    {
        // set image to normal image
        setImage(GHOST_PINK);

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
