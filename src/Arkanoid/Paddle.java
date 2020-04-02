package Arkanoid;


/**The class representing the paddle*/
public class Paddle
{
	/**
	 * x coordinate of the paddle
	 */
	private int x;
	/**
	 * y coordinate of the paddle
	 */
	private final int y;
	/**
	 * width of the paddle
	 */
	private final int width;
	/**
	 * height of the paddle
	 */
	private final int height;
	/**
	 * initial position of the paddle, used when resetting it
	 */
	private final int initX;
	/**
	 * the right edge of the screen that should no be passed
	 */
	private final int maxX;
	/**
	 * Paddle constructor
	 * @param y - height of game board
	 * @param x - width of game board
	 */
	public Paddle(int y, int x)
	{
		height = 20;
		width = 100;
		this.y = y - height;
		this.x = x/2 - width/2;
		initX = this.x;
		maxX = x;
	}
	/**
	 * resets paddle to the initial position
	 */
	public void resetPosition()
	{
		x = initX;
	}
	
	/**
	 * get paddle height
	 * @return height of the paddle
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * get paddle width
	 * @return width of the paddle
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * get paddle x coordinate
	 * @return x position of the paddle
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * get paddle y coordinate
	 * @return y position of the paddle
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * move paddle to the right by distance (if possible)
	 * give negative to move left
	 * @param distance to move the paddle by
	 */
	public void move(int distance)
	{
		if(distance>=0)
		{
			if(this.x + distance + width < maxX)
			{
				this.x += distance;
			}
			else
			{
				this.x = maxX-width;
			}
		}else
		{
			if(this.x + distance > 0)
			{
				this.x += distance;
			}
			else
			{
				this.x = 0;
			}
		}
	}
}