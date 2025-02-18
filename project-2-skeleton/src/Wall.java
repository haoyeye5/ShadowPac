import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class Wall is a class that represents all the wall objects in the game.
 * Wall cannot be past through by player or ghosts.
 * Parent Class: Entity
*/
public class Wall extends Entity {

    public static final int ZIndex = 0;

    private static final Image WALL = new Image("res/wall.png");

    public Wall(double x, double y) {
        super(x, y, WALL, ZIndex);
    }

    /**
     * A method that updates the wall (i.e. render the wall on screen)
     * @param input
     * @param gameObject
     */
    @Override
    public void update(Input input, ShadowPac gameObject) {
        render();
    }

    /**
     * set Walls to frenzy mode (do nothing)
     */
    @Override
    public void setFrenzy() {
        // do nothing
    }

    /**
     * set Walls back to normal mode (do nothing)
     */
    @Override
    public void setBackToNormal()
    {
        // do nothing
    }

    /**
     * A method that renders the wall on the screen
     */
    @Override
    public void render() {
        getImage().drawFromTopLeft(getX(), getY());
    }

}
