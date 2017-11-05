package model;

import java.util.concurrent.*;
import view.*;

public class WorkerThread extends Thread
{
	private int threadID;
	private int numThread;
	private static Semaphore barriers[][];
	NBodyGUI GUI;

	public WorkerThread(int ID, int numThreads, NBodyGUI gui, Body allBodies[])
	{
		threadID = ID;
		this.numThread = numThreads;
		GUI = gui;
		
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