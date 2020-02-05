package com.mycompany.a3.game.model.object;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.game.model.GameObject;
import com.mycompany.a3.game.model.ISelectable;
import com.mycompany.a3.game.view.Printer;
import com.mycompany.a3.game.view.Draw;
import com.mycompany.a3.util.Color;
import com.mycompany.a3.util.Util;

/*
 * Represents flags in the game draw as a blue triangle
 * 
 * The ant(player) wins by collecting all flags in sequential order
 */

public class Flag implements GameObject, ISelectable
{
	private int size = 30;
	private Color flagColor = Color.BLUE;

	private Color color;
	private Point location;
	private int sequenceNumber;
	private boolean selected = false;
	
	/*
	 * Flag constructor
	 * 
	 * Flags are instantiated with a sequence number for the ant (player) to collect in sequential order 
	 */
	
	public Flag(int sequenceNumber, Point location)
	{
		this.location = Util.screenBoundary(location);
		this.color = flagColor;
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return	Get string of Flag variable's values: size, color, location, sequence number
	 */
	
	@Override
	public String toString()
	{
		return "Flag: " + Printer.printBase(size, color, location)
		+ ", sequenceNumber = " + sequenceNumber;
	}

	/*
	 * Flags are in the shape of a triangle
	 */
	
	@Override
	public void draw(Graphics g, Point containerOrigin)
	{
		int drawSize =  Math.round(size * 1.3f);
		float xAlign = 0 - drawSize / 4f;
		float yAlign = 0 - drawSize / 2f;
		Color textColor;
		if (selected)
		{
			Draw.unfilledTriangle(g, location, containerOrigin, drawSize, color);
			textColor = Color.BLACK;
		}
		else
		{
			Draw.filledTriangle(g, location, containerOrigin, drawSize, color);
			textColor = Color.WHITE;
		}
		Draw.text(String.valueOf(sequenceNumber), g, location, containerOrigin, xAlign, yAlign, textColor);
	}

	/**
	 * 
	 * @return	Gets sequence number of Flag to determine order that Ant will collect flags
	 */
	
	public int getSequenceNumber() { 
		return sequenceNumber; 
	}

	/**
	 * @return	Gets center point(location) of Flag
	 */
	
	@Override
	public Point getCenterPoint()
	{
		return location;
	}

	/**
	 * @return 	Gets collision radius(size) of Flag
	 */
	
	@Override
	public int getCollisionRadius()
	{
		return size;
	}

	@Override
	public void select()
	{
		selected = true;
	}

	@Override
	public void deselect()
	{
		selected = false;
	}

	/**
	 * @param location	Set location of Flag to specified location
	 */
	
	@Override
	public void setLocation(Point location) { 
		this.location = location; 
		}
}
