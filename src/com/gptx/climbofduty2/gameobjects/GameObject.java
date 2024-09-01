package com.gptx.climbofduty2.gameobjects;
import java.awt.*;
import java.awt.image.*;


/**
 * Superclass for all characters/objects in the game
 * 
 * @author Sanatan Mishra
 *
 */
public class GameObject {

	private double g = 0.4;
	private double mu = 0.9; 
	public static final int FLOOR = 1900; // Change to 3000 once rest of the platforms added
	
	private int width, height;
	
	private float x, y;
	
	private float xVel, yVel;
	
	private Image appearance;
		
	/**
	 * Constructs a GameObject object with an initial velocity of 0 in both the x and y directions
	 * @param x initial x-coordinate of object
	 * @param y initial y-coordinate of object
	 * @param width width of object
	 * @param height height of object
	 */
	public GameObject(int x, int y, int width, int height)
	{
		this(x, y, width, height, 0, 0);
	}
	
	/**
	 * Complete constructor of GameObject class
	 * @param x initial x-coordinate of object
	 * @param y initial y-coordinate of object
	 * @param width width of object
	 * @param height height of object
	 * @param xVel initial velocity in x-direction
	 * @param yVel initial velocity in y-direction
	 */
	public GameObject(int x, int y, int width, int height, float xVel, float yVel) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	
	/**
	 * Method to check if this GameObject collides with another
	 * @param other other object to check if the objects collide
	 * @return true if the objects collide, false otherwise
	 */
	public boolean collides(GameObject other) {
		Rectangle r1 = new Rectangle((int)x,(int)y,width,height);
		Rectangle r2 = new Rectangle((int)other.x,(int)other.y,other.width,other.height);
		
		if (r1.intersects(r2)) { 
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Draws this GameObject
	 * @param g Graphics object to draw with
	 * @param io ImageObserver for the sprite image
	 */
	public void draw(Graphics g, ImageObserver io) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(appearance, (int)x, (int)y, io);
		
		drawHitBox(g, io);
	}
	
	public void draw(Graphics g, ImageObserver io, int offsetx, int offsety) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(appearance, (int)x + offsetx, (int)y - offsety, io);
		
		 drawHitBox(g, io);
	}
	
	public void drawHitBox(Graphics g, ImageObserver io)
	{
		g.setColor(Color.BLACK);
		g.drawRect((int)x, (int)y, width, height);
	}
	
	/**
	 * Updates the location of the GameObject based on its velocity and applies frictional and gravitational forces.
	 */
	public void update()
	{
		this.x += xVel;
		this.y += yVel;
		
		yVel += g;
		xVel *= mu;
		
		//roundoff error fix
		if(Math.abs(xVel) < 1)
		{
			xVel = 0;
		}
		
		if (this.y > FLOOR) {
			this.yVel = 0;
		}
	}
	
	/**
	 * Sets frictional multiplier for this GameObject
	 * @param newMu new frictional multiplier
	 */
	protected void setMu(double newMu)
	{
		mu = newMu;
	}
	
	/**
	 * Sets gravity for this GameObject
	 * @param newG new gravitational acceleration
	 */
	protected void setG(double newG)
	{
		g = newG;
	}
	
	public double getG()
	{
		return g;
	}
	
	/**
	 * Returns the x-coordinate of this GameObject
	 * @return x-coordinate of this GameObject
	 */
	public int getX()
	{
		return (int)x;
	}
	
	/**
	 * Returns the y-coordinate of this GameObject
	 * @return y-coordinate of this GameObject
	 */
	public int getY()
	{
		return (int)y;
	}
	
	/**
	 * Returns the velocity in the x-direction of this GameObject
	 * @return velocity in the x-direction of this GameObject
	 */
	public float getxVel()
	{
		return this.xVel;
	}
	
	/**
	 * Returns the velocity in the x-direction of this GameObject
	 * @return velocity in the x-direction of this GameObject
	 */
	public float getyVel()
	{
		return this.yVel;
	}
	
	/**
	 * Sets the velocity in the x-direction of this GameObject
	 * @param v the new velocity in the x-direction of this GameObject
	 */
	public void setxVel(float v)
	{
		this.xVel = v;
	}
	
	/**
	 * Sets the velocity in the y-direction of this GameObject
	 * @param v the new velocity in the y-direction of this GameObject
	 */
	public void setyVel(float v)
	{
		this.yVel = v;
	}
	
	/**
	 * Returns the width of this GameObject
	 * @return width of this GameObject
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of this GameObject
	 * @return height of this GameObject
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Sets the height of this GameObject
	 * @param height the height of this GameObject
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
	/**
	 * Returns the sprite appearance of this GameObject in the form of an Image
	 * @return the sprite image of this GameObject
	 */
	public Image getAppearance() {
		return appearance;
	}
	
	/**
	 * Sets the sprite appearance of this GameObject in the form of an Image
	 * @param bi the new sprite image of this GameObject
	 */
	public void setAppearance(Image bi) {
		appearance = bi;
	}
	
	/**
	 * Sets the x-coordinate of this GameObject
	 * @param newX the x-coordinate of this GameObject
	 */
	public void setX(float x2) {
		this.x = x2;
	}
	
	/**
	 * Sets the y-coordinate of this GameObject
	 * @param newY the y-coordinate of this GameObject
	 */
	public void setY(float y2) {
		this.y = y2;
	}

		
}
