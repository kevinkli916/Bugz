package com.mycompany.a3.game.view;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.game.model.GameObject;
import com.mycompany.a3.game.model.object.GameWorld;
import com.mycompany.a3.game.model.ISelectable;
import com.mycompany.a3.game.model.IObserver;

import java.util.List;

/*
 * Container for the gameWorld map 
 * 
 * Handles selectability when playing/paused
 */

public class MapView extends Container implements IObserver<GameWorld>
{
    private GameWorld world;
    private ISelectable selectedObject;

    public MapView(GameWorld world)
    {
        getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.rgb(146, 0, 0)));
        this.world = world;
    }

    @Override
    public void update(GameWorld world)
    {
        world.showMap();
        
        if (selectedObject != null)
        {
            selectedObject.deselect();
            selectedObject = null;
        }
        repaint();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        world.drawAll(g, new Point(getX(), getY()));
    }

    /*
     * Simulates touch interaction at specified location
     */
    
    /**
     * @param x 	Horizontal value of the point pressed
     * @param y		Vertical value of the point pressed
     */
    
    @Override
    public void pointerPressed(int x, int y)
    {
        GameWorld world = GameWorld.getInstance();

        if (!world.paused()) return;

        Point clickLocation = new Point(x - getAbsoluteX(), y - getAbsoluteY());

        if (selectedObject != null)
        {
            if (world.positionModeEnabled()) selectedObject.setLocation(clickLocation);
            selectedObject.deselect();
            selectedObject = null;
            repaint();
            if (world.positionModeEnabled()) return;
        }

        List<GameObject> selectedObjects = world.getObjectsAtPoint(clickLocation);

        for (GameObject object : selectedObjects)
        {
            if (object instanceof ISelectable)
            {
                selectedObject = (ISelectable) object;
                selectedObject.select();
                repaint();
                return;
            }
        }
    }
}
