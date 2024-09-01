package com.gptx.climbofduty2.gameobjects;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Consumable extends GameObject 
{
	private Image[] spriteList;
	private Image currentSprite;
	private int tick, spriteIndex;
	private String file;
	
	/**
	 * Represents all consumable items
	 * @param x x-coordinate of the PowerUp
	 * @param y y-coordinate of the PowerUp
	 * @param width width of the PowerUp
	 * @param height height of the PowerUp
	 * @param dir name of file
	 * @param frames number of animation frames
	 */
	public Consumable(int x, int y, int width, int height, String file, int frames) {
		super(x, y, width, height);
		super.setG(0); 
		super.setxVel(0);
		super.setyVel(0);
		super.setMu(1);
		
		this.file = file;
		spriteList = new Image[frames];
		loadSprites();
		currentSprite = spriteList[0];
	}
	
	private void loadSprites()
	{
		for(int i = 0; i < spriteList.length; i++)
		{
			spriteList[i] = new ImageIcon(file + (i) + ".png").getImage();
		}
	}

	public void update()
	{
		super.update();
		
		if(tick < 8)
		{
			tick++;
		}
		else
		{
			if(spriteIndex >= spriteList.length)
				spriteIndex = 0;
			currentSprite = spriteList[spriteIndex];
			spriteIndex++;
			tick = 0;
		}
		
		super.setAppearance(currentSprite);
		
	}

}
