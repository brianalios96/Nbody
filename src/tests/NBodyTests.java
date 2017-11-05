package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import model.Body;

public class NBodyTests {

	@Test
	public void test() {
		Body allBodies[] = new Body[2];
		for(int i=0; i<2; i++)
		{
			allBodies[i]=new Body();
			allBodies[i].setcolor(Color.CYAN);
			allBodies[i].setSize(10);
			allBodies[i].setxPosition(20+(i*10));
			allBodies[i].setyPosition(10+(i*10));
			allBodies[i].setxVelocity(0);
			allBodies[i].setyVelocity(2);
		}
		allBodies[1].setcolor(Color.GREEN);
		allBodies[1].setyVelocity(-2);
		int collisions=0;
		for (int i = 0; i < 10; i++)
		{
			collisions= collisions+ physics(allBodies);
		}
	}

}
