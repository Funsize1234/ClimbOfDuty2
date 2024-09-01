package com.gptx.climbofduty2.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Ammo extends Consumable{
	
	private int x;
	private int y;
	
	public Ammo(int x, int y)
	{
		super(x,y,20,20,null,6);
		
		this.x = x;
		this.y = y;
	}
	
	
	public void draw(Graphics g, ImageObserver io)
	{
		g.setColor(Color.blue);
		g.fillRect(x, y, 20, 20);
	}
	
	
	
}
