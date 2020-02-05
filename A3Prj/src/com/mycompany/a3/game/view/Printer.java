package com.mycompany.a3.game.view;

import com.codename1.charts.models.Point;
import com.mycompany.a3.util.Color;
import com.mycompany.a3.game.model.component.Movable;
import com.mycompany.a3.util.Util;

/*
 * Reusable functions to print values of various classes
 */

public class Printer
{
	/**
	 * @return	Get string of gameObject variable's values: size, color, and location
	 */
	
    public static String printBase(int size, Color color, Point location)
    {
        return "size = " + size
                + "; color = " + color
                + "; location = " + Util.pointToString(location);
    }

    /**
	 * @return	Get string of movable gameObject variable's values: speed and heading
	 */
    
    public static String printMovable(Movable movable)
    {
        return  "speed = " + movable.getSpeed() + "; heading = " + movable.getHeading();
    }
}
