package Arkanoid;
/**
 * main class to initiate the game
 * @author Maciej
 */
public class Arkanoid {
	/**
	 * the object to display the game
	 */
	private View view;
	/**
	 * object running the actual game during the ingame state
	 */
	private Model model;
	/**
	 * object checking for player input and running main game loop
	 */
	private Controller controller;
	/**
	 * default constructor
	 * creates view, model and controller and lets them to their job
	 */
	public Arkanoid()
	{
		view = new View();
		model = new Model(view);
		controller = new Controller(view, model);
	}
	
	/**
	 * Main function, starts up the game by creating object of class Arkanoid
	 * @param args - not used
	 */
	public static void main(String args[])
	{
		new Arkanoid();
	}

}
