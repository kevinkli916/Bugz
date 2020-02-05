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
 * Represents a food station in the game drawn as a yellow square
 * 
 *  Food stations replenish food levels upon the ant colliding
 */

public class FoodStation implements GameObject, ISelectable
{
	private  Color foodStationColor = Color.YELLOW;
	private int minCapacity = 200;
	private int maxCapacity = 500;
	private Color color;
	private Point location;
	private int size;
	private int capacity;
	private boolean selected = false;
	
	/*
	 * FoodStation constructor
	 * 
	 * Instantiates a food station at a random location with a random food capacity
	 * 
	 * The size is relative to the capacity
	 */
	
	public FoodStation()
	{
		location = Util.randomLocation();
		capacity = Util.generateRandomInt(minCapacity, maxCapacity);
		size = capacity / 10 + 20;
		this.color = foodStationColor;
	}
	
	/**
	 * 
	 * @return	Get FoodStation's capacity (food level that it provides Ant upon collision)
	 */
	
	public int getCapacity()
	{
		return capacity;
	}
	
	/**
	 * 
	 * @param capacity	Set FoodStation's capacity(food level that it provides Ant upon collision) to specified capacity
	 */
	
	public void setCapacity(int capacity)
	{
		this.capacity = Math.max(capacity, 0);
	}
	
	/**
	 * @return	Get string of FoodStation variable's values: size, color, location, capacity
	 */
	
	@Override
	public String toString()
	{
		return "Food Station: " + Printer.printBase(size, color, location) + ", capacity = " + capacity;
	}

	/*
	 * Food stations are drawn in the shape of a square
	 */
	
	@Override
	public void draw(Graphics g, Point containerOrigin)
	{
		if (selected)
		{
			float root2 = 1.414213562f;
	        int sideLength = Math.round(size * root2);
	        int x = Math.round(containerOrigin.getX() + location.getX() - sideLength / 2f);
	        int y = Math.round(containerOrigin.getY() + location.getY() - sideLength / 2f);
	        g.setColor(color.toInt());
	        g.drawRect(x, y,  sideLength, sideLength);
		}
		else
		{
			float root2 = 1.414213562f;
	        int sideLength = Math.round(size * root2);
	        int x = Math.round(containerOrigin.getX() + location.getX() - sideLength / 2f);
	        int y = Math.round(containerOrigin.getY() + location.getY() - sideLength / 2f);
	        g.setColor(color.toInt());
	        g.fillRect(x, y, sideLength, sideLength);
		}
		float xAlign = capacity == 0 ? -10 : -27;
		float yAlign = -23;
		Draw.text(String.valueOf(capacity), g, location, containerOrigin, xAlign, yAlign, Color.BLACK);
	}

	/**
	 * @return	Gets center point(location) of FoodStation
	 */
	
	@Override
	public Point getCenterPoint()
	{
		return location;
	}

	/**
	 * @return 	Gets collision radius(size) of FoodStation
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
	 * @param location	Set location of FoodStation to specified location
	 */
	
	@Override
	public void setLocation(Point location) 
	{ 
		this.location = location; 
	}
}
