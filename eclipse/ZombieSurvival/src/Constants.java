import java.awt.Color;
import java.awt.Toolkit;

public interface Constants 
{
	final Color [] colors = {Color.red, Color.blue, Color.black};
	final int PLAYER_RADIUS = 10;
	final int ATTACK_DELAY = 1000;
	final int ZOMBIE_RADIUS = 10;
	final int MENU_BAR = 22;
	final int TOP_OF_SCREEN = 100;
	final int TICK_SPEED = 20;
	final int SPRINT_DELAY = 50;
	final int REGEN_CHANCE = 25; //1 in x chance of happening
	final int ZOMBIE_SPAWN = 20; //1 in x chance of happening
	final double ZOMBIE_SPAWN_INCREASE = (double) 1/3; //smaller numbers mean faster increase
	final int ZOMBIE_CLUSTER = 3;
	final int CLUSTER_VARIATION = 2;
	final int ZOMBIE_DAMAGE = 2;
	final int ZOMBIE_HEALTH = 10;
	public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//	List<Color> colors = new ArrayList<Colors>(Arrays.asList(array))
}
