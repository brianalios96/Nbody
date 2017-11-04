package model;

public class Body
{
	private int xPosition;
	private int yPosition;
	private int xVelocity;
	private int yVelocity;
	private int size;

	public int getxPosition()
	{
		return xPosition;
	}

	public void setxPosition(int xPosition)
	{
		this.xPosition = xPosition;
	}

	public int getyPosition()
	{
		return yPosition;
	}

	public void setyPosition(int yPosition)
	{
		this.yPosition = yPosition;
	}

	public int getxVelocity()
	{
		return xVelocity;
	}

	public void setxVelocity(int xVelocity)
	{
		this.xVelocity = xVelocity;
	}

	public int getyVelocity()
	{
		return yVelocity;
	}

	public void setyVelocity(int yVelocity)
	{
		this.yVelocity = yVelocity;
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
		xPosition += xVelocity;
		yPosition += yVelocity;
	}

}