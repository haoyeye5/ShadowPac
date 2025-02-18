import bagel.Image;
import bagel.Input;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Class Pellet is a class that represents all the pellets in the game.
 * Parent Class: EatableStationary
 */
public class Pellet extends EatableStationary{

    private static final Image PELLET = new Image("res/pellet.png");
    private static final int POINT_VALUE = 0;


    public Pellet(double x, double y) {
        super(x, y, PELLET, POINT_VALUE);
    }

}
