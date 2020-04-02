package Arkanoid;

/**The class representing a ball*/
public class Ball 
{
	/**
	 * x coordinate of the ball
	 */
	private float x;
	/**
	 * y coordinate of the ball
	 */
	private float y;
	/**
	 * width of the ball - equals 2 time it's radius
	 */
	private final int width;
	/**
	 * height of the ball - equals 2 time it's radius
	 */
	private final int height;
	/**
	 * amount of pixels to move the ball by on x axis on each move
	 */
	private float movementX;
	/**
	 * amount of pixels to move the ball by on y axis on each move
	 */
	private float movementY;
	/**
	 * default constructor for ball, does not define it's location, as it should be done later
	 * however it sets it's radius to COSTNANT.BALL_RADIUS
	 */
	public Ball()
	{
		width = CONSTANT.BALL_RADIUS*2;
		height = CONSTANT.BALL_RADIUS*2;
		movementX = 0.5f;
		movementY = 0.5f;
	}

	/**
	 * returns width of the ball (2*CONSTANT.BALL_RADIUS)
	 * @return width of the ball
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * returns height of the ball (2*CONSTANT.BALL_RADIUS)
	 * @return the height of the ball
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * returns x coordinate casted to int
	 * @return int position x
	 */
	public int getX()
	{
		return (int)x;
	}

	/**
	 * returns y coordinate casted to int
	 * @return int position y
	 */
	public int getY()
	{
		return (int)y;
	}

	/**
	 * get float X coordinate
	 * @return actual X coordinate
	 */
	public float getRealX()
	{
		return x;
	}

	/**
	 * get float X coordinate
	 * @return actual X coordinate
	 */
	public float getRealY()
	{
		return y;
	}
	/**
	 * set movementX to x and movementY to y if possible if not sets max range
	 * @param x has to be between -1 and 1
	 * @param y has to be between -1 and 1
	 */
	public void setMovement(float x, float y)
	{
		this.movementX = x>1?1f:(x<-1?-1f:x);
		this.movementY = y>1?1f:(y<-1?-1f:y);
	}
	/**
	 * moves the ball by movementX and movementY
	 */
	public void move()
	{
		x += movementX;
		y -= movementY;	
	}
	/**
	 * bounce the ball horizontally 
	 */
	public void bounceHorizontal()
	{
		movementX *= -1;
	}

	/**
	 * bounce the ball vertically 
	 */
	public void bounceVertical()
	{
		movementY *= -1;
	}
	/**
	 * set new x coordinate
	 * @param x new x coordinate, has to be positive
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * set new y coordinate
	 * @param y new y coordinate, has to be positive
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	/**
	 * @return x movement
	 */
	public float getMovementX()
	{
		return movementX;
	}
	/**
	 * @return y movement
	 */
	public float getMovementY()
	{
		return movementY;
	}
}