

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class NBodyParallel {

	public static void main(String[] args)
	{
		int workers = 32;// 1 to 32. This argument will be ignored by the sequential solution.
		int bodies = 50;
		int size = 10;// of each body.
		int timeSteps = 10000;// number of time steps (how many times the physics loop will run)
		boolean guiOn = false;//display the GUI or not
		boolean random= false;//set size of bodies to random numbers
		boolean sleeper=false;//make the gui sleep

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
		if(6<= args.length)
		{
			random=Boolean.parseBoolean(args[5]);
		}
		if(7== args.length)
		{
			sleeper= Boolean.parseBoolean(args[6]);
		}


		Random rng = new Random();
		Body allBodies[] = new Body[bodies];
		for (int i = 0; i < allBodies.length; i++) {
			allBodies[i] = new Body();
			allBodies[i].setxPosition(rng.nextInt(NBodyGUI.GUIsize));
			allBodies[i].setyPosition(rng.nextInt(NBodyGUI.GUIsize));
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
			gui = new NBodyGUI(allBodies, sleeper);
		}

		int collisions=0;
		
		WorkerThread threadworkers[]= new WorkerThread[workers];
		
		// start the timer
		long startTime = System.nanoTime();
		
		
		for(int i=0; i<workers; i++)
		{
			threadworkers[i]= new WorkerThread(i, workers, gui, allBodies, timeSteps);
			threadworkers[i].start();
		}
		
		int totalcollisions= 0;
		//see if the threads are done
		for(int i=0; i<workers; i++)
		{
			try {
				threadworkers[i].join();
				totalcollisions= totalcollisions+ threadworkers[i].getcollisons();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			out = new BufferedWriter(new FileWriter("NBodyParallelResults.txt"));
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
	
}
