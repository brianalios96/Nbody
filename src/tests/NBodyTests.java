package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import model.Body;

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
