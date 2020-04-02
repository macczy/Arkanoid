package Arkanoid;

import java.util.Random;
import java.util.ArrayList;
/**
 * Model of the project
 * contains list of all blocks, the paddle and the ball. 
 * Also points that the player currently has and the difficulty on which they are playing
 * As well as the number of Lives left
 * @author Maciej
 *
 */
public class Model
{
	/**
	 * reference to the view
	 */
	private View view;
	/**
	 * number of lives left
	 */
	private int numberOfLives = 5;
	/**
	 * list of all blocks
	 */
	private ArrayList<Block> blocks;
	/**
	 * the paddle
	 */
	private Paddle paddle;
	/**
	 * the ball
	 */
	private Ball ball;
	/**
	 * difficulty, which changes setLevel algorithm
	 */
	private int difficulty = 0;
	/**
	 * amount of points currently possesed by a player
	 */
	private int points = 0;
	/**
	 * simple constructor. Sets reference to view for later use and generates the paddle, the ball and the initial level
	 * @param view new reference to view cannot be empty
	 */
	public Model(View view)
	{
		this.view = view;
		blocks = new ArrayList<Block>();
		view.addBlocks(blocks);
		paddle = new Paddle(view.getHeight(), view.getWidth());
		view.addPaddle(paddle);
		ball = new Ball();
		view.addBall(ball);
		setLevel();
	}
	
	/**
	 * clear all blocks
	 */
	private void cleanUpBlocks()
	{
		while(!blocks.isEmpty())
		{
			blocks.remove(blocks.get(0));
		}
	}
	
	/**
	 * reset difficulty to 0
	 */
	private void resetDifficulty()
	{
		difficulty = 0;
	}
	/**
	 * fill in blocks basic on difficulty
	 * 0 - blocks will not spawn on the sides and top
	 * 1 - blocks may spawn in any place possible
	 * 2 or more - all space will be filled with blocks
	 */
	private void setLevel()
	{
		Random rand = new Random();
		
		switch(difficulty)
		{
		case 0:
				for(int i=1; i < view.getWidth() / CONSTANT.WIDTH - 1; i++) 
				{
					for(int j = 2; j< (view.getHeight()) / CONSTANT.HEIGHT - 5; j++) 
					{
						int tmp = rand.nextInt(10);
						tmp = tmp>5?0:tmp;
						if(tmp > 0)
							blocks.add(new Block(i*CONSTANT.WIDTH, j*CONSTANT.HEIGHT, tmp));
					}
				
				}
				break;
		case 1:
			for(int i=0; i < view.getWidth() / CONSTANT.WIDTH; i++) 
			{
				for(int j = 1; j< (view.getHeight()) / CONSTANT.HEIGHT - 5; j++) 
				{
					int tmp = rand.nextInt(10);
					tmp = tmp>5?0:tmp;
					if(tmp > 0)
						blocks.add(new Block(i*CONSTANT.WIDTH, j*CONSTANT.HEIGHT, tmp));
				}
			
			}
			break;
		default:
			for(int i=0; i < view.getWidth() / CONSTANT.WIDTH; i++) 
			{
				for(int j = 1; j< (view.getHeight()) / CONSTANT.HEIGHT - 5; j++) 
				{
					int tmp = rand.nextInt(10);
					tmp = tmp%5 + 1;
					if(tmp > 0)
						blocks.add(new Block(i*CONSTANT.WIDTH, j*CONSTANT.HEIGHT, tmp));
				}
			
			}
			break;
		}  
		paddle.resetPosition();
		ball.setX(paddle.getX() + paddle.getWidth()/2 - CONSTANT.BALL_RADIUS);
		ball.setY(paddle.getY() - CONSTANT.BALL_RADIUS*2);
	}
	/**
	 * raise difficulty by one
	 */
	private void raiseDifficulty()
	{
		difficulty++;
	}
	
	/**
	 * resets points to 0
	 */
	private void resetPoints()
	{
		points = 0;
		view.updatePoints(0);
	}
	/**
	 * add 1 point and let the view know about it
	 */
	private void newPoints()
	{
		points += difficulty;
		view.updatePoints(++points);
	}
	/**
	 * resets amount of lives left to the default value 5
	 */
	private void resetLife()
	{
		numberOfLives = 5;
	}
	/**
	 * one tick of the model, move ball basic on its movementX and movementY
	 * then check if the ball if going to collide with a border, block or paddle and bounces the ball if needed
	 * @return false if the a life has been lost and true otherwise
	 */
	public boolean update()
	{
		ball.move();
		int r = CONSTANT.BALL_RADIUS;
		   float x = ball.getRealX() + ball.getMovementX() + r;
		   float y = ball.getRealY() - ball.getMovementY() + r; //actual ball coordinates after next move
		   boolean horizontal = false; //max one horizontal bounce
		   boolean vertical = false; //max one vertical bounce
		   
		   if( (int) (x + r) >= 1200 || (int)( x - r) <= 0 ) 
		   {
			   ball.bounceHorizontal();
			   horizontal = true;
		   }
		   
		   if( (int)(y + r) >= paddle.getY()   ) 
		   {			   
	 		   if((int)(x + r) >= paddle.getX() && (int) (x - r) <= paddle.getX() + paddle.getWidth())
	 		   {
	 			  bounceOffPaddle();
	 			  vertical = true;
	 			  horizontal = true; //if hasn't bounced off the screen already, no need to check for side bounce
	 		   }
	 		   else
	 		   {   
	 			  ball.setX(paddle.getX() + paddle.getWidth()/2 - CONSTANT.BALL_RADIUS);
	 			  ball.setY(paddle.getY()-CONSTANT.BALL_RADIUS*2);
	 			  return false;
	 		   }
		   }
		   else
		   {
			   if((int)( y - r ) <= CONSTANT.HEIGHT ) 
			   {
				   vertical = true;
				   ball.bounceVertical(); 
			   }
			   for (int i = 0; (!horizontal || !vertical) && i < blocks.size(); i++) 
			   {
				    
					boolean shouldDegrade = false;
					Block block = blocks.get(i);
					
					  float testX = x;
					  float testY = y;

					  // which edge is closest?
					  if (x < block.getX())         testX = block.getX();      // test left edge
					  else if (x > block.getX() + CONSTANT.WIDTH) testX = block.getX() + CONSTANT.WIDTH;   // right edge
					  if (y < block.getY())         testY = block.getY();      // top edge
					  else if (y > block.getY() + CONSTANT.HEIGHT) testY = block.getY() + CONSTANT.HEIGHT;   // bottom edge

					  // get distance from closest edges
					  float distX = x-testX;
					  float distY = y-testY;
					  float distance = (float)Math.sqrt( (distX*distX) + (distY*distY) );
					  // if the distance is less than the radius, collision!
					  if (distance <= r) {
							if(x < block.getX()) //left-side bump
							{
								if(ball.getMovementX() > 0)
								{
									horizontal = true;
									ball.bounceHorizontal();
								}
							}
							else if( x > block.getX() + CONSTANT.WIDTH) //right-side bump
							{
								if(ball.getMovementX() < 0)
								{
									horizontal = true;
									ball.bounceHorizontal();
								}
							}
							if(y <= block.getY()) //top bump
							{
								if(ball.getMovementY() < 0)
								{
									ball.bounceVertical();
									vertical = true;
								}
							}
							else if(y >= block.getY() + CONSTANT.HEIGHT) //bottom bump
							{
								if(ball.getMovementY() > 0)
								{
									ball.bounceVertical();
									vertical = true;
								}
							}
							
							shouldDegrade = true;
					  }
				   if(shouldDegrade) 
				   {
				    	newPoints();
				    	if(!block.degrade()) {
			    			  blocks.remove(block);
			    			  i--;
			    		  }
				    }
			   }
		   }
		   return true;
	}
	
	/**
	 * set movement of the ball
	 * @param x x-axis movement
	 * @param y y-axis movement
	 */
	public void setMovement(float x, float y)
	{
		ball.setMovement(x, y);
	}
	/**
	 * get Number of Lives
	 * @return number of lives
	 */
	public int getNumberOfLives()
	{
		return numberOfLives;
	}
	/**
	 * decrements about of lives left
	 */
	public void lifeLost()
	{
		numberOfLives--;
	}
	
	/**
	 * Tells if the game is lost basic on the amount of lives left
	 * @return true if the game has been lost and false otherwise
	 */
	public boolean isLost()
	{
		if(numberOfLives>=0) return false;
				return true;
	}
	
	
	/**
	 * bounce the ball off the paddle basic on the place they connect
	 */
	private void bounceOffPaddle()
	{
		int section = ball.getX()+CONSTANT.BALL_RADIUS - paddle.getX();
		if(section<paddle.getWidth()/4)
		{
			
			if(ball.getMovementX()>-1)
			{
				ball.setMovement(ball.getMovementX()-0.25f,-ball.getMovementY());
			}else if(ball.getMovementY()>-0.25f)
			{
				ball.setMovement(ball.getMovementX(), -(ball.getMovementY()+0.25f));
			}
			else
			{
				ball.bounceVertical();
			}
		}
		else if(section < paddle.getWidth()*3/4)
		{
			ball.bounceVertical();
			
		}else
		{
			if(ball.getMovementX()<1)
			{
				ball.setMovement(ball.getMovementX()+0.25f, -(ball.getMovementY()-0.25f));
			}else if(ball.getMovementY()>-0.25f)
			{
				ball.setMovement(ball.getMovementX(), -(ball.getMovementY()+0.25f));
			}
			else
			{
				ball.bounceVertical();
			}
		}
	}
	/**
	 * checks if the game has been won
	 * @return true if there is no more blocks left and false otherwise
	 */
	public boolean isWin()
	{
		if(blocks.isEmpty()) return true;
		return false;
	}
	/**
	 * asks paddle to move by given distance
	 * @param distance - amount of pixels to move the paddle by to the right
	 */
	public void movePaddle(int distance)
	{
		paddle.move(distance);
	}
	/**
	 * asks paddle to move by given distance and then sets the ball to be above the middle of the paddle
	 * @param distance - amount of pixels to move the paddle by to the right
	 */
	public void movePaddleBall(int distance)
	{	
		paddle.move(distance);
		ball.setX(paddle.getX() + paddle.getWidth()/2 - CONSTANT.BALL_RADIUS);
	}
	/**
	 * advances to next game
	 */
	public void nextLevel()
	{
		if(isWin())
		{
			resetDifficulty();
			resetPoints();
			resetLife();
		}
		else
		{
			raiseDifficulty();	
			cleanUpBlocks();
		}
		setLevel();
	}
}
