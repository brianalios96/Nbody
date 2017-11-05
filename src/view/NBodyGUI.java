package view;

import java.awt.*;
import javax.swing.*;
import model.*;

public class NBodyGUI
{
	private static final int FramePerSecond = 100;//slows down the simulation so it is understandable to humans.  dose NOT affect simulation.
	
	private Body allBodies[];
	private JFrame frame;

	public static final int GUIsize=500;
	
	public NBodyGUI(Body[] allBodies)
	{
		this.allBodies = allBodies;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(GUIsize,GUIsize);
		frame.setLocation(600, 200);
		frame.add(new DrawingPanel());
		frame.setVisible(true);
	}

	public void dispose()
	{
		frame.dispose();		
	}
	
	public void update()
	{
		frame.repaint();
		try {
			Thread.sleep(1000/FramePerSecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class DrawingPanel extends JPanel
	{
		private static final long serialVersionUID = 1L; // Completely useless, but required since some java employee added it to JPanel

		@Override
		public void paintComponent(Graphics g)
		{
			Graphics2D graphics = (Graphics2D) g;
			for(Body body: allBodies)
			{
				graphics.setColor(body.getcolor());
				graphics.fillOval(body.getxPosition(), body.getyPosition(), body.getSize(), body.getSize());
			}
		}
	}
}
