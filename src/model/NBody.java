package model;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import view.*;


public class NBody
{
	//private static final double secondInTimeStep = 0.1;//used to calculate velocity and position
	//private static final int FramePerSecond = 10;//how long the body sleeps

	public static void main(String[] args)
	{
		int workers = 0;// 1 to 32. This argument will be ignored by the sequential solution.
		int bodies = 50;
		int size = 10;// of each body.
		int timeSteps = 10000;// number of time steps (how many times the physics loop will run)
		boolean guiOn = false;//display the GUI or not
		boolean random= false;//set size of bodies to random numbers

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
		if (5 <= args.length) {
			guiOn = Boolean.parseBoolean(args[4]);
		}
		if(6== args.length)
		{
			random=Boolean.parseBoolean(args[5]);
		}


		Random rng = new Random();
		Body allBodies[] = new Body[bodies];
		for (int i = 0; i < allBodies.length; i++) {
			allBodies[i] = new Body();
			allBodies[i].setxPosition(rng.nextInt(NBodyGUI.GUIsize));
			allBodies[i].setyPosition(rng.nextInt(NBodyGUI.GUIsize));
			allBodies[i].setxVelocity(0);
			allBodies[i].setyVelocity(0);
			allBodies[i].setcolor(new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255)));
			if(random==true)
			{
				allBodies[i].setSize(10+rng.nextInt(40));
			}
			else{
			allBodies[i].setSize(size);
			}
		}

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
//			try {
//				Thread.sleep(1000 / FramePerSecond);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				System.exit(1);
//			}
		}

		// stop the timer
		long endTime = System.nanoTime();

		// convert the time to microseconds
		executionTime = (endTime - startTime) / 1000000;//nano to milli
		long seconds = executionTime / 1000;//milli to seconds
		long milliseconds = executionTime - (seconds * 1000);//find left over milliseconds
		
		System.out.println("computation time: "+seconds+" seconds "+ milliseconds +" milliseconds");
		System.out.println("Number of collisions: "+collisions);

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
			collisions= body.updateVelocity(allBodies);
		}
		for (Body body : allBodies) {
			body.updatePosition();
		}
		return collisions;
	}
}
