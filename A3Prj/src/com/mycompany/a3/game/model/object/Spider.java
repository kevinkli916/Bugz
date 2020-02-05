package com.mycompany.a3.game.model.object;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.game.model.GameObject;
import com.mycompany.a3.game.model.IMovable;
import com.mycompany.a3.game.model.component.Movable;
import com.mycompany.a3.game.view.Printer;
import com.mycompany.a3.game.view.Draw;
import com.mycompany.a3.util.Color;
import com.mycompany.a3.util.Util;

/*
 * Represents a spider(enemy) in the game drawn as a triangle outlined red
 * 
 * When the spider collides with the ant, the ant takes damage
 */

public class Spider implements GameObject, IMovable
{
	private Color spiderColor;
	private int size;

	/*
	 * Generates spider at a random location and moves spider in random directions
	 */
	
	private Movable movable = new Movable(Util.randomLocation(), Util.generateRandomInt(0, 360), 10);

	/*
	 * Spider constructor 
	 */
	
	public Spider()
	{
		spiderColor = Color.rgb(150, 0, 0);
		
		movable.setLocation(Util.randomLocation());
		
		movable.setHeading(Util.generateRandomInt(0, 359));
		
		movable.setSpeed(Util.generateRandomInt(5, 10));

		size = Util.generateRandomInt(50, 80);
	}
	
	/*
	 * Spider changes heading from -5 to +5 so that it will move in curves rather than a straight line
	 */
	
	/**
	 * @param deltaTime		Amount of time units used to calculate how far to move
	 */
	
	@Override
	public void move(int deltaTime)
	{
		movable.setHeading(movable.getHeading() + Util.generateRandomInt(-5, 5));
		
		movable.move(deltaTime);
		
		if (!Util.isOnScreen(movable.getLocation()))
		{
			movable.setLocation(Util.screenBoundary(movable.getLocation()));

			System.out.println("Heading: " + movable.getHeading());
			
			movable.setHeading(movable.getHeading() - 180);
			System.out.println("New heading: " + movable.getHeading());
		}
	}

	/**
	 * @return	Get string of Spider variable's values: size, color, location, speed, max speed, health, and food consumption rate
	 */
	
	@Override
	public String toString()
	{
		return "Spider: " + Printer.printBase(size, spiderColor, movable.getLocation()) + Printer.printMovable(movable);
	}

	/*
	 * Spider is in the shape of an unfilled triangle with a red outline
	 */
	
	@Override
	public void draw(Graphics g, Point containerOrigin)
	{
		Draw.unfilledTriangle(g, movable.getLocation(), containerOrigin, Math.round(size * 1.3f), spiderColor);
	}

	/**
	 * @return	 Get center point(location) of Spider as a Point
	 */
	
	@Override
	public Point getCenterPoint()
	{
		return movable.getLocation();
	}

	/**
	 * @return	Get collision radius(size) of Spider
	 */
	
	@Override
	public int getCollisionRadius()
	{
		return size;
	}
}
