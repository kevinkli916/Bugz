package com.mycompany.a3.util;

import com.codename1.charts.util.ColorUtil;

/*
 * Functions to get various colors
 */

public class Color
{
	private int color;
	
	private Color(int color)
	{
		this.color = color;
	}
	
	public static final Color BLACK = new Color(ColorUtil.BLACK);
	public static final Color BLUE = new Color(ColorUtil.BLUE);
	public static final Color CYAN = new Color(ColorUtil.CYAN);
	public static final Color GRAY = new Color(ColorUtil.GRAY);
	public static final Color GREEN = new Color(ColorUtil.GREEN);
	public static final Color LTGRAY = new Color(ColorUtil.LTGRAY);
	public static final Color MAGENTA = new Color(ColorUtil.MAGENTA);
	public static final Color WHITE = new Color(ColorUtil.WHITE);
	public static final Color YELLOW = new Color(ColorUtil.YELLOW);
	
	/**
	 * 
	 * @param r	Integer value for red from 0 - 255
	 * @param g	Integer value for green from 0 - 255
	 * @param b	Integer value for blue from 0 - 255
	 * @return	Color given red, green, and blue values
	 */
	
	public static Color rgb(int r, int g, int b)
	{
		return new Color(ColorUtil.rgb(r, g, b));
	}
	
	/**
	 * 
	 * @return	Integer value for the color "red"
	 */
	
	public int red()
	{
		return ColorUtil.red(color);
	}
	
	/**
	 * 
	 * @return	Integer value for the color "green"
	 */
	
	public int green()
	{
		return ColorUtil.green(color);
	}
	
	/**
	 * 
	 * @return	Integer value for the color "blue"
	 */
	
	public int blue()
	{
		return ColorUtil.blue(color);
	}

	public int toInt() { return color; }

	/**
	 * 
	 * @return	Gets string of the hexadecimal value of the particular color
	 */
	
	@Override
	public String toString()
	{
		return "#" + Util.hexToString(red())
				   + Util.hexToString(green())
				   + Util.hexToString(blue());
	}
}
