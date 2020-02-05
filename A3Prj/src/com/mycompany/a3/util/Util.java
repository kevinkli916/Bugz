package com.mycompany.a3.util;

import java.util.Random;

import com.codename1.charts.models.Point;

/*
 * Various functions providing reusable code throughout the project
 */

public class Util
{
	public static float viewWidth;
	public static float viewHeight;

	/**
	 * 
	 * @return	Get point at the middle of the screen
	 */
	
	public static Point viewCenter()
	{
		return new Point(viewWidth / 2, viewHeight / 2);
	}

	/*
	 * Guarantees the target number is within the range
	 */
	
	/**
	 * 
	 * @param num 	Number to test if within range
	 * @param min 	Minimum value for range
	 * @param max 	Maximum value for range
	 * @return	Number within given min and max range. If it is larger than the range, number is set to max. 
	 * 			If it is smaller than the range, number is set to min
	 */
	
	public static float boundary(float num, float min, float max)
	{
		num = Math.max(num, min);
		return Math.min(num, max);
	}
	
	/*
	 * Guarantees the target number is within the screen
	 */
	
	/**
	 * 
	 * @param point		Point to test if within screen
	 * @return	Point within the screen. If x and/or y is not within screen, it will clamp to the screen
	 */
	
	public static Point screenBoundary(Point point)
	{
		float x = Util.boundary(point.getX(), 0, viewWidth);
		float y = Util.boundary(point.getY(), 0, viewHeight);
		return new Point(x, y);
	}

	/*
	 * Finds distance ^ 2 given two points
	 */
	
	/**
	 * 
	 * @param a 	First point
	 * @param b		Second point
	 * @return Distance squared derived from the distance formula: d^2 = (x2 - x1)^2 + (y2 - y1)^2
	 */
	
	public static float distanceSquared(Point a, Point b)
	{
		float xDiff = a.getX() - b.getX();
		float yDiff = a.getY() - b.getY();
		return xDiff * xDiff + yDiff * yDiff;
	}

	/*
	 * Converts hexadecimal to string
	 */
	
	/**
	 * 
	 * @param num	Integer hexadecimal value to convert to string
	 * @return If argument was not in integer hexadecimal format, then exception is thrown. Else converted to string
	 */
	
	public static String hexToString(int num)
	{
		if (num < 0 || num > 255)
		{
			throw new IllegalArgumentException();
		}
		
		return Integer.toHexString(0x100 | num).substring(1).toUpperCase();
	}
	
	/*
	 * Checks to see if point is within the screen
	 */
	
	/**
	 * 
	 * @param point		Point to test if on screen
	 * @return True if point is on screen. False if point is not on screen
	 */
	
	public static boolean isOnScreen(Point point)
	{
		return point.getX() >= 0 && point.getX() <= viewWidth &&
			   point.getY() >= 0 && point.getY() <= viewHeight;
	}
	
	/*
	 * Converts Point class to string
	 */
	
	/**
	 * 
	 * @param point		Point to convert to a string
	 * @return	String representing a point in the format: (X,Y)
	 */
	
	public static String pointToString(Point point)
	{
		return "(" + point.getX() + ", " + point.getY() + ")";
	}
	
	/*
	 * Generate random float given a range
	 */
	
	/**
	 * 
	 * @param lower		Min value of generated float
	 * @param upper		Max value of generated float
	 * @return	Float within the given range
	 */
	
	public static float generateFloatInRange(float lower, float upper) 
	{
		return (float) new Random().nextDouble() * (upper - lower) + lower;
	}
	
	/*
	 * Generate random int given a range
	 */
	
	/**
	 * 
	 * @param lower		Min value of generated int
	 * @param upper		Max value of generated int
	 * @return	Int within the given range
	 */
	
	public static int generateRandomInt(int lower, int upper)
	{
		return new Random().nextInt(upper - lower) + lower;
	}

	/*
	 * Generate random point within the screen 
	 */
	
	/**
	 * 
	 * @return	Point within the screen
	 */
	
	public static Point randomLocation()
	{
		return new Point(generateFloatInRange(0, viewWidth), generateFloatInRange(0, viewHeight));
	}
}
