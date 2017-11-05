package model;

import view.NBodyGUI;

public class Body
{
	private static final double GRAVITY = 6.673 * Math.pow(10, -11) / (1000 * 1000); // Newton square-kilometer per square-kilogram
	private static final double MASS = 5.972 * Math.pow(10,24); // kg, mass of earth
	
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private int size;

	private double initialXVelocity;
	private double initialYVelocity;
	
	public int getxPosition()
	{
		return (int) xPosition;
	}

	public void setxPosition(int xPosition)
	{
		this.xPosition = xPosition;
	}

	public int getyPosition()
	{
		return (int) yPosition;
	}

	public void setyPosition(int yPosition)
	{
		this.yPosition = yPosition;
	}

	public void setxVelocity(double xVelocity)
	{
		this.xVelocity = xVelocity;
		initialXVelocity = xVelocity;
	}
	public double getxVelocity()
	{
		return xVelocity;
	}

	public void setyVelocity(double yVelocity)
	{
		this.yVelocity = yVelocity;
		initialYVelocity = yVelocity;
	}
	
	public double getyVelocity()
	{
		return yVelocity;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}
	
	public void updatePosition(double seconds)
	{
		xPosition += xVelocity * seconds;
		yPosition += yVelocity * seconds;
		
		initialXVelocity = xVelocity;
		initialYVelocity = yVelocity;
		
		if(xPosition > NBodyGUI.GUIsize)
		{
			xPosition -= NBodyGUI.GUIsize;
		}
		else if(xPosition < 0)
		{
			xPosition += NBodyGUI.GUIsize;
		}
		
		if(yPosition > NBodyGUI.GUIsize)
		{
			yPosition -= NBodyGUI.GUIsize;
		}
		else if(yPosition < 0)
		{
			yPosition += NBodyGUI.GUIsize;
		}
	}
	
	public int updateVelocity(Body[] allBodies, double seconds)
	{
		int collisions=0;
		for(Body other: allBodies)
		{
			if(other != this)
			{
				double xDistance = other.xPosition - xPosition; // kilometers
				double yDistance = other.yPosition - yPosition;
				
				double distanceSquared = square(xDistance) + square(yDistance); // kilometers-squared
				
				if(distanceSquared <= size*size) // size is diameter not radius
				{					
//					System.out.println("Collide");
					xVelocity = other.initialXVelocity * square(xDistance) + (other.initialYVelocity - initialYVelocity)*xDistance*yDistance + initialXVelocity*square(yDistance);
					xVelocity /= distanceSquared;
					
					yVelocity = other.initialYVelocity * square(yDistance) + (other.initialXVelocity - initialXVelocity)*xDistance*yDistance + initialYVelocity*square(xDistance);
					yVelocity /= distanceSquared;
					collisions=collisions+1;
				}
				
				double force = GRAVITY * MASS * MASS / square(distanceSquared); // Newtons
				
				double xForce = force * xDistance / distanceSquared; // still Newtons
				double yForce = force * yDistance / distanceSquared;
				
				double xAcceleration = xForce / MASS; // m per second-squared
				double yAcceleration = yForce / MASS;

				xVelocity += xAcceleration * (seconds / 1000); // KILOmeter per second
				yVelocity += yAcceleration * (seconds / 1000);
			}
		}
		return collisions;
	}
	
	private double square(double input)
	{
		return input * input;
	}

}