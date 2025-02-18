import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

/**
 * Class Entity is a class that represents all entities in the game, including:
 * Player, Ghosts, EatableStationary and walls.
 * Abstract Class
 * Child Classes: Player, Ghost, EatableStationary, Wall
 */
public abstract class Entity {

    private double x;    // the x coordinate of the entity
    private double y;    // the y coordinate of the entity
    private Image image;    // the image representing the entity that would be rendered on screen
    private int ZIndex;    // z-index, which determines the rendering order of the entity

    public Entity(double x, double y, Image image, int ZIndex) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.ZIndex = ZIndex;
    }

    // Getter
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public Image getImage() {
        return image;
    }
    public int getZIndex() {
        return ZIndex;
    }

    // Setter
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * The method Update is an abstract method that used to update entities' states, including:
     * move the entity according,
     * implement collision check on the entity and perform corresponding actions,
     * render the entity on the screen.
     * @param input
     * @param gameObject
     */
    public abstract void update(Input input, ShadowPac gameObject);

    /**
     * Check collision between the entities
     * @param entity
     * @return: a boolean value if this entity collides with the entity to check
     */
    public boolean collideWithEntity(Entity entity)
    {
        // create a rectangle representing this entity
        Rectangle thisEntityRect = this.createRect();

        // create a rectangle representing the entity to check
        Rectangle otherEntityRect = entity.createRect();

        // check if player rectangle intersect with the entity rectangle
        if (otherEntityRect.intersects(thisEntityRect))
        {
            return true;
        }

        return false;
    }

    /**
     * set the entity to Frenzy mode, an abstract method needed to be implemented
     */
    public abstract void setFrenzy();

    /**
     * set the entity back to normal mode, an abstract method needed to be implemented
     */
    public abstract void setBackToNormal();

    /**
     * create a Rectangle object for the entity
     * @return a rectangle object of the entity
     */
    public Rectangle createRect()
    {
        double Height = this.image.getHeight();
        double Width = this.image.getWidth();
        Point entityPoint = new Point(this.x, this.y);

        return new Rectangle(entityPoint, Width, Height);
    }

    /**
     * An abstract class needed to be implemented,
     * render the entity on screen
     */
    public abstract void render();
}
