package Arkanoid;


/**The class representing a single block*/
public class Block
{
	/**
	 * level of a block, should be destroyed when level is less than 0
	 */
	private int level;
	/**
	 * x coordinate of the block
	 */
	private final int x;
	/**
	 * y coordinate of the block
	 */
	private final int y;
	/**
	 * The only constructor
	 * @param x coordinate
	 * @param y coordinate
	 * @param level - initial level
	 */
	public Block(int x, int y, int level)
	{
		this.x = x;
		this.y = y;
		if(level > 0 && level <=5) 
		{
			this.level = level;
		}else
		{
			this.level = 0;
		}
	}

	/**
	 * get level of the block
	 * @return level of the block, an integer between 0 and 5
	 */
	public int getLevel()
	{
		return level;
	}
	/**
	 * get x coordinate of the block
	 * @return x position of the block
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * get y coordinate of the block
	 * @return y position of the block
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * lowers the level of a block if possible
	 * @return false if the block should be destroyed and true otherwise
	 */
	public boolean degrade() {
		if(--level<=0)
		{
			return false;
		}
		return true;
	}
}
