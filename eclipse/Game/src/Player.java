import static java.lang.Math.toIntExact;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/*
 * Player class
 */
public class Player implements Constants
{
	private int x, y; //coords
	private int dx, dy; //speed
	private int sprintDX, sprintDY; //sprint speed
	private int radius; //size

	private int maxHealth;
	private int currentHealth;
	private int attack;
	private int inteligence;
	private int stamina;
	private int speed;
	private int dexterity;
	private Color color1;
	private Color color2;
	private String name;

	private boolean isSprinting = false;
	private int timeSprinting = 0;
	
	private Random gen = new Random();

	public Player (String filename)
	{
		readFromJSON(filename);
		dx = speed;
		dy = speed;
		sprintDX = 4*dx;
		sprintDY = 4*dy;
		x = SCREEN_WIDTH/2;
		y = SCREEN_HEIGHT/2;
		radius = PLAYER_RADIUS;
	}
	private void readFromJSON(String filename)
	{
		JSONParser parser = new JSONParser();
		try
		{
			Object object = parser.parse(new FileReader(filename));

			//convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;

			//Reading the String
			maxHealth = toIntExact((Long) jsonObject.get("hp"));
			currentHealth = maxHealth;
			attack = toIntExact((Long) jsonObject.get("str"));
			inteligence = toIntExact((Long) jsonObject.get("intel"));
			stamina = toIntExact((Long) jsonObject.get("stam"));
			speed = toIntExact((Long) jsonObject.get("spd"));
			dexterity = toIntExact((Long) jsonObject.get("dex"));
			color1 = colors[toIntExact((Long) jsonObject.get("color1"))];
			color2 = colors[toIntExact((Long) jsonObject.get("color1"))];
			name = (String) jsonObject.get("name");

			System.out.println("Loading " + name);
			System.out.println("Stats:");
			System.out.println("\tHealth: " + maxHealth);
			System.out.println("\tAttack: " + attack);
			System.out.println("\tInteligence: " + inteligence);
			System.out.println("\tStamina: " + stamina);
			System.out.println("\tStamina: " + speed);
			System.out.println("\tDexterity: " + dexterity);
			System.out.println("\tBody Color: " + color1.toString());
			System.out.println("\tWeapon Color: " + color2.toString());
		}
		catch (FileNotFoundException fe) { fe.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	}
	public int getDX()
	{
		return dx;
	}
	public int getDY()
	{
		return dy;
	}
	public int getSprintDX()
	{
		return sprintDX;
	}
	public int getSprintDY()
	{
		return sprintDY;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void draw(Graphics g)
	{
		g.setColor(color1);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}
	public void moveUp() 
	{
		if (isSprinting && timeSprinting < stamina)
		{
			timeSprinting++;
			y = y - sprintDY;
		}
		else
		{
			y = y - dy;
		}
		if (TOP_OF_SCREEN + radius > y)
		{
			y = radius + TOP_OF_SCREEN;
		}
	}
	public void moveDown()
	{
		if (isSprinting && timeSprinting < stamina)
		{
			timeSprinting++;
			y = y + sprintDY;
		}
		else
		{
			y = y + dy;
		}
		if (SCREEN_HEIGHT < y + radius)
		{
			y = SCREEN_HEIGHT - radius;
		}
	}
	public void moveRight()
	{
		if (isSprinting && timeSprinting < stamina)
		{
			timeSprinting++;
			x = x + sprintDX;
		}
		else
		{
			x = x + dx;
		}
		if (SCREEN_WIDTH < x + radius)
		{
			x = SCREEN_WIDTH - radius;
		}
	}
	public void moveLeft()
	{
		if (isSprinting && timeSprinting < stamina)
		{
			x = x - sprintDX;
			timeSprinting++;
		}
		else
		{
			x = x - dx;
		}
		if (0 > x - radius)
		{
			x = radius;
		}
	}
	public void attack(Zombie z)
	{
		int zx = z.getX();
		int zy = z.getY();
		double distance = Math.pow((x - zx) * (x - zx) + (y - zy) * (y - zy), 0.5);
		if (distance <= (ZOMBIE_RADIUS + PLAYER_RADIUS) * 3)
		{
			z.doDamage(attack);
		}
	}
	public void setSprintState(boolean b) 
	{
		isSprinting = b;
	}
	public boolean getSprintState()
	{
		return isSprinting;
	}
	public void update() 
	{
		if (!isSprinting)
		{
			timeSprinting = max(0, timeSprinting - 1);
		}
		else
		{
			if (timeSprinting > stamina)
			{
				timeSprinting = SPRINT_DELAY;
			}
			else
			{
				timeSprinting++;
			}
		}
		if (currentHealth < maxHealth && (int) (Math.random() * REGEN_CHANCE) == 1)
		{
			currentHealth++;
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
	public int getStamina() 
	{
		return stamina;
	}
	public int getTimeSprinting()
	{
		return timeSprinting;
	}
	public int getMaxHealth() 
	{
		return maxHealth;
	}
	public int getCurrentHealth() 
	{
		return currentHealth;
	}
	public void takeDamage(int damage)
	{
		currentHealth -= damage;
	}
}