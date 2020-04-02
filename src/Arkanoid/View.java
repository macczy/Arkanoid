package Arkanoid;

import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
/**
 * class designed to display Arkanoid
 * @author Maciej
 */
public class View extends JPanel
{
	private static final long serialVersionUID = -6125245672390206448L;
	/**
	 * width of the main panel
	 */
	private final int width = 1200;
	/**
	 * height of the panel
	 */
	private final int height = 800;
	/**
	 * JPanel holding the banner in which the score, main message and hearts are displayed
	 */
	private JPanel banner;
	/**
	 * list holding the hearts
	 */
	private ArrayList<JLabel> hearts;
	/**
	 * text field to display the points of the player
	 */
	private JTextField points;
	/**
	 * text field to display messages for the player
	 */
	private JTextField mainMessage;
	/**
	 * main frame to hold game content
	 */
	private JFrame mainFrame;
	/**
	 * reference to the ball, should be given by model
	 * if not null will be painted
	 */
	private Ball ball;
	/**
	 * reference to the paddle, should be given by model
	 * if not null will be painted
	 */
	private Paddle paddle;
	/**
	 * reference to the list of all blocks, should be given by model
	 * if not null will be painted
	 */
	private ArrayList<Block> blocks;
	/**
	 * constructor setting up all needed fields and the mainFrame to display the game
	 * it does not initiate hearts as it should be called by another module once the number of lives has been decided
	 */
	public View()
	{
		mainFrame = new JFrame();
		setLayout(null);
		mainFrame.setTitle("Arkanoid");
		initBanner();
		setFocusable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setMinimumSize(new Dimension(1214, 830));
		mainFrame.getContentPane().setBackground(Color.WHITE);
		initPoints();
		initMainMessage();
		mainFrame.add(this);
		mainFrame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, 0);
		mainFrame.setVisible(true);
	}
	/**
	 * initiate massage in the banner
	 */
	private void initMainMessage()
	{
		mainMessage = new JTextField();
		mainMessage.setText("Welcome to Arkanoid! Press space to start");
		mainMessage.setHorizontalAlignment(SwingConstants.CENTER);
		mainMessage.setBounds(0,0,getWidth(),CONSTANT.HEIGHT);
		mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
		mainMessage.setOpaque(false);
		mainMessage.setBorder(null);
		banner.add(mainMessage);
		mainMessage.repaint();
	}
	/**
	 * change message to one showing that the game has been lost
	 */
	private void gameOverScreen()
	{
		mainMessage.setText("GAME OVER! Press space to try again");
		mainMessage.repaint();
	}
	/**
	 * initiate banner
	 */
	private void initBanner()
	{
		banner = new JPanel();
		banner.setSize(new Dimension(getWidth(), CONSTANT.HEIGHT));
		banner.setMaximumSize(new Dimension(getWidth(), CONSTANT.HEIGHT));
		banner.setBackground(Color.LIGHT_GRAY);
		banner.setLocation(0,0);
		banner.setLayout(null);
		add(banner);
	}
	/**
	 * get a filled heart
	 * @return icon with a filled heart
	 */
	private ImageIcon getFilledHeart()
	{
		ImageIcon heart = createImageIcon("filledHeart.png", "a filled heart");
		return heart;
	}

	/**
	 * get an empty heart
	 * @return icon with an empty heart
	 */
	private ImageIcon getEmptyHeart()
	{
		ImageIcon heart = createImageIcon("emptyHeart.png", "an empty heart");
		return heart;
	}
	/**
	 * gets an icon from the disk
	 * @param path - path to the file
	 * @param description - description of the icon
	 * @return the image icon or null if it couldnt open the file
	 */
	private ImageIcon createImageIcon(String path, String description) 
	{
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) 
		{
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	/**
	 * initiate field to print points
	 */
	private void initPoints() //points
	{
		points = new JTextField("Score: " + 0);
		points.setBounds(0,0,100,CONSTANT.HEIGHT);
		points.setFont(new Font("SansSerif", Font.PLAIN, 15));
		points.setHorizontalAlignment(SwingConstants.CENTER);
		points.setOpaque(false);
		points.setBorder(null);
		banner.add(points);
	}
	
	/**
	 * paints the blocks, ball and the paddle
	 * color of the blocks depend on it's level and is as follows:
	 * 
	 * paints a single blocks
	 * color depending on level
	 * 1 - cyan
	 * 2 - blue
	 * 3 - green
	 * 4 - yellow
	 * 5 - orange
	 * 0 or higher than 5 - does not fill the rectangle, only draws white frame		 
	 */
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);	
		Graphics2D g2d = (Graphics2D) g;
		if(ball!=null)
		{
		    Ellipse2D.Double circle = new Ellipse2D.Double(ball.getX(), ball.getY(), CONSTANT.BALL_RADIUS*2, CONSTANT.BALL_RADIUS*2);
	        g2d.setColor(Color.MAGENTA);
	        g2d.fill(circle);	       
		}
        if(paddle!=null)
        {
    		g2d.setColor(Color.LIGHT_GRAY);
    		g2d.fillRect(0, paddle.getY(), this.width, paddle.getHeight());
    		g2d.setColor(Color.BLACK);
    		g2d.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        }
        if(blocks!=null)
        {
        	for(int i = 0; i<blocks.size(); i++)
        	{
        		Block block = blocks.get(i);
        		
	        	switch (block.getLevel()) 
	    		{
	    			case 1:
	    				g2d.setColor(Color.CYAN);
	    				break;
	    			case 2:
	    				g2d.setColor(Color.BLUE);
	    				break;
	    			case 3:
	    				g2d.setColor(Color.GREEN);
	    				break;
	    			case 4:
	    				g2d.setColor(Color.YELLOW);
	    				break;
	    			case 5:
	    				g2d.setColor(Color.ORANGE);
	    				break;
	    			default:
	    				return;
	    		}
	    		g2d.fillRect(block.getX(), block.getY(), CONSTANT.WIDTH, CONSTANT.HEIGHT);
	    		g2d.setColor(Color.WHITE);
	    		g2d.drawRect(block.getX(), block.getY(), CONSTANT.WIDTH, CONSTANT.HEIGHT);
        	}
        }
	}
	/**
	 * clear main message
	 */
	public void clearMessage()
	{
		mainMessage.setText("");
		mainMessage.repaint();
	}
	/**
	 * change message to one showing that the level has been won
	 */
	public void gameWon()
	{
		mainMessage.setText("Level won! Press space to advance to the next level");
		repaint();
	}
	/**
	 * initiates graphic representation of the lives the player has by showing heart icons on the left side of the banner
	 * @param numberOfLives - number of hearts to print
	 */
	public void initHearts(int numberOfLives)
	{
		hearts = new ArrayList<JLabel>();
		for(int i = 0; i<numberOfLives; i++)
		{
			ImageIcon icon = getFilledHeart();
			JLabel heart;
			heart = new JLabel("Heart", icon, JLabel.LEFT);
			heart.setSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
			heart.setLocation(getWidth() - (int)((2*i+1.5)*icon.getIconWidth()), (CONSTANT.HEIGHT-icon.getIconHeight())/2);
			banner.add(heart);
			hearts.add(heart);
		}
	}
	/**
	 * updates image of a filled heart to an empty one, basic on the number of lives lost
	 * @param indexOfLife that should have the icon changed to the empty
	 */
	public void lifeLost(int indexOfLife)
	{
		if(indexOfLife>=0)
		{
			hearts.get(indexOfLife).setIcon(getEmptyHeart());
		}
		else
		{
			gameOverScreen();
		}
	}
	
	/**
	 * refills hearts in the corner, depending on total number of Lives
	 * @param numberOfLives to reset back to filledHeart
	 */
	public void resetGame(int numberOfLives)
	{
		for(int i = 0; i<numberOfLives; i++)
		{
			hearts.get(i).setIcon(getFilledHeart());
		}
		mainMessage.setText("");
	}
	/**
	 * returns width
	 * @return width of the main panel
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * gets height
	 * @return the height of the main panel
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * update points
	 * @param p - number of current points
	 */
	public void updatePoints(int p)
	{
		points.setText("Score: " + p);
	}
	
	/**
	 * starts showing the paddle given
	 * @param paddle reference to the paddle that should be displayed
	 */
	public void addPaddle(Paddle paddle)
	{
		this.paddle = paddle;
	}
	/**
	 * start showing the ball given
	 * @param ball - reference to the ball that should be displayed
	 */
	public void addBall(Ball ball)
	{
		this.ball = ball;
	}
	/**
	 * start showing the list of blocks
	 * @param blocks - reference to the list of all blocks that should be tracked and displayed
	 */
	public void addBlocks(ArrayList<Block> blocks)
	{
		this.blocks = blocks;
	}
	/**
	 * ask view to refresh components
	 */
	public void refresh()
	{
		repaint();
	}
}