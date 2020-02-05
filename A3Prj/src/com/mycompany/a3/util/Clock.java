package com.mycompany.a3.util;

/*
 * Clock keeps track of time
 */

public class Clock
{
	private int time = 0;
	
	/**
	 * 
	 * @return	Get time elapsed since game started. Time does not reset upon death
	 */
	
	public int getTime()
	{
		return time;
	}
	
	public void tick()
	{
		time += 1;
	}
}
