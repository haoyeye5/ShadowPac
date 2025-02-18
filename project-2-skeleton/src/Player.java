import bagel.*;
import bagel.util.Rectangle;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class Player is a class that represents the player in the game.
 * Player can eat eatableStationary to gain scores.
 * Player would be attacked by ghosts, or attack ghosts in frenzy mode to gain scores.
 * PLayer can only move around the wall, it cannot pass through the wall.
 * Parent Class: Entity
 */
public class Player extends Entity {

    public static final int ZIndex = 2;

    // directions
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final Image PLAYER = new Image("res/pac.png");
    public static final Image PLAYER_OPEN = new Image("res/pacOpen.png");
    private final Font SCORE_FONT = new Font("res/FSO8BITR.TTF", 20);
    private final static Point SCORE_POINT = new Point(25,25);
    private ArrayList<Heart> hearts = new ArrayList<>(Arrays.asList(new Heart(new Point(900, 10)),
            new Heart(new Point(930, 10)),
            new Heart(new Point(960, 10))));
    // the 3 hearts have fixed position


    public final static double SPEED_NORMAL = 3;
    public final static double SPEED_FRENZY = 4;
    private final static int PLAYER_IMAGE_CHANGE_FRAME = 15;
    private final static int MAX_LIVES = 3;

    private double initialX;
    private double initialY;
    private double previousX;
    private double previousY;
    private int playerDirection = RIGHT;
    private double speed = SPEED_NORMAL;
    private DrawOptions drawOption = new DrawOptions();
    private boolean isOpen = false;
    private int frameNumber = PLAYER_IMAGE_CHANGE_FRAME;
    private int lives = MAX_LIVES;
    private int scores = 0;

    public Player(double x, double y) {
        super(x, y, PLAYER, ZIndex);

        // set the initial position for the player
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

    public int getPlayerDirection() {
        return playerDirection;
    }

    // setter
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Move the player upwards, change the facing direction of the player
     */
    public void moveUp() {
        // set the rotation of the player to radians corresponding to Up
        drawOption.setRotation(-Math.PI / 2);

        // move speed size of pixels each time
        this.setY(getY() - speed);

        // update the direction to current direction
        this.playerDirection = UP;
    }

    /**
     * Move the player downwards, change the facing direction of the player
     */
    public void moveDown() {
        drawOption.setRotation(Math.PI / 2);
        this.setY(getY() + speed);
        this.playerDirection = DOWN;
    }

    /**
     * Move the player leftwards, change the facing direction of the player
     */
    public void moveLeft() {
        drawOption.setRotation(-Math.PI);
        this.setX(getX() -speed);
        this.playerDirection = LEFT;
    }

    /**
     * Move the player rightwards, change the facing direction of the player
     */
    public void moveRight() {
        drawOption.setRotation(0);
        this.setX(getX() + speed);
        this.playerDirection = RIGHT;
    }


    /**
     * move the player and implement corresponding collision logic
     * @param input
     * @param gameObject
     */
    public void update(Input input, ShadowPac gameObject)
    {
        // switch the player image every 15 frames
        frameNumber = changePlayerImage(frameNumber, PLAYER_IMAGE_CHANGE_FRAME);

        // implement movement of the player

        //record the current position as previous position of player
        previousX = getX();
        previousY = getY();

        // if do not collide with walls, player moves freely
        if (input.isDown(Keys.UP)){moveUp();}
        if (input.isDown(Keys.DOWN)){moveDown();}
        if (input.isDown(Keys.LEFT)){moveLeft();}
        if (input.isDown(Keys.RIGHT)){moveRight();}

        // check the collision of player and implement corresponding logic.
        gameObject.checkPlayerCollision(input, this);

        // render the player, player's lives and scores
        render();
        renderHearts(hearts, lives);
        renderScore(scores);

    }

    /**
     * set the player to frenzy mode (increase the speed by 1)
     */
    @Override
    public void setFrenzy()
    {
        speed = SPEED_FRENZY;
    }

    /**
     * set the player back to normal mode (change the speed back)
     */
    @Override
    public void setBackToNormal()
    {
        speed = SPEED_NORMAL;
    }

    /**
     * increase the score by certain scores
     * @param score
     */
    public void incrementScore(int score) {
        scores += score;
    }

    /**
     * reduce the lives of player by 1
     */
    public void reduceLives() {
        lives--;
    }

    /**
     * reset the position of the player to any position
     * @param x
     * @param y
     */
    public void resetPosition(double x, double y)
    {
        setX(x);
        setY(y);
    }

    /**
     * change the player's image every 15 frames
     * @param frameNum
     * @param changeFrameNum
     * @return
     */
    public int changePlayerImage(int frameNum, int changeFrameNum)
    {
        frameNum--;    // reduce frameNumber by 1
        if (frameNum == 0)
        {
            if (isOpen) {
                setImage(PLAYER);
                isOpen = false;
            } else {
                setImage(PLAYER_OPEN);
                isOpen = true;
            }

            frameNum = changeFrameNum;    // if frameNum reduced from 15 to 0, reset it to 15 again
        }
        return frameNum;
    }

    /**
     * method that checks if the player dead -- lost all 3 lives
     * @return: a boolean value if dead or not dead
     */
    public boolean isDead() {
        return lives == 0;
    }

    /**
     * method that checks if the player reaches win score
     * @param targetScore
     * @return: a boolean value if reached or haven't reached
     */
    public boolean reachedWinScore(int targetScore){
        return scores == targetScore;
    }

    /**
     * reset the lives of player to full (3 lives)
     */
    public void resetLives() {
        this.lives = MAX_LIVES;
    }

    /**
     * reset the scores of player to 0
     */
    public void resetScores() {
        this.scores = 0;
    }

    /**
     * render the hearts based on the lives of the player
     * @param lives
     * @param hearts
     */
    private void renderHearts(ArrayList<Heart> hearts, int lives)
    {
        for (int i=0; i<lives; i++)
        {
            hearts.get(i).render();
        }
    }

    /**
     * render the player's score
     * @param score
     */
    private void renderScore(int score)
    {
        SCORE_FONT.drawString(String.format("SCORE %d", score), SCORE_POINT.x, SCORE_POINT.y);
    }


    /**
     * render the player on screen
     */
    public void render()
    {
        getImage().drawFromTopLeft(getX(), getY(), drawOption);
    }

}
