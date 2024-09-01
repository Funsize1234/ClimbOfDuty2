package com.gptx.climbofduty2.gameobjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.gptx.climbofduty2.panel.GamePanel;

import jay.jaysound.JayLayer;

/**
 * Represents a player
 * @author Colin Lou
 */
public class Player extends GameObject {
	
	private static final int MAX_HEALTH = 5, WIDTH = 18, HEIGHT = 64;
	
	public static final int GLOCK = 0, AK = 1;
	
	private Image[][] spriteList;
	private Image currentSprite;
	
	private int shootingtick, appearancetick, tick, coyoteJump, coyoteJumpTick, jumpBuffer, jumpBufferTick, upReqCount, gunInHand, crouchTimer = 20;
	
	private int canRollTick;
	
	private boolean firstRight;
	
	private boolean left, right, up, down, isGrounded, isShooting, isCrouching;
	
	private boolean isLeft, isRight, shootIntrvl, winner, changeHealth, canMoveRight = true, canMoveLeft = true, canJump = true, shotOnce, shootReq;
	
	private int health, bulletsInGlockMag = 5, bulletsInAKMag = 30, rateOfFire;
	
	private JayLayer sound;

	private int currentSpriteIndex;
	
	private float healthBarWidth = MAX_HEALTH * 5;
	
	private Velocity healthVel = new Velocity(0);
	
	public Player(int x, int y, int width, int height, int startingDirection)
	{
		super(x, y, width, height);
		spriteList = new Image[4][15];
		loadSprites();
		currentSprite = spriteList[0][0];
		
		if(startingDirection == 1)
		{
			isLeft = false;
			isRight = true;
		}	
		else if(startingDirection == 2)
		{
			isLeft = true;
			isRight = false;
		}
		
		shootIntrvl = true;
		isGrounded = true;
		
		coyoteJump = 5;
		coyoteJumpTick = 0;
		
		jumpBuffer = 5;
		jumpBufferTick = 0;
		
		health = MAX_HEALTH;

		super.setMu(0.92);
		super.setG(0.6);
		
		sound = new JayLayer("audio/","audio/",false);
		sound.addSoundEffect("gunshotak47.mp3");
		sound.addSoundEffect("grunt.mp3");
		sound.addSoundEffect("emptygun.mp3");
		sound.addSoundEffect("gunshotglock.mp3");
		winner = false;
		
		tick = -1;
		
		this.gunInHand = Player.GLOCK;
		this.rateOfFire = 15;
	}
	
	private void loadSprites()
	{

		for(int i = 0; i < 15; i++)
		{
			spriteList[0][i] = new ImageIcon("sprites/playerR" + (i) + ".png").getImage();
		}
		for(int i = 0; i < 15; i++)
		{
			spriteList[1][i] = new ImageIcon("sprites/playerL" + (i) + ".png").getImage();
		}
		for(int i = 0; i < 15; i++)
		{
			spriteList[2][i] = new ImageIcon("sprites/playerR_AK" + (i) + ".png").getImage();
		}
		for(int i = 0; i < 15; i++)
		{
			spriteList[3][i] = new ImageIcon("sprites/playerL_AK" + (i) + ".png").getImage();
		}
		
		
	}
	
	
	public void draw(Graphics g, ImageObserver io)
	{
		if(gunInHand == Player.GLOCK)
			super.draw(g, io, - 11, 0);
		else if (gunInHand == Player.AK)
			super.draw(g, io, - 25, 0);
		this.drawHealth(g);
	
	}
	
	private void drawHealth(Graphics g)
	{
		healthBarWidth = Interpolation.smoothDamp(healthBarWidth, health * 5, healthVel,0.5f);
		g.setColor(Color.black);
		g.fillRect(this.getX() - 5, this.getY() - 5, 27, 3);
		g.setColor(Color.red);
		g.fillRect(this.getX() - 4, this.getY() - 4, Math.round(healthBarWidth), 1);
	}
	
	public boolean isWinner() {
		return winner;
	}
	
	public boolean getCrouch() {
		return isCrouching;
	}
	
	public void setWinner(boolean isWin) {
		winner = isWin;
	}
	
	public void setLeft(boolean key) {
		left = key;
	}
	
	public void setRight(boolean key) {
		right = key;
	}
	
	public void setUp(boolean key) {
		
		if(key == true)
		{
			if(upReqCount == 0)
				up = true;
			else
				up = false;
			
			upReqCount++;
		}
		
		if (key == false)
		{
			up = false;
			upReqCount = 0;
		}
	}
	
	public void setDown(boolean key) {
		down = key;
	}
	
	public void update()
	{
		this.updateInput();
		this.updateAppearance();
		this.updateShootingTick();
		super.setAppearance(currentSprite);
		
		if(changeHealth && tick < 30)
		{
			if(tick < 0)
				decreaseHealth();
			tick++;
		}
		else if(changeHealth)
		{
			decreaseHealth();
			tick = 0;
		}
	
		super.update();	
	}
	
	private void updateInput()
	{
		if(this.isGrounded)
			coyoteJumpTick = coyoteJump;
		else
			coyoteJumpTick--;
		
		if(up)
		{
			this.jumpBufferTick = this.jumpBuffer;
		}
		else
		{
			this.jumpBufferTick--;
		}
		
		//System.out.println(upReqCount);
		
		if (jumpBufferTick > 0 && coyoteJumpTick > 0 && canJump && !isCrouching) 
		{	
			super.setyVel(-12);
			coyoteJumpTick = 0;
			jumpBufferTick = 0;
		}
		
	
			
		if (right && checkBoundsRight() && canMoveRight && ((!isCrouching) || (!isGrounded)))
		{
			if(firstRight)
			{
				firstRight = false;
				
				if(canRollTick < 10)
				{
					super.setX(super.getX() + 100);
				}
				canRollTick = 0;
			}
			
			super.setxVel(4);
		}
		
		if(canRollTick < 10)
			canRollTick++;
		
		  if(!right)
		{
			firstRight = true;;
		}
	
		
		System.out.println(" rollTick " + canRollTick);
	
		if (left && checkBoundsLeft() && canMoveLeft &&((!isCrouching) || (!isGrounded))) 
			super.setxVel(-4);
		
		
		
		if(down)
		{
			if(!isGrounded)
				this.setG(1.5);
			else if(isGrounded && !isCrouching)
			{
				this.isCrouching = true;
				this.setY(this.getY() + 15);
				this.setHeight(HEIGHT - 15);
				crouchTimer--;
			}
			
			else if(isCrouching)
				crouchTimer--;
		}
		else
		{
			this.setG(0.65);
			
			if(isCrouching)
			{
				if(crouchTimer > 0)
					crouchTimer--;
				else
				{
					this.isCrouching = false;
					crouchTimer = 20;
					this.setY(this.getY() - 15);
					this.setHeight(HEIGHT);
				}
			}
		}
		
		if(this.shootReq)
		{
			if(gunInHand == Player.GLOCK)
				shootGlock();
			else if(gunInHand == Player.AK)
				shootAK();
		}
		else
			shotOnce = false;
	
	}
	
	private void updateShootingTick()
	{
		//delay shooting
		if(isShooting && shootingtick < rateOfFire)
		{
			shootingtick++;
			shootIntrvl = false;	
				 	
		}
		else
		{
			shootIntrvl = true;	
			isShooting = false;
			shootingtick = 0;
		}
	}
	
	public void requestShoot(boolean req)
	{
		this.shootReq = req;
	}
	
	public void shootGlock()
	{
		if(shootIntrvl && !shotOnce && bulletsInGlockMag > 0)
		{
			sound.playSoundEffect(3);
			
			float angle;
			
			if(isCrouching)
			{
				angle = 0;
			}
			else if(getxVel() == 0 && isGrounded)
			{
				angle = (float)Math.toRadians((Math.random() - 0.5) * 2);
			}
			else if(getxVel() != 0 && isGrounded)
			{
				angle = (float)Math.toRadians((Math.random() - 0.5) * 4);
			}
			else
			{
				angle = (float)Math.toRadians((Math.random() - 0.5) * 6);
			}
			
			if(isRight)
			{
				Bullet b = new Bullet(super.getX() + 30 + (int)super.getxVel(), super.getY() + 12, 4, 2, super.getxVel() + 10, angle);
				GamePanel.objects.add(b);
				isShooting = true;
		
			}
		
			else if(isLeft)
			{
				Bullet b = new Bullet(super.getX() - 16 + (int)super.getxVel(), super.getY() + 12, 4, 2, super.getxVel() - 10, angle);
				GamePanel.objects.add(b);
				isShooting = true;
			}
			
			bulletsInGlockMag--;
			shotOnce = true;
			
		}
		else if(shootIntrvl && !shotOnce)
		{
			sound.playSoundEffect(2);
			isShooting = true;
			shotOnce = true;
		}

	}
	
	public void shootAK()
	{
		if(shootIntrvl && bulletsInAKMag > 0)
		{
			sound.playSoundEffect(0);
			
			float angle;
			
			if(isCrouching)
			{
				angle = 0;
			}
			else if(getxVel() == 0 && isGrounded)
			{
				angle = (float)Math.toRadians((Math.random() - 0.5) * 3);
			}
			else if(getxVel() != 0 && isGrounded)
			{
				angle = (float)Math.toRadians((Math.random() - 0.5) * 7);
			}
			else
			{
				angle = (float)Math.toRadians((Math.random() - 0.5) * 10);
			}
			
			
			if(isRight)
			{
				Bullet b;
				if(isCrouching)
					b = new Bullet(super.getX() + 44 + (int)super.getxVel(), super.getY() + 15, 4, 2, super.getxVel() + 15, angle);
				else
					b = new Bullet(super.getX() + 44 + (int)super.getxVel(), super.getY() + 16, 4, 2, super.getxVel() + 15, angle);
				GamePanel.objects.add(b);
				isShooting = true;
		
			}
		
			else if(isLeft)
			{
				Bullet b;
				if(isCrouching)			
					b = new Bullet(super.getX() - 30 + (int)super.getxVel(), super.getY() + 15, 4, 2, super.getxVel() - 15, angle);
				else
					b = new Bullet(super.getX() - 30 + (int)super.getxVel(), super.getY() + 16, 4, 2, super.getxVel() - 15, angle);
				GamePanel.objects.add(b);
				isShooting = true;
			}
			
			bulletsInAKMag--;
			
		}
		else if(shootIntrvl)
		{
			this.setGun(Player.GLOCK);
		}
	}
	
	private void updateAppearance() {
		
		//update direction
		if(super.getxVel() > 0)
		{
			this.isRight = true;
			this.isLeft = false;
		}
		else if(super.getxVel() < 0)
		{
			this.isRight = false;
			this.isLeft = true;;
		}
		
	
		//update sprite direction and shoot
		if(isRight)
		{
			if(!isCrouching)
			{
				
				if(getxVel() != 0 && isGrounded)
				{
					
					if(appearancetick < 8)
					{
						appearancetick++;
					}
					else
					{
						
						currentSpriteIndex++;
						if(currentSpriteIndex > 3)
							currentSpriteIndex = 0;
						appearancetick = 0;
					}
					
					if(isShooting)
					{
						if(gunInHand == Player.GLOCK)
							currentSprite = spriteList[0][currentSpriteIndex + 6];
						else if(gunInHand == Player.AK)
							currentSprite = spriteList[2][currentSpriteIndex + 6];
					}
					else
					{
						if(gunInHand == Player.GLOCK)
							currentSprite = spriteList[0][currentSpriteIndex + 1];
						else if(gunInHand == Player.AK)
							currentSprite = spriteList[2][currentSpriteIndex + 6];
					}
				
				}
				else
				{
					if(isShooting)
					{
						if(gunInHand == Player.GLOCK)
							currentSprite = spriteList[0][5];
						else if (gunInHand == Player.AK)
							currentSprite = spriteList[2][5];
					}
					
					else
					{
						if(gunInHand == Player.GLOCK)
						currentSprite = spriteList[0][0];
					else if(gunInHand == Player.AK)
						currentSprite = spriteList[2][5];
					}
					
				}
			}
			else if(isCrouching)
			{
				if(isShooting)
				{
					if(gunInHand == Player.GLOCK)
						currentSprite = spriteList[0][11];
					else if (gunInHand == Player.AK)
						currentSprite = spriteList[2][11];
				}
				else
				{
					if(gunInHand == Player.GLOCK)
						currentSprite = spriteList[0][10];
					else if (gunInHand == Player.AK)
						currentSprite = spriteList[2][11];
				}
			}
		}
		else if (isLeft)
		{
			if(!isCrouching)
			{
				
				if(getxVel() != 0 && isGrounded)
				{
					if(appearancetick < 8)
					{
						appearancetick++;
					}
					else
					{
						
						currentSpriteIndex++;
						if(currentSpriteIndex > 3)
							currentSpriteIndex = 0;
						appearancetick = 0;
					}
					
					if(isShooting)
					{
						if(gunInHand == Player.GLOCK)
							currentSprite = spriteList[1][currentSpriteIndex + 6];
						else if(gunInHand == Player.AK)
							currentSprite = spriteList[3][currentSpriteIndex + 6];
					}
					else
					{
						if(gunInHand == Player.GLOCK)
							currentSprite = spriteList[1][currentSpriteIndex + 1];
						else if(gunInHand == Player.AK)
							currentSprite = spriteList[3][currentSpriteIndex + 6];
					}
				}
				else
				{
					if(isShooting)
					{
						if(gunInHand == Player.GLOCK)
							currentSprite = spriteList[1][5];
						else if (gunInHand == Player.AK)
							currentSprite = spriteList[3][12];
					}
					else
					{
						if(gunInHand == Player.GLOCK)
						currentSprite = spriteList[1][0];
					else if(gunInHand == Player.AK)
						currentSprite = spriteList[3][5];
					}
					
				}
			}
			else if(isCrouching)
			{
				if(isShooting)
				{
					if(gunInHand == Player.GLOCK)
						currentSprite = spriteList[1][11];
					else if (gunInHand == Player.AK)
						currentSprite = spriteList[3][11];
				}
				else
				{
					if(gunInHand == Player.GLOCK)
						currentSprite = spriteList[1][10];
					else if (gunInHand == Player.AK)
						currentSprite = spriteList[3][11];
				}
			}
					
		}
	
	}

	
	//returns boolean array with [groundcollision,sidecollision]
	public boolean[] interactWithPlatform(Platform platform)
	{
		
		boolean gcoll = false;
		boolean sidecoll = false;
		
		boolean xbound = this.getX() + this.getWidth() > platform.getX() && this.getX() < platform.getX() + platform.getWidth();
		boolean ybound = this.getY() + this.getHeight() >= platform.getY() && this.getY() <= platform.getY() + platform.getHeight();
		boolean rcdown = this.getY() + this.getHeight() + this.getyVel() >= platform.getY() && this.getY() + this.getHeight() <= platform.getY();
		boolean rcup = this.getY() + this.getyVel() <= platform.getY() + platform.getHeight() && this.getY() >= platform.getY() + platform.getHeight();
		boolean rcright = this.getX() + this.getWidth() + this.getxVel() >= platform.getX() && this.getX() + this.getWidth() <= platform.getX();
		boolean rcleft = this.getX() + this.getxVel() <= platform.getX() + platform.getWidth() && this.getX() >= platform.getX() + platform.getWidth();
		boolean cornerfixtopleft = this.getX() + this.getWidth() == platform.getX() && rcdown;
		boolean cornerfixtopright = this.getX() == platform.getX() + platform.getWidth() && rcdown;
		boolean cornerfixbottom;
		
		if(xbound && (rcdown))
		{
			this.setY(platform.getY() - this.getHeight());
			this.setyVel(0);
			this.setGrounded(true);
			gcoll = true;
		}
		
		if(ybound && rcright || cornerfixtopleft)
		{
			this.setX(platform.getX() - this.getWidth());
			this.setxVel(0);
			sidecoll = true;
			canMoveRight = false;
		}
		
		if(ybound && rcleft || cornerfixtopright)
		{
			this.setX(platform.getX() + platform.getWidth());
			this.setxVel(0);
			sidecoll = true;
			canMoveLeft = false;
		}

		
		if(xbound && rcup)
		{
			this.setY(platform.getY() + platform.getHeight());
			this.setyVel(0);
		}

		return new boolean[] {gcoll,sidecoll} ;
	}
	
	public boolean[] onCollide(GameObject obj)
	{
		boolean enemy = false;
		
		if(obj instanceof Enemy)
		{
			this.beginDecreaseHealth();
			enemy = true;
		}
		
		else if (obj instanceof HealthPowerUp)
		{
			this.increaseHealth();
		}
		
		else if(obj instanceof TeleportPowerUp)
		{
			this.increaseHealth();
		}
		
		else if(obj instanceof Ammo)
		{
			this.resetMagazine();
		}
		else if(obj instanceof GunPowerUp)
		{
			this.setGun(Player.AK);
		}
		
		return new boolean[] {enemy};
	}
	
	public void beginDecreaseHealth()
	{
		this.changeHealth = true;
	}
	public void stopDecreaseHealth()
	{
		this.changeHealth = false;
		tick = -1;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void increaseHealth()
	{
		health++;
		if(health > Player.MAX_HEALTH)
		{
			health = Player.MAX_HEALTH;
		}
	}
	public void decreaseHealth()
	{
		sound.playSoundEffect(1);
		health--;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public void setGrounded(boolean g)
	{
		this.isGrounded = g;
	}
	
	public void setGun(int g)
	{
		if(g == Player.GLOCK)
		{
			gunInHand = Player.GLOCK;
			this.rateOfFire = 15;
			shotOnce = true;
		}
		else if(g == Player.AK)
		{
			gunInHand = Player.AK;
			this.rateOfFire = 6;
		}
	}
	
	public boolean getGrounded()
	{
		return this.isGrounded;
	}
	
	public void resetCanMove()
	{
		this.canMoveLeft = true;
		this.canMoveRight = true;
	}
	
	public void resetMagazine()
	{
		if(this.gunInHand == Player.GLOCK)
			bulletsInGlockMag = 5;
		else if (this.gunInHand == Player.AK)
			bulletsInAKMag = 30;
		shotOnce = false;
	}
	
	public void setCanJump(boolean jump)
	{
		this.canJump = jump;
	}
	
	private boolean checkBoundsRight()
	{
		if(getX() + getWidth() < 960)
		{
			return true;
		}
		else
		{
			this.setX(960 - this.getWidth());
			return false;
		}	
	}
	
	private boolean checkBoundsLeft()
	{
		if (getX() > 0)
		{
			
			return true;
		}
		else
		{
			this.setX(0);
			return false;
		}
		
	}
	

}
