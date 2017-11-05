package model;

import java.awt.Color;
import java.util.Random;

import view.NBodyGUI;

public class Body
{
	private static final double secondInTimeStep = 0.001;//used to calculate velocity and position
	private static final double GRAVITY = 6.673 * Math.pow(10, -11) / (1000 * 1000); // Newton square-kilometer per square-kilogram
	private static final double MASS = 5.972 * Math.pow(10,24); // kg, mass of earth
	private static final double MAX_VELOCITY = 200;
	
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private int size;

	private Color color;
	private double initialXVelocity;
	private double initialYVelocity;
	
	public Body()
	{
		Random rng = new Random();
		setxVelocity(0);
		setyVelocity(0);
		setcolor(new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255)));
	}
	
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
	
	public void updatePosition(double nextTime)
	{

		xPosition += xVelocity * nextTime;
		yPosition += yVelocity * nextTime;
		
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
	
	public int updateVelocity(Body[] allBodies, double nextTime)
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
				
				boolean collide = false;
				if(distance <= (size + other.size)/2) // size is diameter not radius
				{	
					double initialKineticEnergySquared = square(xVelocity) + square(yVelocity) + square(other.xVelocity) + square(other.yVelocity);
					
//					System.out.println("Collide");
					xVelocity = other.initialXVelocity * square(xDistance) + (other.initialYVelocity - initialYVelocity)*xDistance*yDistance + initialXVelocity*square(yDistance);
					xVelocity /= distanceSquared;
					
					yVelocity = other.initialYVelocity * square(yDistance) + (other.initialXVelocity - initialXVelocity)*xDistance*yDistance + initialYVelocity*square(xDistance);
					yVelocity /= distanceSquared;
					collisions=collisions+1;
					
					distance = (size + other.size)/2;
					distanceSquared = distance*distance;
					
					collide = true;
					
					
					double otherxVelocity = initialXVelocity * square(xDistance) + (initialYVelocity - other.initialYVelocity)*xDistance*yDistance + other.initialXVelocity*square(yDistance);
					otherxVelocity /= distanceSquared;
					
					double otheryVelocity = initialYVelocity * square(yDistance) + (initialXVelocity - other.initialXVelocity)*xDistance*yDistance + other.initialYVelocity*square(xDistance);
					otheryVelocity /= distanceSquared;
					
					double finalKineticEnergySquared = square(xVelocity) + square(yVelocity) + square(otherxVelocity) + square(otheryVelocity);
					
					System.out.println(initialKineticEnergySquared - finalKineticEnergySquared);
				}
				
				double force = GRAVITY * MASS * MASS / distanceSquared; // Newtons
				
				double xForce = force * xDistance / distance; // still Newtons
				double yForce = force * yDistance / distance;
				
				double xAcceleration = xForce / MASS; // m per second-squared
				double yAcceleration = yForce / MASS;

				xVelocity += xAcceleration * (nextTime/1000); // KILOmeter per second
				yVelocity += yAcceleration * (nextTime/1000);
				
				if(collide == false)
				{
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
//				System.out.println(xVelocity + " " + yVelocity);
			}
		}
		return collisions;
	}
	
	public double nextCollisionTime(Body[] allBodies)
	{
		double nextTime = secondInTimeStep;
		
//		for(Body other : allBodies)
//		{
//			if(other != this)
//			{
//				double xVelocityDifference = other.xVelocity - this.xVelocity;
//				double yVelocityDifference = other.yVelocity - this.yVelocity;
//				double xPositionDifference = other.xPosition - this.xPosition;
//				double yPositionDifference = other.yPosition - this.yPosition;
//				
//				double a = square(xVelocityDifference) + square(yVelocityDifference);
//				double b = 2 * (xVelocityDifference*xPositionDifference + yVelocityDifference*yPositionDifference);
//				double c = square(xPositionDifference) + square(yPositionDifference) - square((other.size + this.size)/2);
//				
//				double time1 = ( -b + Math.sqrt(square(b) - 4*a*c) ) / (2*a);
//				double time2 = ( -b - Math.sqrt(square(b) - 4*a*c) ) / (2*a);
//				
//				if(time1 > 0 && time1 < nextTime)
//				{
//					nextTime = time1;
//				}
//				if(time2 > 0 && time2 < nextTime)
//				{
//					nextTime = time2;
//				}
//			}
//		}
		
		return nextTime;
	}
	
	private double square(double input)
	{
		return input * input;
	}

}