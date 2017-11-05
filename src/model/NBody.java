package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import view.*;


public class NBody
{
	private static final int secondInTimeStep = 1;
	private static final int FramePerSecond = 50;
	public static void main(String[] args)
	{
		int workers = 0;// , 1 to 32. This argument will be ignored by the sequential solution.
		int bodies = 2;
		int size = 7;// of each body.
		int timeSteps = 1000;// number of time steps
		boolean guiOn = true;

		long executionTime = 0;

		// change values to the command line arguments
		if (1 <= args.length) {
			workers = Integer.parseInt(args[0]);
		}
		if (2 <= args.length) {
			bodies = Integer.parseInt(args[1]);
		}
		if (3 <= args.length) {
			size = Integer.parseInt(args[2]);
		}
		if (4 <= args.length) {
			timeSteps = Integer.parseInt(args[3]);
		}
		if (5 == args.length) {
			guiOn = Boolean.parseBoolean(args[4]);// TODO test if this works
		}
		
		System.out.println("workers: "+workers);
		System.out.println("bodies: "+bodies);
		System.out.println("size: "+size);
		System.out.println("timeSteps: "+timeSteps);
		System.out.println("guiOn: "+guiOn);

		Body allBodies[] = new Body[bodies];
		for (int i = 0; i < allBodies.length; i++) {
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
		NBodyGUI gui = null;
		if (guiOn) {
			//second parameter is the window size
			gui = new NBodyGUI(allBodies);
		}

		int collisions=0;
		
		// start the timer
		long startTime = System.nanoTime();

		for (int i = 0; i < timeSteps; i++)
		{
			collisions= collisions+ physics(allBodies);
			if (guiOn) 
			{
				gui.update();
			}
			try {
				Thread.sleep(1000 / FramePerSecond);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		// stop the timer
		long endTime = System.nanoTime();

		// convert the time to microseconds
		executionTime = (endTime - startTime) / 1000000;//nano to milli
		long seconds = executionTime / 1000;//milli to seconds
		long milliseconds = executionTime - (seconds * 1000);//find left over milliseconds
		
		System.err.println("computation time: "+seconds+" seconds "+ milliseconds +" milliseconds");
		System.err.println("Number of collisions: "+collisions);

		if (guiOn) {
			gui.dispose();
		}
		
		writetofile(allBodies);
	}

	private static void writetofile(Body[] allBodies) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("NBodyResults.txt"));
			for(int i=0; i<allBodies.length; i++)
			{
				out.write("Body: "+i);
				out.newLine();
				out.write("XPosition: "+allBodies[i].getxPosition()+", YPosition: "+allBodies[i].getyPosition()
						+", XVelocity: "+ allBodies[i].getxVelocity()+", YVeloctiy: "+allBodies[i].getyVelocity());
				out.newLine();
				out.newLine();
			}
			out.close();
		}catch (IOException e) {
			System.out.println("error in writing to file");
		}
		
	}

	private static int physics(Body[] allBodies) {
		int collisions=0;
		for (Body body : allBodies) {
			collisions= body.updateVelocity(allBodies, secondInTimeStep);
		}
		for (Body body : allBodies) {
			body.updatePosition(secondInTimeStep);
		}
		return collisions;
	}
}
