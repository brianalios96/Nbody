package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import model.Body;
import view.NBodyGUI;

public class NBodyTests {

	private static final double TOLERANCE = 0.01;

	@Test
	public void test() {
		Body allBodies[] = new Body[2];

		allBodies[0] = new Body();
		allBodies[0].setcolor(Color.CYAN);
		allBodies[0].setSize(10);
		allBodies[0].setxPosition(20);
		allBodies[0].setyPosition(10);
		allBodies[0].setxVelocity(0);
		allBodies[0].setyVelocity(2);

		allBodies[1] = new Body();
		allBodies[1].setcolor(Color.GREEN);
		allBodies[1].setSize(10);
		allBodies[1].setxPosition(20);
		allBodies[1].setyPosition(20);
		allBodies[1].setxVelocity(0);
		allBodies[1].setyVelocity(-2);

		int collisions = 0;
		collisions = collisions + physics(allBodies);
		assertEquals(1, collisions);
		assertEquals(20, allBodies[0].getxPosition());
		assertEquals(10, allBodies[0].getyPosition());
		assertEquals(0, allBodies[0].getxVelocity(), TOLERANCE);
		assertEquals(1.983324, allBodies[0].getyVelocity(), TOLERANCE);

		assertEquals(20, allBodies[1].getxPosition());
		assertEquals(19, allBodies[1].getyPosition());
		assertEquals(0, allBodies[1].getxVelocity(), TOLERANCE);
		assertEquals(-1.98511, allBodies[1].getyVelocity(), TOLERANCE);
	}

	@Test
	public void testposition() {
		Random rng = new Random();
		ArrayList<ArrayList<Integer>> d = new ArrayList<ArrayList<Integer>>(3);

		for(int i=0; i<3; i++)
		{
			d.add(new ArrayList<Integer>());
		}
		
		// get row position
		int rowposition = rng.nextInt(3);
		int columnposition = rng.nextInt(8);
		int fullcount=0;
		for (int i = 0; i < 24; i++) {
			while (d.get(rowposition).contains(columnposition)) {
				if (d.get(rowposition).size() == 8) {
					System.out.println("row is full "+rowposition);
					rowposition = rng.nextInt(3);
				} else {
					columnposition = rng.nextInt(8);
				}
			}
			d.get(rowposition).add(columnposition);
			fullcount=fullcount+1;
			if(fullcount==24)
			{
				System.out.println("space is full, too many input");
				break;
			}
			rowposition = rng.nextInt(3);
			columnposition = rng.nextInt(8);
		}
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<d.get(i).size(); j++)
			{
				System.out.println(d.get(i).get(j)+" ");
			}
			System.out.println("");
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
