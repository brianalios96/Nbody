package model;

import java.awt.Color;

import view.NBodyGUI;

public class Body
{
	private static final double secondInTimeStep = 0.1;//used to calculate velocity and position
	private static final double GRAVITY = 6.673 * Math.pow(10, -11) / (1000 * 1000); // Newton square-kilometer per square-kilogram
	private static final double MASS = 5.972 * Math.pow(10,24); // kg, mass of earth
	private static final double MAX_VELOCITY = 10;
	
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private int size;

	private Color color;
	private double initialXVelocity;
	private double initialYVelocity;
	
	public void setcolor(Color color)
	{
		this.color= color;
	}
	
	public Color getcolor()
	{
		return color;
	}
	
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
	
	public void updatePosition()
	{
		xPosition += xVelocity * secondInTimeStep;
		yPosition += yVelocity * secondInTimeStep;
		
		initialXVelocity = xVelocity;
		initialYVelocity = yVelocity;
		
		if(xPosition > NBodyGUI.GUIsize)
		{
			xPosition %= NBodyGUI.GUIsize;
		}
		if(xPosition < 0)
		{
			xPosition %= NBodyGUI.GUIsize;
			xPosition += NBodyGUI.GUIsize;
		}
		
		if(yPosition > NBodyGUI.GUIsize)
		{
			yPosition %= NBodyGUI.GUIsize;
		}
		if(yPosition < 0)
		{
			yPosition %= NBodyGUI.GUIsize;
			yPosition += NBodyGUI.GUIsize;
		}
	}
	
	public int updateVelocity(Body[] allBodies)
	{
		int collisions=0;
		for(Body other: allBodies)
		{
			if(other != this)
			{
				double xDistance = other.xPosition - xPosition; // kilometers
				double yDistance = other.yPosition - yPosition;
				
				double distanceSquared = square(xDistance) + square(yDistance); // kilometers-squared
				double distance = Math.sqrt(distanceSquared);
				
				if(distance <= (size + other.size)/2) // size is diameter not radius
				{					
//					System.out.println("Collide");
					xVelocity = other.initialXVelocity * square(xDistance) + (other.initialYVelocity - initialYVelocity)*xDistance*yDistance + initialXVelocity*square(yDistance);
					xVelocity /= distanceSquared;
					
					yVelocity = other.initialYVelocity * square(yDistance) + (other.initialXVelocity - initialXVelocity)*xDistance*yDistance + initialYVelocity*square(xDistance);
					yVelocity /= distanceSquared;
					collisions=collisions+1;
					
					distance = (size + other.size)/2;
					distanceSquared = distance*distance;
				}
				
				double force = GRAVITY * MASS * MASS / distanceSquared; // Newtons
				
				double xForce = force * xDistance / distance; // still Newtons
				double yForce = force * yDistance / distance;
				
				double xAcceleration = xForce / MASS; // m per second-squared
				double yAcceleration = yForce / MASS;

				xVelocity += xAcceleration * (secondInTimeStep/1000); // KILOmeter per second
				yVelocity += yAcceleration * (secondInTimeStep/1000);
				
				if(xVelocity > MAX_VELOCITY)
				{
					xVelocity = MAX_VELOCITY;
				}
				if(xVelocity < -MAX_VELOCITY)
				{
					xVelocity = -MAX_VELOCITY;
				}
				
				if(yVelocity > MAX_VELOCITY)
				{
					yVelocity = MAX_VELOCITY;
				}
				if(yVelocity < -MAX_VELOCITY)
				{
					yVelocity = -MAX_VELOCITY;
				}
			}
		}
		return collisions;
	}
	
	private double square(double input)
	{
		return input * input;
	}

}