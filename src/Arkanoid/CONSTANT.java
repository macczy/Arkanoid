package Arkanoid;
/**
 * Contains:
 * the WIDTH and the HEIGHT of each block before it's creation
 * the radius of the ball
 * all states of the game to use in Controller
 * @author Maciej
 */
public class CONSTANT {
	/**
	 * width of a single block
	 */
	public static final int WIDTH = 120;
	/**
	 * height of a single block
	 */
	public static final int HEIGHT = 50;
	/**
	 * radius of the ball
	 */
	public static final int BALL_RADIUS = 10;
	/**
	 * game status constans
	 */
	public static final int INITIAL_STATE = 0;
	/**
	 * game status constans
	 */
	public static final int IN_GAME = 1;
	/**
	 * game status constans
	 */
	public static final int GAME_WON = 2;
	/**
	 * game status constans
	 */
	public static final int GAME_LOST = 3;
	/**
	 * private constructor, as the class is static
	 */
	private CONSTANT () {}
}
