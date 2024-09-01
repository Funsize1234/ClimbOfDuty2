package com.gptx.climbofduty2.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.gptx.climbofduty2.panel.GamePanel;

public class SmartEnemy extends Enemy {
	
	
	
	private Image[] spriteList;
	private Image currentSprite;

	private int state, followX, followY, speed = 3;
	
	private final int IDLE = 0, FOLLOWING = 1, FOLLOWING_DISTANCE = 300;

	
	private boolean left, right, canShoot;
	private int shootingTick, currentIndex, difficulty, appearanceTick;

	/**
	 * Constructs a new SmartEnemy, which is an intelligent antagonist for the players that can follow them around and shoot at them
	 * @param x x-coordinate of the SmartEnemy
	 * @param y y-coordinate of the SmartEnemy
	 * @param width width of the SmartEnemy
	 * @param height height of the SmartEnemy
	 * @param difficulty time between shots
	 */
	
	public SmartEnemy(int x, int y, int width, int height, int difficulty, String loc, int ticks) {
		super(x, y, width, height, 0,0, loc, ticks);
		canShoot = true;
		shootingTick = 0;
		spriteList = new Image[3];
		this.difficulty = difficulty;
		
		super.setG(0);
		super.setMu(1);
	}
	
	public void update() {
		
		super.update();
	
	
		if(getDistanceFrom(followX,followY) < this.FOLLOWING_DISTANCE) {
			state = FOLLOWING;
		}
		else {
			state = IDLE;
		}
		
		if(state == FOLLOWING)
		{
			
			if(followX < getX())
			{
				left = true;
				right = false;
			}
			else if (followX > getX())
			{
				right = true;
				left = false;
			}
			
			if (shootingTick < difficulty) 
			{
				shootingTick++;
			}
			else 
			{
				GamePanel.objects.add(this.shoot());
				shootingTick = 0;
			}
			
			
			this.setX((int)Interpolation.lerp(this.getX(), followX, .02f));
			this.setY((int)Interpolation.lerp(this.getY(), followY, .02f));
			//this.setX((int)Interpolation.goTo(this.getX(), followX, speed));
			//this.setY((int)Interpolation.goTo(this.getY(), followY, speed));
			
		}
		else if(state == IDLE)
		{
			
		}

	}
	
	public void setTrackingLoc(int x, int y)
	{
		this.followX = x;
		this.followY = y;
	}
	
	private double getDistanceFrom(int x, int y)
	{
		double deltaX = Math.abs(this.getX() + this.getWidth()/2 - x);
		double deltaY = Math.abs(this.getY() + this.getHeight()/2 - y);
		double distance = Math.hypot(deltaX, deltaY);
		return distance;	
	}
	
	private float getAngleFrom(int x, int y)
	{
		float deltaX = x - (this.getX() + this.getWidth()/2);
		float deltaY = this.getY() + this.getHeight()/2 - y;
	
		float constrainedangle = (float)Math.atan(deltaY/deltaX);
		
		if(deltaX < 0)
		{
			return constrainedangle + (float)Math.PI;
		}
		
		else
			return constrainedangle;
		
	}
	
	
	/**
	 * Enables the SmartEnemy to reload its Bullets
	 * @return Bullet that will let the SmartEnemy shoot at Players 
	 */
    public Bullet shoot() {	
		//sound.playSoundEffect(0);
    	Bullet bullet;
    	if(left)
    	{
    		bullet = new Bullet(this.getX() - 10, this.getY() + this.getHeight()/2, 4, 2, getxVel() - 15, 0);
    	}
    	else
    	{
    		bullet = new Bullet(this.getX() + getWidth() + 10, this.getY() + this.getHeight()/2, 4, 2, getxVel() + 15, 0);
    	}

    	return bullet;
	}

    
    @Override
    public void draw(Graphics g, ImageObserver io)
    {
    	super.draw(g, io);
    	
    	if(state == FOLLOWING)
    	{
    		g.setColor(Color.red);
    		g.drawLine(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2, followX, followY);
    	}

    }

}
