package com.gptx.climbofduty2.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * Represents a bullet
 *
 */
public class Bullet extends GameObject {
	
	
	
	private double angle;
	
	private boolean firstUpdate = true;
	
	/**
	 * Constructs a new Bullet
	 * @param x x-coordinate of the Bullet
	 * @param y y-coordinate of the Bullet
	 * @param width width of the Bullet
	 * @param height height of the Bullet
	 * @param xVel velocity in the x direction of the Bullet
	 * @param yVel velocity in the y direction of the Bullet
	 */
	public Bullet(int x, int y, int width, int height, float vel) {
		super(x, y, width, height, vel,0f);
		super.setMu(1);
		super.setG(0);
		this.angle = 0;
	}
	
	public Bullet(int x, int y, int width, int height, float vel, float angle)
	{
		super(x, y, width, height, vel, 0f);
		
		this.setMu(1);
		this.setG(0);
		
		float xVel = (vel * (float)Math.cos(angle));
		float yVel = (float)(-vel * Math.sin(angle));
		
		this.setxVel(xVel);
		this.setyVel(yVel);
		
		this.angle = angle;
	}
	
	@Override
	public void update()
	{
		if(firstUpdate) {
			firstUpdate = false;
			return;
		}
		else
			super.update();
	}
	@Override
	public void draw(Graphics g, ImageObserver io)
	{
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(new Color(228,188,60));
		
		g2.drawLine(this.getX(), this.getY(), this.getX() + 4, this.getY());
		g2.drawLine(this.getX(), this.getY() + 1, this.getX() + 4, this.getY() + 1);
	
	}
	

}
