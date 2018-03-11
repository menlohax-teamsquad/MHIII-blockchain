import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

public class GameScreen extends JFrame implements KeyListener, Constants
{
	private final Set <Integer> pressed = new HashSet <Integer>();
	private int milliseconds = 0;
	static Player p = new Player("player1.json");
	static ArrayList <Zombie> zombies = new ArrayList<Zombie>();
	boolean gameOver = false;
	int kills = 0;
	int attackWait = 0;
	public static void main(String args[])
	{
		makeZombies(10);
		//data = p.readJSONData();
		try
		{
			GameScreen gs = new GameScreen(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void makeZombies(int numZombies) 
	{
		for (int i = 0; i < numZombies; i++)
		{
			zombies.add(new Zombie());
		}
	}
	public int randomInt(int min, int max)
	{
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	/**
	 * ManyBalls constructor: create the one Ball and simulate the "clock"
	 * @throws IOException 
	 */
	public GameScreen(Player p) throws IOException
	{
		addKeyListener(this);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createBufferStrategy(2);

		while (p.getCurrentHealth() > 0)
		{
			try {
				Thread.sleep(1000/TICK_SPEED);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			actionPerformed();
		}
		gameOver = true;
	}

	private void actionPerformed()
	{
		System.out.println(attackWait);
		milliseconds += 1000/TICK_SPEED;
		attackWait = max(0, (attackWait - (1000/TICK_SPEED)));
//		System.out.println(attackWait);
		//		System.out.println(p.getSprintState() + " " +  p.getDX() + " " + p.getSprintDX());
		p.update();
//		System.out.println(Math.max(1.0, (ZOMBIE_SPAWN - (milliseconds/1000)*(ZOMBIE_SPAWN_INCREASE))) + "__" + milliseconds);

		if (((int) Math.ceil(Math.random() * Math.max(1.0, (ZOMBIE_SPAWN - (milliseconds/1000)*(ZOMBIE_SPAWN_INCREASE)))) == 1))
		{
			makeZombies(randomInt(ZOMBIE_CLUSTER - CLUSTER_VARIATION, ZOMBIE_CLUSTER + CLUSTER_VARIATION));
		}
		for (Zombie z : zombies)
		{
			p.takeDamage(z.attack(p.getX(), p.getY()));
			z.move(p.getX(), p.getY());
		}
		repaint();
	}
	public void attack()
	{
		if (attackWait == 0)
		{
			for (int i = 0; i < zombies.size(); i++)
			{
				p.attack(zombies.get(i));
				if (zombies.get(i).getHealth() <= 0)
				{
					kills++;
					zombies.remove(i);
				}
			}
			attackWait = ATTACK_DELAY/p.getInteligence();
		}
	}
	public int max(int a, int b)
	{
		if (a > b)
		{
			return a;
		}
		return b;
	}
	public void myPaint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 22, SCREEN_WIDTH, SCREEN_HEIGHT);

		drawOverlay(g, p);
		p.draw(g);
		for (Zombie z : zombies)
		{
			z.draw(g);
		}
		if (gameOver)
		{
			g.setColor(Color.white);
			g.fillRect(0, 22, SCREEN_WIDTH, SCREEN_HEIGHT);
			g.setColor(Color.BLACK);
			drawCenteredString(g, "Game Over!", new Rectangle(0, TOP_OF_SCREEN, SCREEN_WIDTH, SCREEN_HEIGHT - 200), new Font("Futura", Font.PLAIN, 100));
			drawCenteredString(g, "Your Score was: " + (kills*1000 + milliseconds), new Rectangle(0, TOP_OF_SCREEN, SCREEN_WIDTH, SCREEN_HEIGHT), new Font("Futura", Font.PLAIN, 50));
		}
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
	public void drawOverlay(Graphics g, Player p)
	{
		g.setColor(Color.gray);
		g.fillRect(0, MENU_BAR - 1, SCREEN_WIDTH, TOP_OF_SCREEN - MENU_BAR);
		g.setColor(Color.black);
		g.drawRect(0, MENU_BAR - 1, SCREEN_WIDTH, TOP_OF_SCREEN - MENU_BAR);
		if (p.getTimeSprinting() - 1 <= p.getStamina())
		{
			g.setColor(Color.GREEN);
		}
		else
		{
			g.setColor(Color.YELLOW);
		}
		g.fillRect(10, MENU_BAR + 11, 100, 58);
		g.setColor(Color.black);
		g.fillRect(10, MENU_BAR + 11, p.getTimeSprinting()*2, 58);

		g.fillRect(150, MENU_BAR + 11, p.getMaxHealth(), 58);
		g.setColor(Color.red);
		g.fillRect(150, MENU_BAR + 11, p.getCurrentHealth(), 58);

		if (attackWait == 0)
		{
			g.setColor(Color.GREEN);
		}
		else
		{
			g.setColor(Color.YELLOW);
		}
		g.fillRect(190 + p.getMaxHealth(), MENU_BAR + 11, 100, 58);
		g.setColor(Color.black);
		g.fillRect(190 + p.getMaxHealth(), MENU_BAR + 11, attackWait/10, 58);
		
		g.setColor(Color.black);
		g.setFont(new Font("Futura", Font.PLAIN, 20)); 
		g.drawString((int) milliseconds/1000 + "", SCREEN_WIDTH - 100, 68);	
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
	public void addNotify()
	{
		super.addNotify();
		requestFocus();
	}
	public synchronized void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == 16)
		{
			p.setSprintState(true);
		}
		else
		{
			pressed.add(e.getKeyCode());
		}
		for (int code : pressed)
		{
//			System.out.print((char) code);
			switch ((char) code)
			{
			case 'W': p.moveUp(); break;
			case 'S': p.moveDown(); break;
			case 'A': p.moveLeft(); break;
			case 'D': p.moveRight(); break;
			case ' ': attack(); break;
			default: break;
			}
		}
		//		System.out.println("code:\'" + e.getKeyCode() + "\' char:\'" + e.getKeyChar() + "\'");
		System.out.println();
	}
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == 16)
		{
			p.setSprintState(false);
		}
		else
		{
			pressed.remove(e.getKeyCode());
		}

	}
	public void keyTyped(KeyEvent e) {}
}