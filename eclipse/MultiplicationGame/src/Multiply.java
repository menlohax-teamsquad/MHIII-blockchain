import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Multiply extends JFrame
{
	public static void main (String [] args)
	{

	}
	public void myPaint(Graphics g)
	{
		
	}
	public void paint(Graphics g) //bufferstrategy to reduce lag
	{
		BufferStrategy bf = this.getBufferStrategy();
		if(bf == null)
			return;
		Graphics g2 = null;

		try
		{
			g2 = bf.getDrawGraphics();
			myPaint(g2);
		} finally
		{
			g2.dispose();
		}

		bf.show();
		Toolkit.getDefaultToolkit().sync(); //prevent lost frames
	}
}
