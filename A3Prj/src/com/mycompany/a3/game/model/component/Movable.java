package com.mycompany.a3.game.model.component;

import com.codename1.charts.models.Point;
import com.mycompany.a3.util.Util;
/*
 * A moveable game object.
 */

public class Movable
{
    private Point location;
    private int heading;
    private int speed;

    public Movable(Point location, int heading, int speed)
    {
        this.location = location;
        this.heading = heading;
        this.speed = speed;
    }

    public Point getLocation() { return location; }

    public int getHeading() { return heading; }

    public int getSpeed() { return speed; }

    public void setLocation(Point location) { this.location = Util.screenBoundary(location); }

    public void setHeading(int heading) { this.heading = heading; }

    public void setSpeed(int speed) { this.speed = speed; }
    
   /*
    * move() calculates the movement for a single tick.
    * Moves in straight line at a fixed speed unless it collides with another game object or moves left/right.  
    */

    public void move(int deltaTime)
    {
        double angle = Math.toRadians(90 - heading);

        float deltaX = (float) Math.cos(angle) * speed * deltaTime;
        float deltaY = (float) Math.sin(angle) * speed * deltaTime;

        Point newLocation = new Point(location.getX() + deltaX, location.getY() + deltaY);

        if (!Util.isOnScreen(newLocation))
        {
            if (newLocation.getY() > Util.viewHeight || newLocation.getY() < 0)
            {
                heading += 180;
            }
            else
            {
                heading *= -1;
            }
        }
        setLocation(newLocation);
    }
}
