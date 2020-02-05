package com.mycompany.a3.game.model;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface IDrawable
{
    /*
     * Interface to draw game objects using CDO "graphics" class
     */
	void draw(Graphics g, Point containerOrigin);
}
