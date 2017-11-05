package model;
import view.*;

public class NBody
{
	private static final int secondInTimeStep = 1;
	private static final int FramePerSecond = 200;
	public static void main(String[] args)
	{
		int workers = 0;// , 1 to 32. This argument will be ignored by the sequential solution.
		int bodies = 2;
		int size = 30;// of each body.
		int timeSteps = 10000;// number of time steps
		
		// TODO change for command line arguments

		Body allBodies[] = new Body[bodies];
		for (int i = 0; i < allBodies.length; i++)
		{
			allBodies[i] = new Body();
		}
		// Random rng = new Random();
		allBodies[0].setxPosition(200);
		allBodies[0].setyPosition(150);
		allBodies[0].setxVelocity(-0.1);
		allBodies[0].setyVelocity(0);
		allBodies[0].setSize(size);

		allBodies[1].setxPosition(100);
		allBodies[1].setyPosition(150);
		allBodies[1].setxVelocity(0.1);
		allBodies[1].setyVelocity(0);
		allBodies[1].setSize(size);

		NBodyGUI gui = new NBodyGUI(allBodies); //TODO set based off comand line argument?
		
		for (int i = 0; i < timeSteps; i++)
		{
			physics(allBodies);
			gui.update();
			try
			{
				Thread.sleep(1000 / FramePerSecond);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		gui.dispose(); // gets rid of the JFrame so the program ends
	}

	private static void physics(Body[] allBodies)
	{
		for(Body body: allBodies)
		{
			body.updateVelocity(allBodies, secondInTimeStep);
		}
		for(Body body: allBodies)
		{
			body.updatePosition(secondInTimeStep);
		}
	}
}
