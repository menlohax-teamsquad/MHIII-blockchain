import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;

import javax.swing.JFrame;

public class Multiply extends JFrame implements KeyListener, Constants
{
	public static int [] nums = {1,2,3,4,5,6,7,8,9,10,11,12};
	static String prompt = "What is %d times %d?";
	boolean next = true;
	static String answer;
	static String question;
	static int currentQuestion = 1;
	static boolean nextProblem = false;
	static String input = "";
	static int numCorrect = 0;
	boolean gameOver = false;
	public static void main (String [] args)
	{
		Multiply m = new Multiply();
	}
	public Multiply()
	{
		addKeyListener(this);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createBufferStrategy(2);
		newProblem();
		while (!gameOver)
		{
			repaint();
			if (nextProblem)
			{
				newProblem();
			}
		}
	}
	public void newProblem()
	{
		nextProblem = false;
		int num1 = randInt(nums);
		int num2 = randInt(nums);
		answer = "" + (num1 * num2);
		question = String.format(prompt, num1, num2);
		System.out.println(question);
		currentQuestion++;
	}
	private int randInt(int [] numArr) 
	{
		return numArr[((int) (Math.random() * numArr.length))];
	}
	public void myPaint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 22, SCREEN_WIDTH, SCREEN_HEIGHT);
		if (gameOver)
		{
			String str = "You were correct " + new DecimalFormat("#.##").format((double)(numCorrect)/NUM_PROBLEMS*(100)) + "% of the time";
			g.setColor(Color.green);
			drawCenteredString(g, str, new Rectangle(400, 200, 400, 150), new Font("Arial", Font.BOLD, 50));
			drawCenteredString(g, "Your Strength has increased by 1!", new Rectangle(400, 300, 400, 150), new Font("Arial", Font.BOLD, 50));
		}
		else
		{
			drawOverlay(g);
			g.setColor(Color.black);
			drawCenteredString(g, question, new Rectangle(0, TOP_OF_SCREEN - 200, SCREEN_WIDTH, SCREEN_HEIGHT), new Font("Arial", Font.PLAIN, 100));
			g.setColor(Color.BLACK);
			g.drawRect(400, 350, 400, 150);
			drawCenteredString(g, input, new Rectangle(400, 350, 400, 150), new Font("Arial", Font.BOLD, 50));
		}
	}
	public void drawOverlay(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(0, MENU_BAR - 1, SCREEN_WIDTH, TOP_OF_SCREEN - MENU_BAR);
		g.setColor(Color.black);
		g.drawRect(0, MENU_BAR - 1, SCREEN_WIDTH, TOP_OF_SCREEN - MENU_BAR);
		drawCenteredString(g, "Question " + (currentQuestion - 1) + " out of " + NUM_PROBLEMS, new Rectangle(150, MENU_BAR - 10, 100, 100), new Font("Arial", Font.PLAIN, 35));
		drawCenteredString(g, new DecimalFormat("#.##").format((double)(numCorrect)/Math.max(1, currentQuestion-2)*(100)) + "% correct", new Rectangle(1100, MENU_BAR - 10, 100, 100), new Font("Arial", Font.PLAIN, 35));
	}
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) 
	{
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(font);
		// Determine the X coordinate for the text
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Set the font
		g.setFont(font);
		// Draw the String
		g.drawString(text, x, y);
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
	public void keyTyped(KeyEvent e)
	{
		if (Character.isDigit(e.getKeyChar()))
		{
			input += e.getKeyChar();
		}
		System.out.println(e.getExtendedKeyCode());
		if (e.getExtendedKeyCode() == 8)
		{
			if (input.length() < 2)
			{
				input = "";
			}
			else
			{
				input = input.substring(0, input.length()-1);
			}
		}
		repaint();
	}
	private void submit() 
	{
		if (currentQuestion > NUM_PROBLEMS)
		{
			System.out.println("da");
			gameOver = true;
		}
		if (input == "")
		{
			nextProblem = true;
			return;
		}
		if (Integer.parseInt(input) == Integer.parseInt(answer))
		{
			numCorrect++;
		}
		nextProblem = true;
		input = "";
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e)
	{
		if (e.getExtendedKeyCode() == 10)
		{
			submit();
		}
	}
}
