import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2023
 *
 * Please enter your name below
 * @ Ruiyi Hao (1133452)
 */

/**
 * ShadowPac is a class that represents the game Shadow Pac.
 * The game contains game entities including: player, enemies(ghosts) and Stationary Entities(EatableStationary
 * and Walls).
 */
public class ShadowPac extends AbstractGame  {

    // general settings
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static int LEVEL_0_COMPLETE_MESSAGE_LAST = 300;    // frame num that the message lasts
    private final static int FRENZY_MODE_LAST_FRAME = 1000;

    // Win Scores
    private final static int LEVEL_0_WIN_SCORE = 300;
    private final static int LEVEL_1_WIN_SCORE = 800;

    // messages, fonts and positions for level instructions
    private final static String INSTRUCTION_1 = "PRESS SPACE TO START";
    private final static String INSTRUCTION_2 = "USE ARROW KEYS TO MOVE";
    private final static String INSTRUCTION_3 = "EAT THE PELLET TO ATTACK";
    private final static String LEVEL_0_WIN_MESSAGE = "LEVEL COMPLETE!";
    private final static String LEVEL_1_WIN_MESSAGE = "WELL DONE!";
    private final static String LOSE_MESSAGE = "GAME OVER!";

    private final Font TITLE_FONT = new Font("res/FSO8BITR.TTF", 64);
    private final static int TITLE_FONT_SIZE = 64;
    private final Font LEVEL_0_INSTRUCTION_FONT = new Font("res/FSO8BITR.TTF",24);
    private final Font LEVEL_1_INSTRUCTION_FONT = new Font("res/FSO8BITR.TTF", 40);

    private final static Point TITLE_POINT = new Point(260, 250);
    private final static Point INSTRUCTION_1_POINT_LEVEL0 = new Point(320, 440);
    private final static Point INSTRUCTION_2_POINT_LEVEL0 = new Point(320, 464);
    private final static Point INSTRUCTION_1_POINT_LEVEL1 = new Point(200, 350);
    private final static Point INSTRUCTION_2_POINT_LEVEL1 = new Point(200, 390);
    private final static Point INSTRUCTION_3_POINT_LEVEL1 = new Point(200, 430);

    // World File
    private final static String WORLD_FILE_LEVEL0 = "res/level0.csv";
    private final static String WORLD_FILE_LEVEL1 = "res/level1.csv";
    private boolean worldFileLevel0Loaded = false;
    private boolean worldFileLevel1Loaded = false;


    // Attributes
    private int levelIndicator;    // an int indicate the current level (0 or 1)
    private boolean level0Start;
    private boolean level0Win;
    private int framesAfterLevel0Complete = 0;
    private boolean cleanUpBeforeLevel1;
    private boolean level1Start;
    private boolean playerWin;
    private boolean gameOver;
    private boolean frenzyStart;
    private int frenzyModeFrameNum = FRENZY_MODE_LAST_FRAME;


    // Game Entities
    private ArrayList<Entity> entities = new ArrayList<>();


    // constructor for the game, define the initial states of the game
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        levelIndicator = 0;    // start with level 0
        level0Start = false;
        level0Win = false;
        cleanUpBeforeLevel1 = false;
        level1Start = false;
        playerWin = false;
        gameOver = false;
        frenzyStart = false;
    }

    /**
     * Method used to read file and create objects (you can change/move
     * this method as you wish).
     */
    private void readCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String  text;

            // iterate through each line of the file
            while ((text = br.readLine()) != null)
            {

                // split each line by comma, get an array of strings of length 3
                String[] cells = text.split(",");

                // assign each element to their label
                String type = cells[0];
                double x = Double.parseDouble(cells[1]);
                double y = Double.parseDouble(cells[2]);

                // then assign the positions of corresponding type of game entity
                if (type.equals("Player"))
                {
                    // add the player
                    entities.add(new Player(x, y));
                }
                else if (type.equals("GhostRed") | type.equals("Ghost"))
                {
                    // add red ghost
                    entities.add(new RedGhost(x, y));
                }
                else if (type.equals("GhostBlue"))
                {
                    // add blue ghost
                    entities.add(new BlueGhost(x, y));
                }
                else if (type.equals("GhostGreen"))
                {
                    // add green ghost
                    entities.add(new GreenGhost(x, y));
                }
                else if (type.equals("GhostPink"))
                {
                    // add pink ghost
                    entities.add(new PinkGhost(x, y));
                }
                else if (type.equals("Wall"))
                {
                    // add the wall
                    entities.add(new Wall(x, y));
                }
                else if (type.equals("Dot"))
                {
                    // add the dot
                    entities.add(new Dot(x, y));
                }
                else if (type.equals("Cherry"))
                {
                    // add the cherry
                    entities.add(new Cherry(x, y));
                }
                else if (type.equals("Pellet"))
                {
                    // add the pellet
                    entities.add(new Pellet(x, y));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        // close the game window
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // render the background
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        if (!level0Start)        // before level-0
        {

            // render start screen for level 0
            renderStartScreen(levelIndicator);

            // assign the game entities by reading their positions from the level 0 world file
            if (!worldFileLevel0Loaded) {
                readCSV(WORLD_FILE_LEVEL0);
                worldFileLevel0Loaded = true;
            }

            // start level 0 when space key was pressed
            if (input.wasPressed(Keys.SPACE)) {
                level0Start = true;
            }

        } else if (level0Start && !level0Win && !gameOver)        // during level-0
        {

            // update the entities accordingly
            updateGameEntities(input);

            // implement win-lose detection
            if (getPlayer().isDead()) {
                gameOver = true;
            }
            if (getPlayer().reachedWinScore(LEVEL_0_WIN_SCORE)) {
                level0Win = true;
            }

        } else if (level0Win && levelIndicator == 0)  // after level-0, hold the level-0 win message for 300 frames
        {
            renderWinMessages(levelIndicator);
            setLevel1();    // set the levelIndicator to level 1

        } else if (levelIndicator == 1 && !level1Start)        // before level-1
        {
            // render start screen for level 1
            renderStartScreen(levelIndicator);

            // clean up all the entities in level-0
            if (!cleanUpBeforeLevel1) {
                cleanUp();
                cleanUpBeforeLevel1 = true;
            }

            // assign the game entities by reading their positions from the level 1 world file
            if (!worldFileLevel1Loaded) {
                readCSV(WORLD_FILE_LEVEL1);
                worldFileLevel1Loaded = true;
            }

            // start level 1 when space key was pressed
            if (input.wasPressed(Keys.SPACE)) {
                level1Start = true;
            }

        } else if (level1Start && !playerWin && !gameOver)        // during level-1
        {

            // update the entities accordingly
            updateGameEntities(input);

            // if frenzy mode start, handle it
            if (frenzyStart)
            {
                handleFrenzy();
            }

            // implement win-lose detection
            if (getPlayer().isDead()) {
                gameOver = true;
            }
            if (getPlayer().reachedWinScore(LEVEL_1_WIN_SCORE)) {
                playerWin = true;
            }

        } else if (playerWin)        // if player win either level-0 or level-1
        {
            renderWinMessages(levelIndicator);    // render win message according to the level

        } else if (gameOver)        // player lose the game
        {
            renderLoseMessages();       // render lose message

        }
    }

    /**
     * get the level indicator of the game
     * @return: int representing the level
     */
    public int getLevelIndicator() {
        return levelIndicator;
    }

    /**
     * get the player of the game
     * @return: Player
     */
    public Player getPlayer()
    {
        // search among all entities
        for (Entity entity: entities)
        {
            if (entity instanceof Player)    // if is an player, return it
            {
                return (Player) entity;
            }
        }
        return null;
    }

    /**
     * Check collision and implement the collision behaviour of the player with other game entities
     * @param input
     * @param player
     */
    public void checkPlayerCollision(Input input, Player player)
    {
        // search among all entities
        for (Entity entity: entities)
        {
            // player collides with the walls
            if (entity instanceof Wall)
            {
                // implement player behaviour when colliding with walls
                if ((player.collideWithEntity(entity))  && player.getPlayerDirection() == player.UP)
                {
                    // reset the player's position to previous position
                    player.resetPosition(player.getPreviousX(), player.getPreviousY());

                    // if collide with the wall when moving up, then player can only move down, left or right
                    if (input.isDown(Keys.DOWN)){player.moveDown();}
                    if (input.isDown(Keys.LEFT)){player.moveLeft();}
                    if (input.isDown(Keys.RIGHT)){player.moveRight();}
                }
                else if ((player.collideWithEntity(entity)) && player.getPlayerDirection() == player.DOWN)
                {
                    // reset the player's position to previous position
                    player.resetPosition(player.getPreviousX(), player.getPreviousY());

                    // if collide with the wall when moving down, then player can only move up, left or right
                    if (input.isDown(Keys.UP)){player.moveUp();}
                    if (input.isDown(Keys.LEFT)){player.moveLeft();}
                    if (input.isDown(Keys.RIGHT)){player.moveRight();}
                }
                else if ((player.collideWithEntity(entity)) && player.getPlayerDirection() == player.LEFT)
                {
                    // reset the player's position to previous position
                    player.resetPosition(player.getPreviousX(), player.getPreviousY());

                    // if collide with the wall when moving left, then player can only move up, down or right
                    if (input.isDown(Keys.UP)){player.moveUp();}
                    if (input.isDown(Keys.DOWN)){player.moveDown();}
                    if (input.isDown(Keys.RIGHT)){player.moveRight();}
                }
                else if ((player.collideWithEntity(entity)) && player.getPlayerDirection() == player.RIGHT)
                {
                    // reset the player's position to previous position
                    player.resetPosition(player.getPreviousX(), player.getPreviousY());

                    // if collide with the wall when moving right, then player can only move up, down or left
                    if (input.isDown(Keys.UP)){player.moveUp();}
                    if (input.isDown(Keys.DOWN)){player.moveDown();}
                    if (input.isDown(Keys.LEFT)){player.moveLeft();}
                }
            }
            else if (entity instanceof Ghost)        // player collides with the ghosts
            {
                if (!frenzyStart)    // if not in frenzy mode, player been attacked by ghosts
                {
                    // implement the behaviour of the player when colliding with ghosts
                    if ((player.collideWithEntity(entity)))
                    {
                        // reset the player to its initial position
                        player.resetPosition(player.getInitialX(), player.getInitialY());

                        // reset the ghost to its initial position
                        ((Ghost) entity).resetPosition(((Ghost) entity).getInitialX(), ((Ghost) entity).getInitialY());

                        // player loses one live, the heart rendering would change according to the live number
                        player.reduceLives();
                    }
                }
                if (frenzyStart)    // if frenzy mode starts, ghosts attacked by player
                {
                    // implement player's behaviour when colliding with ghost
                    if ((player.collideWithEntity(entity)))        // if the ghost is currently active
                    {
                        if (((Ghost) entity).isActive())
                        {
                            // increase the player's score by 30
                            player.incrementScore(((Ghost) entity).FRENZY_POINT_VALUE);

                            // inactivated the ghost (eaten by the player)
                            ((Ghost) entity).setActive(false);
                        }
                    }
                }

            }
            else if (entity instanceof EatableStationary)    // player collides with eatable stationary
            {
                // implement the behaviour of the player when colliding with the currently active
                // eatableStationary
                if (((EatableStationary) entity).isActive() && (player.collideWithEntity(entity)))
                {
                    // increase the score of player by the corresponding point value of the eatables
                    player.incrementScore(((EatableStationary) entity).getPointValue());

                    // inactivate the EatableStationary (been eaten by the player)
                    ((EatableStationary) entity).inactivate();

                    // start the frenzy mode of the game if pellet been eaten
                    if (entity instanceof Pellet)
                    {
                        frenzyStart = true;
                    }
                }
            }
        }
    }

    /**
     * Check the collision of ghost and implement behaviour when it collides with walls
     * @param ghost
     */
    public void checkGhostCollision(Ghost ghost)
    {
        for (Entity entity: entities)
        {
            // if the ghost collides with the walls, update the direction of the ghosts
            if ((entity instanceof Wall) && ghost.collideWithEntity(entity))
            {
                // reset the ghost to previous position
                ghost.resetPosition(ghost.getPreviousX(), ghost.getPreviousY());

                // update direction
                ghost.updateDirection();
            }
        }
    }


    /**
     * set level-1 indicator after lasting the level-0 complete message for 300 frames
     */
    private void setLevel1()
    {
        // last the win message for 300 frames
        if (framesAfterLevel0Complete < LEVEL_0_COMPLETE_MESSAGE_LAST)
        {
            framesAfterLevel0Complete ++;
        }
        else
        {
            levelIndicator = 1;    // set level indicator to level-1
        }
    }

    /**
     * clean up all the entities in the entity list
     */
    private void cleanUp()
    {
        entities.clear();
    }

    /**
     * handle the Frenzy mode of the game, last the frenzy mode for 1000 frames
     */
    private void handleFrenzy()
    {
        frenzyModeFrameNum --;

        // for all entities, set them to frenzy mode
        for (Entity entity: entities)
        {
            entity.setFrenzy();
        }

        if (frenzyModeFrameNum == 0)        // frenzy mode ends
        {
            // for all entities, set them to frenzy mode
            for (Entity entity: entities)
            {
                entity.setBackToNormal();
            }

            // reset the frenzy frame num to 1000
            frenzyModeFrameNum = FRENZY_MODE_LAST_FRAME;

            // set the frenzy start to false
            frenzyStart = false;
        }

    }

    /**
     * The method updates all game entities based on the order: Wall / EatableStationary, Ghost, Player.
     * So when entities overlap each other, Player would at the top layer, the ghosts at next layer, and so on.
     * @param input
     */
    private void updateGameEntities(Input input)
    {
        // Sort the entities list based on their z-index
        Collections.sort(entities, new Comparator<Entity>() {
            @Override
            public int compare(Entity entity1, Entity entity2) {
                return Integer.compare(entity1.getZIndex(), entity2.getZIndex());
            }
        });

        // Render the sorted entities in order
        for (Entity entity : entities) {
            entity.update(input, this);
        }
    }

    /**
     * This function renders the start screen before each level
     * @param levelIndicator
     */
    private void renderStartScreen(int levelIndicator)
    {
        // if before level 0, then render level 0 instructions
        if (levelIndicator == 0)
        {
            TITLE_FONT.drawString(GAME_TITLE, TITLE_POINT.x, TITLE_POINT.y);
            LEVEL_0_INSTRUCTION_FONT.drawString(INSTRUCTION_1, INSTRUCTION_1_POINT_LEVEL0.x,
                    INSTRUCTION_1_POINT_LEVEL0.y);
            LEVEL_0_INSTRUCTION_FONT.drawString(INSTRUCTION_2, INSTRUCTION_2_POINT_LEVEL0.x,
                    INSTRUCTION_2_POINT_LEVEL0.y);
        }

        // if before level 1, then render level 1 instructions
        if (levelIndicator == 1)
        {
            LEVEL_1_INSTRUCTION_FONT.drawString(INSTRUCTION_1, INSTRUCTION_1_POINT_LEVEL1.x,
                    INSTRUCTION_1_POINT_LEVEL1.y);
            LEVEL_1_INSTRUCTION_FONT.drawString(INSTRUCTION_2, INSTRUCTION_2_POINT_LEVEL1.x,
                    INSTRUCTION_2_POINT_LEVEL1.y);
            LEVEL_1_INSTRUCTION_FONT.drawString(INSTRUCTION_3, INSTRUCTION_3_POINT_LEVEL1.x,
                    INSTRUCTION_3_POINT_LEVEL1.y);
        }
    }

    /**
     * render the win message of each level
     * @param levelIndicator
     */
    private void renderWinMessages(int levelIndicator)
    {
        if (levelIndicator == 0)
        {
            TITLE_FONT.drawString(LEVEL_0_WIN_MESSAGE,
                    Window.getWidth()/2.0 - TITLE_FONT.getWidth(LEVEL_0_WIN_MESSAGE)/2.0,
                    Window.getHeight()/2.0 + TITLE_FONT_SIZE/2.0);

        }

        if (levelIndicator == 1)
        {
            TITLE_FONT.drawString(LEVEL_1_WIN_MESSAGE,
                    Window.getWidth()/2.0 - TITLE_FONT.getWidth(LEVEL_1_WIN_MESSAGE)/2.0,
                    Window.getHeight()/2.0 + TITLE_FONT_SIZE/2.0);

        }
    }

    /**
     * render the lose message, same for all levels
     */
    private void renderLoseMessages()
    {
        TITLE_FONT.drawString(LOSE_MESSAGE,
                Window.getWidth()/2.0 - TITLE_FONT.getWidth(LOSE_MESSAGE)/2.0,
                Window.getHeight()/2.0 + TITLE_FONT_SIZE/2.0);

    }

}