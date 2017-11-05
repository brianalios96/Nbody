package model;

public class Body
{
	private static final double GRAVITY = 6.673 * Math.pow(10, -11) / (1000 * 1000); // Newton square-kilometer per square-kilogram
	private static final double MASS = 5.972 * Math.pow(10,24); // kg, mass of earth
	
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private int size;

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
	}
	public double getxVelocity()
	{
		return xVelocity;
	}

	public void setyVelocity(double yVelocity)
	{
		this.yVelocity = yVelocity;
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
	}
	
	public void updateVelocity(Body[] allBodies, double seconds)
	{
		for(Body body: allBodies)
		{
			if(body != this)
			{
				double xDistance = body.xPosition - xPosition; // kilometers
				double yDistance = body.yPosition - yPosition;
				
				double distance = square(xDistance) + square(yDistance); // kilometers-squared
				
				double force = GRAVITY * MASS * MASS / square(distance); // Newtons
				
				double xForce = force * xDistance / distance; // still Newtons
				double yForce = force * yDistance / distance;
				
				double xAcceleration = xForce / MASS; // m per second-squared
				double yAcceleration = yForce / MASS;

				xVelocity += xAcceleration * (seconds / 1000); // KILOmeter per second
				yVelocity += yAcceleration * (seconds / 1000);
			}
		}
	}
	
	private double square(double input)
	{
		return Math.pow(input, 2);
	}

}