import java.awt.Toolkit;

public interface Constants 
{
	public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 200;
	final int NUM_PROBLEMS = 5;
	final int MENU_BAR = 22;
	final int TOP_OF_SCREEN = 100;
}
