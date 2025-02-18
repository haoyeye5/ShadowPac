import bagel.Image;
import bagel.Input;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Class Cherry is a class that represents all the cherries in the game.
 * Parent Class: EatableStationary
 */
public class Cherry extends EatableStationary{

    private static final Image CHERRY = new Image("res/cherry.png");
    public final static int POINT_VALUE = 20;


    public Cherry(double x, double y) {
        super(x, y, CHERRY, POINT_VALUE);
    }

}
