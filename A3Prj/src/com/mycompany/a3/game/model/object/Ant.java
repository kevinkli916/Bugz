package com.mycompany.a3.game.model.object;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.game.model.*;
import com.mycompany.a3.game.model.component.Collider;
import com.mycompany.a3.game.model.component.Movable;
import com.mycompany.a3.game.view.Printer;
import com.mycompany.a3.util.Color;

/*
 * Represents the ant in the game drawn as a red circle
 * 
 * The ant is player controlled
 */

public class Ant implements GameObject, IMovable
{
	private int damage = 5;
	private int size = 30;
	private int foodConsumption = 1;
	private int maxSpeed = 10;
	private int initialFood = 500;
	private int initialHeading = 0;
	private int initialHealth = 10;
	private int initialSpeed = 5;
	private Color antColor = Color.GREEN;

	private Movable movable;
	private Collider collider = new Collider(this);
	private Color color;
	private int foodLevel = initialFood;
	private int healthLevel = initialHealth;
	private int lastFlagReached;
	private int speedLimit;

	/*
	 * Ant constructor
	 */
	
	/**
	 * 
	 * @param location	The location where Ant spawns
	 */
	
	public Ant(Point location)
	{
		movable = new Movable(location, initialHeading, initialSpeed);
		computeSpeedLimit();
		color = antColor;
		lastFlagReached = 1;
	}
	
	/*
	 * Ant's color is based off of health.
	 * The less health, the lighter the color.
	 */
	
	private void computeColor()
	{
		color = Color.rgb(healthLevel * 20, 0, 0);
	}
	
	private void computeSpeed()
	{
		setSpeed(movable.getSpeed());
	}
	
	/*
	 * Ant's max speed depends on health.
	 * Less health means slower max speed.
	 */
	
	private void computeSpeedLimit()
	{
		float speedLimitModifier = ((float) 1 / initialHealth) * healthLevel;
		
		speedLimit = Math.round(speedLimitModifier * maxSpeed);
		
		speedLimit = Math.min(speedLimit, maxSpeed);
	}

	/*
	 * Ant's movement costs food.
	 * If food reaches 0, then ant loses a life.
	 */
	
	public void consumeFood()
	{
		foodLevel -= foodConsumption;
		
		foodLevel = Math.max(foodLevel, 0);
	}

	/**
	 * 
	 * @return	Get food level of the Ant(player)
	 */
	
	public int getFood()
	{
		return foodLevel;
	}
	
	/**
	 * 
	 * @return	Get heading(direction) of the Ant(player)
	 */

	public int getHeading()
	{
		return movable.getHeading();
	}

	/**
	 * 
	 * @return	Get health of the Ant(player)
	 */
	
	public int getHealth()
	{
		return healthLevel;
	}
	
	/*
	 * Flags are reached in a consecutive order, 1, 2, 3... etc.
	 */
	
	/**
	 * 
	 * @return	Get the last flag reached by the ant. Ant spawns on flag 1, so lastFlagReached = 1 by default
	 */
	
	public int getLastFlagReached()
	{
		return lastFlagReached;
	}

	/**
	 * 
	 * @return	Get speed of Ant(Player)
	 */
	
	public int getSpeed()
	{
		return movable.getSpeed();
	}

	/*
	 * If current target flag is reached, change target to next flag
	 */
	
	public void hitNextFlag()
	{
		lastFlagReached++;
	}
	
	public void hitSpider()
	{
		setHealth(healthLevel - damage);
	}
	
	/**
	 * 
	 * @param food	Set Ant's(player's) foodLevel to food
	 */
	
	public void setFood(int food)
	{
		this.foodLevel = Math.max(food, 0);
	}

	/**
	 * 
	 * @param heading	Set Ant's(player's) heading
	 */
	
	public void setHeading(int heading)
	{
		movable.setHeading(heading);
	}
	
	/*
	 * Change in health changes color, max speed, and speed if current speed is faster than max speed after taking damage. 
	 */
	
	/**
	 * 
	 * @param health	Set Ant's(player's) health level to health
	 */
	
	private void setHealth(int health)
	{
		this.healthLevel = Math.max(health, 0);
		
		computeSpeedLimit();
		computeSpeed();
		computeColor();
	}
	
	/**
	 * 
	 * @param speed		Set Ant's(player's) speed
	 */
	
	public void setSpeed(int speed)
	{
		speed = Math.max(speed, 0);

		movable.setSpeed(Math.min(speed, speedLimit));
	}
	
	/**
	 * @return	Get string of Ant variable's values: size, color, location, speed, max speed, health, and food consumption rate
	 */
	
	@Override
	public String toString()
	{
		return "Ant: "
				+ Printer.printBase(size, color, movable.getLocation())
				+ Printer.printMovable(movable)
				+ ", speedLimit = " + speedLimit
				+ ", maxSpeed = " + maxSpeed
				+ ", health = " + healthLevel
				+ ", foodConsumptionRate = " + foodConsumption;
	}
	
	/**
	 * @param deltaTime		Amount of time units used to calculate how far to move
	 */
	
	@Override
	public void move(int deltaTime)
	{
		movable.move(deltaTime);
	}

	/*
	 * Ant is draw as a circle
	 */
	
	@Override
	public void draw(Graphics g, Point containerOrigin)
	{	
		int x = Math.round(containerOrigin.getX() + movable.getLocation().getX() - size);
        int y = Math.round(containerOrigin.getY() + movable.getLocation().getY() - size);
        g.setColor(color.toInt());
        g.fillArc(x, y, size * 2, size * 2, 0, 360);
	}
	
	public boolean collidesWith(GameObject gameObject)
	{
		return collider.collidesWith(gameObject);
	}

	/**
	 * @return	 Get center point(location) of Ant as a Point
	 */
	
	@Override
	public Point getCenterPoint()
	{
		return movable.getLocation();
	}

	/*
	 * Collision radius is based off of size
	 */
	
	/**
	 * @return	Get collision radius(size) of Ant
	 */
	
	@Override
	public int getCollisionRadius()
	{
		return size;
	}

	/*
	 * Collision handling of Ant
	 */
	
	public void handleCollision(ICollider gameObject, GameWorld world)
	{
		if (gameObject instanceof Flag)
		{
			world.antHitFlag((Flag) gameObject);
		} else if (gameObject instanceof FoodStation)
		{
			world.antHitFoodStation((FoodStation) gameObject);
		} else if (gameObject instanceof Spider)
		{
			world.antHitSpider();
		}
	}
}
