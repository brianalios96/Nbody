package model;

import java.util.concurrent.*;
import view.*;

public class WorkerThread extends Thread
{
	private int threadID;
	private int numThread;
	private static Semaphore barriers[][];
	private NBodyGUI GUI;
	private Body[] allBodies;
	private double timeSteps;


	public WorkerThread(int ID, int numThreads, NBodyGUI gui, Body allBodies[], double timesteps)
	{
		threadID = ID;
		this.numThread = numThreads;
		GUI = gui;
		this.allBodies = allBodies;
		this.timeSteps= timesteps;
		
		if(barriers == null)
		{
			barriers = new Semaphore[5][32];
			for(int i = 0; i < barriers.length; i++)
			{
				for(int j = 0; j < barriers[0].length; j++)
				{
					barriers[i][j] = new Semaphore(0);
				}
			}
		}
	}
	
	@Override
	public void run()
	{
		int height = allBodies.length / numThread;
		int firstbody = threadID * height + 1;
		int lastBody = firstbody + height;

		if (threadID == (numThread - 1))
		{
			lastBody = allBodies.length + 1;
		}
		
		
		for(int i = 0; i < timeSteps; i++)
		{
			for(int j = firstbody; i<lastBody; i++)
			{
				allBodies[j].updateVelocity(allBodies); //TODO
			}
			barrier();
			for(int j = firstbody; i<lastBody; i++)
			{
				allBodies[j].updatePosition(); //TODO
			}
			barrier();
			if(threadID == 0)
			{
				GUI.update();
			}
		}
	}
	
	private void barrier()
	{
		int i = 0;
		for (int stage = 1; stage < numThread; stage*=2)
		{
			barriers[i][threadID].release();
			int nextID = (threadID + stage) % numThread;
			try
			{
				barriers[i][nextID].acquire();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			i++;
		}

	}
}