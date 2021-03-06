

import java.io.*;
import java.util.*;

public class NBody {
	public static void main(String[] args) {
		int workers = 0;// 1 to 32. This argument will be ignored by the
						// sequential solution.
		int bodies = 50;
		int size = 10;// of each body.
		int timeSteps = 10000;// number of time steps (how many times the
								// physics loop will run)
		boolean guiOn = false;// display the GUI or not
		boolean random = false;// set size of bodies to random numbers
		boolean sleeper= false;//make the threads sleep for the gui

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
		if (6 <= args.length) {
			random = Boolean.parseBoolean(args[5]);
		}
		if(7== args.length)
		{
			sleeper= Boolean.parseBoolean(args[6]);
		}

		ArrayList<ArrayList<Integer>> used = new ArrayList<ArrayList<Integer>>(3);

		for (int i = 0; i < 500; i++) {
			used.add(new ArrayList<Integer>());
		}

		Random rng = new Random();
		Body allBodies[] = new Body[bodies];
		for (int i = 0; i < allBodies.length; i++) {
			allBodies[i] = new Body();
			int rowposition = rng.nextInt(400);
			int columnposition = rng.nextInt(400);
			int fullcount = 0;
			while (used.get(rowposition).contains(columnposition)) {
				if (used.get(rowposition).size() == 400) {
					System.out.println("row is full " + rowposition);
					rowposition = rng.nextInt(400);
				} else {
					columnposition = rng.nextInt(400);
				}
			}
			used.get(rowposition).add(columnposition);
			fullcount = fullcount + 1;
			if (fullcount == 1600) {
				System.err.println("space is full, too many input");
				return;
			}
			
			allBodies[i].setxPosition(rowposition);// rng.nextInt(NBodyGUI.GUIsize));
			allBodies[i].setyPosition(columnposition);// rng.nextInt(NBodyGUI.GUIsize));
			
			rowposition = rng.nextInt(400);
			columnposition = rng.nextInt(400);

			
			if (random == true) {
				allBodies[i].setSize(10 + rng.nextInt(40));
			} else {
				allBodies[i].setSize(size);
			}
		}

		NBodyGUI gui = null;
		if (guiOn) {
			gui = new NBodyGUI(allBodies, sleeper);
		}

		int collisions = 0;

		// start the timer
		long startTime = System.nanoTime();

		for (int i = 0; i < timeSteps; i++) {
			collisions = collisions + physics(allBodies);
			if (guiOn) {
				gui.update();
			}
		}

		// stop the timer
		long endTime = System.nanoTime();

		// convert the time to microseconds
		executionTime = (endTime - startTime) / 1000000;// nano to milli
		long seconds = executionTime / 1000;// milli to seconds
		long milliseconds = executionTime - (seconds * 1000);// find left over
																// milliseconds

		System.out.println("computation time: " + seconds + " seconds " + milliseconds + " milliseconds");
		System.out.println("Number of collisions: " + collisions);

		if (guiOn) {
			gui.dispose();
		}

		writetofile(allBodies);
	}

	private static void writetofile(Body[] allBodies) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("NBodyResults.txt"));
			for (int i = 0; i < allBodies.length; i++) {
				out.write("Body: " + i);
				out.newLine();
				out.write("XPosition: " + allBodies[i].getxPosition() + ", YPosition: " + allBodies[i].getyPosition()
						+ ", XVelocity: " + allBodies[i].getxVelocity() + ", YVeloctiy: "
						+ allBodies[i].getyVelocity());
				out.newLine();
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			System.out.println("error in writing to file");
		}

	}

	private static int physics(Body[] allBodies) {
		int collisions = 0;
		for (Body body : allBodies) {
			collisions = body.updateVelocity(allBodies);
		}
		for (Body body : allBodies) {
			body.updatePosition();
		}
		return collisions;
	}
}
