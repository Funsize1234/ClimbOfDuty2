package com.gptx.climbofduty2.panel;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import com.gptx.climbofduty2.gameobjects.Interpolation;
import com.gptx.climbofduty2.gameobjects.Player;
import com.gptx.climbofduty2.gameobjects.Velocity;

/**
 * Does zooming, etc. for each player's POV
 * 
 * @author Colin Lou
 *
 */
public class Camera {
	
	private final int WIDTH = 960;
	private final int HEIGHT = 1080;
	
	private float scale, x, y, targetx, targety;
	
	private int tick, interpTime, interpStart, interpEnd;
	
	private String interpType;
	
	private boolean isScaling, isScrolling;
	
	private Player player;
	private int interpStart2;
	private int interpEnd2;
	
	private float boundx, boundy;
	
	private Velocity xVel = new Velocity(0);
	private Velocity yVel = new Velocity(0);
	
	public Camera(Player p)
	{
		this.scale = 1;
		tick = 0;
		this.player = p;
		this.isScaling = false;
		this.x = WIDTH/2;
		this.y = HEIGHT/2;
	}
	
	public void update()
	{	
		
		boundx = (WIDTH/2.0f)/scale;
		boundy = (HEIGHT/2.0f)/scale;

		if(!isScrolling)
		{
			
			if (player.getX() + player.getWidth()/2 <= boundx)
				this.targetx = boundx;
			else if (player.getX() + player.getWidth()/2 >= WIDTH - boundx)
				this.targetx = WIDTH - boundx;
			else
			{
				this.targetx = player.getX() + player.getWidth()/2;
			}
			
			this.x = Interpolation.smoothDamp(this.x, this.targetx, xVel, 0.1f);
			
			
			
			if (player.getY() < boundy)
				this.targety = boundy + player.getHeight()/2;
			else if (player.getY() > 2160 - boundy)
				this.targety = 2160 - boundy;
			else
			{
				this.targety = player.getY() + player.getHeight()/2;
			}
			this.y = Interpolation.smoothDamp(this.y, this.targety, yVel, 0.2f);
		}
	
		
		if(isScrolling && tick < interpTime)
		{
			tick++;
			switch(interpType)
			{
				case "linear":
					x = lerp(this.interpStart, this.interpEnd);
					y = lerp(this.interpStart2, this.interpEnd2);
				case "logarithmic":
					x = logerp(this.interpStart, this.interpEnd);
					y = logerp(this.interpStart2, this.interpEnd2);
					break;
				case "spherical":
					x = slerp(this.interpStart, this.interpEnd);
					y = slerp(this.interpStart2, this.interpEnd2);
					break;
				case "quadratic":
					x = qwerp(this.interpStart, this.interpEnd);
					y = qwerp(this.interpStart2, this.interpEnd2);
					break;
			}
			
			player.setCanJump(false);
		}
		
		if(isScrolling && tick == interpTime - 1)
		{
			isScrolling = false;
			player.setCanJump(true);
		}
		
		
		if(isScaling && tick < interpTime)
		{
			tick++;
			switch(interpType)
			{
				case "logarithmic":
					scale = logerp(this.interpStart, this.interpEnd);
					break;
				case "spherical":
					this.y = slerp(this.interpStart, this.interpEnd);
					break;
				case "quadratic":
					scale = qwerp(this.interpStart, this.interpEnd);
					break;
			}
		}
		else
		{
			isScaling = false;
		}
		
		
	}
	
	public void render(Graphics2D g2)
	{
		g2.scale(scale, scale);
		g2.translate((calculateOffsetX()-x),(calculateOffsetY()-y));
	}
	
	private double calculateOffsetX()
	{
		return (WIDTH/2)/scale;
	}
	
	private double calculateOffsetY()
	{
		return (HEIGHT/2)/scale;
	}
	
	public double getScale()
	{
		return scale;
	}
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	public int getX()
	{
		return (int)this.x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return (int)this.y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	
	
	public void interpScale(int x1, int x2, int time, String type)
	{
		tick = 0;
		this.interpStart = x1;
		this.interpEnd = x2;
		this.interpTime = time;
		this.interpType = type;
		isScaling = true;
	}
	public void interpPos(int x1, int x2, int y1, int y2, int time, String type)
	{
		tick = 0;
		this.interpStart = x1;
		this.interpEnd = x2;
		
		if (x1 <= boundx)
			interpStart = (int) boundx;
		else if (x1 >= WIDTH - boundx)
			interpStart = WIDTH - (int)boundx;
		if (x2 <= boundx)
			interpEnd = (int) boundx;
		else if (x2 >= WIDTH - boundx)
			interpEnd = WIDTH - (int)boundx;
		
		if (y1 < boundy)
			interpStart2 = (int) boundy;
		if (y2 < boundy)
			interpEnd2 = (int) boundy;
		
		this.interpStart2 = y1;
		this.interpEnd2 = y2;
		this.interpTime = time;
		this.interpType = type;
		isScaling = false;
		isScrolling = true;
	}
	
	private float slerp(int x1, int x2)
	{
		return x1 + (x2 - x1) * (float)Math.sqrt(1 - Math.pow(((float)(tick)/interpTime) - 1, 2));
	}
	
	private float logerp(int x1, int x2)
	{
		double logStart = Math.log(x1);
		double logEnd = Math.log(x2);
		
		return (float)Math.exp(logStart + (logEnd - logStart) * ((float)tick/(float)interpTime));
	}
	
	private float qwerp(int x1, int x2)
	{
		return x1 + (x2 - x1) * (float)Math.pow(((float)tick/(float)interpTime), 2);
	}
	
	private float lerp(int x1, int x2)
	{
		return x1 + (x2 - x1) * (float)tick/(float)interpTime;
	}

}
