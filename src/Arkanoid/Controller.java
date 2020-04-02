package Arkanoid;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Controller 
 * runs the main thread, checks for collisions and listens to player input
 * @author Maciej
 */
public class Controller implements KeyListener
{	
	/**
	 * the status of the game, they are defined in CONSTANT.java and are as follows:
	 * 	INITIAL_STATE = 0;
	 *  N_GAME = 1;
	 *  GAME_WON = 2;
	 *  GAME_LOST = 3;
	 */
	private int gameStatus = 0;
	/**
	 * time spend on sleep between each game cycle
	 */
	private int gameSpeed = 2;
	/**
	 * the initial direction to set the ball to
	 * changes basic on movement during INITIAL_STATE
	 */
	private int initDirection;
	/**
	 * reference to model
	 */
	private Model model;
	/**
	 * reference to view
	 */
	private View view;
	/**
	 * sets reference to view and model
	 * initiate hearts for the view
	 * @param view - reference to view, cannot be null
	 * @param model - reference to model, cannot be null
	 */
	public Controller(View view, Model model)
	{
		initDirection = 3;
		gameStatus = 0;
		this.view = view;
		this.model = model;
		view.addKeyListener(this);
		view.initHearts(model.getNumberOfLives());
	}
	/**
	 * run the main loop
	 */
	private void runGame() {
	      Thread gameThread = new Thread() {    	
	    	  /**
	    	   * the actual main loop in the in_game state
	    	   * call update, then refreshes view
	    	   * and sleeps some time depending on the gameSpeed
	    	   */
	         public void run() {
	        	   while (gameStatus == CONSTANT.IN_GAME) {	
	        		   update();
	        		   
	        		   view.refresh();
		               
	        		   try {
			              Thread.sleep(gameSpeed);
			           } catch (InterruptedException ex) {

			           }
	               }
	        	   
	         }
	     };
	     gameThread.start();       
	}
	/**
	 * asks model to move the ball
	 * then checks for collision and then checks for win or lose conditions
	 */
	private void update()
	{
		boolean lifeLost = model.update();
		
		if( lifeLost )
		{
			if(model.isWin())
			{
				view.gameWon();
				gameStatus = CONSTANT.GAME_WON;
			}
		}
		else
		{
			gameStatus = CONSTANT.INITIAL_STATE;
			model.lifeLost();
 			if(model.isLost())
			{
				gameStatus = CONSTANT.GAME_LOST;
			}
			view.lifeLost(model.getNumberOfLives()); 		    
		}
	}

	/**
	 * checks for player input
	 * left and right arrow to move paddle
	 * space for starting the game
	 */
	@Override
	public void keyPressed(KeyEvent e) 
	{
			switch(e.getKeyCode()) 
			{
				case KeyEvent.VK_RIGHT:
					if(gameStatus==CONSTANT.IN_GAME)
					{
					    model.movePaddle(20);
					}
					else if(gameStatus==CONSTANT.INITIAL_STATE)
					{
					    model.movePaddleBall(15);
					    initDirection = initDirection>4?5:(initDirection+1);
				    }
					view.repaint();
					break;
				case KeyEvent.VK_LEFT:
			        if(gameStatus==CONSTANT.IN_GAME)
			        {
			        	model.movePaddle(-20); 
			        }
			        else if(gameStatus==CONSTANT.INITIAL_STATE)
			        {
			        	model.movePaddleBall(-15);
				        initDirection = initDirection<1?0:(initDirection-1);   
		        	}
			        view.repaint();
			        break;
				case KeyEvent.VK_SPACE:
					if(gameStatus==CONSTANT.INITIAL_STATE)
					{
						gameStatus = CONSTANT.IN_GAME;
						switch(initDirection)
						{
						case 0: 
							model.setMovement(-1, 0.5f);
							break;
						case 1: 
							model.setMovement(-0.5f, 0.4f);
							break;
						case 2: 
							model.setMovement(-0.5f, 1f);
							break;
						case 3: 
							model.setMovement(0.5f, 1f);
							break;
						case 4: 
							model.setMovement(0.5f, 0.5f);
							break;
						default:
							model.setMovement(1f, 0.5f);
							break;
						}
						view.clearMessage();
						runGame();
					}else if(gameStatus == CONSTANT.GAME_LOST)
					{
						model.nextLevel();
						view.resetGame(model.getNumberOfLives());
						gameStatus = 0;
						view.repaint();
					}else if(gameStatus == CONSTANT.GAME_WON)
					{
						model.nextLevel();
						view.gameWon();
						gameStatus = CONSTANT.INITIAL_STATE;
					}
					break;
			}
	}
	/**
	 * has to override for KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		;// nothing to do here
	}

	/**
	 * has to override for KeyListener, no events are taken
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		;// nothing to do here
	}
}
