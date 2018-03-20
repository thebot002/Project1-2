package com.golf2k18.objects;

public class Coordinate 
{
	private int X;
	private int Y;
	private int Z;
	
	public Coordinate(int X, int Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	public Coordinate(int X, int Y, int Z)
	{
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}
	
	public int getX()
	{
		return this.X;
	}
	
	public int getY()
	{
		return this.Y;
	}
	
	public int getZ()
	{
		return this.Z;
	}
}
