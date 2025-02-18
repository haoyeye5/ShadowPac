import bagel.*;
import bagel.Image;
import bagel.util.Point;

/**
 * Class Heart is a class that represents the hearts (i.e. the lives of the player).
 * Hearts should be rendered based on player's lives
 */
public class Heart {

    private final Image HEART = new Image("res/heart.png");

    private Image image = HEART;
    private Point point;

    public Heart(Point point) {
        this.point = point;
    }

    /**
     * render the hearts on screen
     */
    public void render() {
        this.image.drawFromTopLeft(point.x, point.y);
    }

}
