package com.gptx.climbofduty2.gameobjects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * Represents an enemy/obstacle
 * 
 * @author Sanatan Mishra
 *
 */
public class Enemy extends GameObject {
	
	private Image[] spriteList;
	private Image currentSprite;
	private int appearanceTick;
	private int currentIndex;
	
	private String loc;

	
	/**
	 * Constructs a new Enemy/antagonist for the players
	 * @param x x-coordinate of the Enemy
	 * @param y y-coordinate of the Enemy
	 * @param width width of the Enemy
	 * @param height height of the Enemy
	 * @param xVel velocity in the x direction of the Enemy
	 * @param yVel velocity in the y direction of the Enemy
	 */
	public Enemy(int x, int y, int width, int height, int xVel, int yVel, String loc, int ticks) {
		super(x, y, width, height, xVel, yVel);
		super.setG(0); 
		super.setMu(1);
		this.loc = loc;
		this.spriteList = new Image[ticks];
		loadSprites();
		appearanceTick = 0;
	}
	
	private void loadSprites()
	{
		for(int i = 0; i < spriteList.length; i++)
		{
			spriteList[i] = new ImageIcon(loc + (i) + ".png").getImage();
		}
	}
	
	private void updateAppearance()
	{
		if (loc == null) {
			return;
		}
		if(appearanceTick < 5)
		{
			appearanceTick++;
		}
		else
		{
			if(currentIndex + 1 >= spriteList.length)
				currentIndex = -1;
			currentSprite = spriteList[currentIndex + 1];
			currentIndex++;
			appearanceTick = 0;
		}
		
		
		super.setAppearance(currentSprite);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		
		if(super.getX() > 960 - super.getWidth())
		{
			super.setX(960 - super.getWidth());
		}
		else if(super.getX() < 0)
		{
			super.setX(0);
        }
		updateAppearance();
	}

}
