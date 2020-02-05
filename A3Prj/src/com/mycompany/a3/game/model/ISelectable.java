package com.mycompany.a3.game.model;

import com.codename1.charts.models.Point;

public interface ISelectable
{
	/*
	 * Interface for selectable game objects
	 */
    void select();
    void deselect();
    void setLocation(Point location);
}
