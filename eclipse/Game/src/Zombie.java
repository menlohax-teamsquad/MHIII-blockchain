import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Zombie implements Constants
{
	//	private static ArrayList<Color> colors = new ArrayList<Color>();
	private int x, y;
	private Color color;
	private static Random gen = new Random();
	private int radius = ZOMBIE_RADIUS;
	private int moveSpeed = 1;
	private int currentHealth = ZOMBIE_HEALTH;

	public Zombie()
	{
		double seed = Math.random();
		if (seed < 0.25)
		{
			x = randomInt(radius, SCREEN_WIDTH - radius);
			y = TOP_OF_SCREEN + radius;
		}
		else if (seed < 0.5)
		{
			x = randomInt(radius, SCREEN_WIDTH - radius);
			y = SCREEN_HEIGHT - radius;
		}
		else if (seed < 0.75)
		{
			x = radius;
			y = randomInt(TOP_OF_SCREEN + radius, SCREEN_HEIGHT - radius);
		}
		else
		{
			x = SCREEN_WIDTH - radius;
			y = randomInt(TOP_OF_SCREEN + radius, SCREEN_HEIGHT - radius);
		}
		color = new Color(randomInt(80, 120), randomInt(40, 80), randomInt(0, 40));
		//		colors.add(new Color(randomInt(80, 120), randomInt(40, 80), randomInt(0, 40)));
	}
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}
	public int randomInt(int min, int max)
	{
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	public void move(int px, int py)
	{
		if (currentHealth < ZOMBIE_HEALTH)
		{
			color = Color.BLACK;
		}
		if (px < x)
		{
			x -= moveSpeed;
		}
		else if (px > x)
		{
			x += moveSpeed;
		}
		if (py < y)
		{
			y -= moveSpeed;
		}
		else if (py > y)
		{
			y += moveSpeed;
		}
	}
	public int attack(int px, int py)
	{
		double distance = Math.pow((x - px) * (x - px) + (y - py) * (y - py), 0.5);
//		if (PLAYER_RADIUS >= ZOMBIE_RADIUS && distance <= (PLAYER_RADIUS - ZOMBIE_RADIUS))
//		{
//			System.out.println("Circle 1 is inside Circle 2.");
//		}
//		else if (ZOMBIE_RADIUS >= PLAYER_RADIUS && distance <= (ZOMBIE_RADIUS - PLAYER_RADIUS) ) 
//		{
//			System.out.println("Circle 2 is inside Circle 1.");
//		}
//		else if (distance > (ZOMBIE_RADIUS + PLAYER_RADIUS))
//		{
//			System.out.println("Circle 2 does not overlap Circle 1.");
//		}
//		else 
//		{
//			System.out.println("Circle 2 overlaps Circle 1.");
//		}
		if (distance > (ZOMBIE_RADIUS + PLAYER_RADIUS))
		{
			return 0;
		}
		return 1;
	}
	public int getHealth() 
	{
		return currentHealth;
	}
	public int getX() 
	{
		return x;
	}
	public int getY() 
	{
		return y;
	}
	public void doDamage(int damage) 
	{
		currentHealth = currentHealth - damage;
	}
}
