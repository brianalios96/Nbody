import java.util.Random;

public class NBody {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int workers=0;//, 1 to 32. This argument will be ignored by the sequential solution.
		int bodies=2;
		int size=1;// of each body.
		int timeSteps=0;//number of time steps
		
		//TODO change for command line arguments
		
		Body allBodies[] = new Body[bodies];
		for(int i = 0; i < allBodies.length; i++)
		{
			allBodies[i] = new Body();
		}
//		Random rng = new Random();
		allBodies[0].setxPosition(1);
		allBodies[0].setyPosition(0);
		allBodies[0].setxVelocity(-1);
		allBodies[0].setyVelocity(0);
		allBodies[0].setSize(size);
		
		allBodies[1].setxPosition(0);
		allBodies[1].setyPosition(0);
		allBodies[1].setxVelocity(1);
		allBodies[1].setyVelocity(0);
		allBodies[1].setSize(size);
		
		
		for(int i = 0; i < timeSteps; i++)
		{
			physics(allBodies);
		}
	}
	private static void physics(Body[] allBodies) {
		// TODO Auto-generated method stub
		for(int i=0; i<allBodies.length; i++)
		{
			//do physics stuff
		}
	}
	private static class Body
	{
		private int xPosition;
		private int yPosition;
		private int xVelocity;
		private int yVelocity;
		private int size;
		public int getxPosition() {
			return xPosition;
		}
		public void setxPosition(int xPosition) {
			this.xPosition = xPosition;
		}
		public int getyPosition() {
			return yPosition;
		}
		public void setyPosition(int yPosition) {
			this.yPosition = yPosition;
		}
		public int getxVelocity() {
			return xVelocity;
		}
		public void setxVelocity(int xVelocity) {
			this.xVelocity = xVelocity;
		}
		public int getyVelocity() {
			return yVelocity;
		}
		public void setyVelocity(int yVelocity) {
			this.yVelocity = yVelocity;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		
	}
}
