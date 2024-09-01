package com.gptx.climbofduty2.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class GunPowerUp extends Consumable {

	public GunPowerUp(int x, int y) {
		super(x, y, 25, 25, "", 1);
	}
	
	@Override
	public void draw(Graphics g, ImageObserver io)
	{
		g.setColor(new Color(100,50,200));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
