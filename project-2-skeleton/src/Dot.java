import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Class Dot is a class that represents all the dots in the game.
 * Parent Class: EatableStationary
 */
public class Dot extends EatableStationary{

    private static final Image DOT = new Image("res/dot.png");
    private static final int POINT_VALUE = 10;


    public Dot(double x, double y) {
        super(x, y, DOT, POINT_VALUE);
    }

}
