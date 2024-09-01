package com.gptx.climbofduty2.panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import jay.jaysound.JayLayer;

import com.gptx.climbofduty2.gameobjects.*;

/**
 * Class that handles the game
 * 
 * @author Sanatan Mishra
 */
public class GamePanel extends JPanel implements KeyListener {
	
	private int fps, ups, mode;
	private double nextFPSTime;
	private Player p1;
	private Player p2;
	private Camera c1;
	private Camera c2;
	private Camera winner;
	public static ArrayList<GameObject> objects;
	
	Image background;
	private JayLayer sound;
	private int pList1, pList2;
	
	public static final int MAX_LEVEL = 16; // change this later
	
	BufferedImage backBuffer1, backBuffer2;
	Graphics2D g2_1, g2_2;
	
	private PanelHandler p;
	
	private boolean running;
	private int currentFPS;
	private int currentUPS;
	
	private int winTick;
	private int enemyShootDistance;

	
	/**
	 * Constructor to construct a GamePanel object
	 * @param p PanelHandler object that covers the whole game
	 */
	public GamePanel(PanelHandler p)
	{
		super();      
		this.p = p; 
	}
	
	public void initialize(int mode, int diff)
	{
		this.mode = mode;
		running = false;
		p1 = new Player(0,1836,18,64, 1);
		if(mode == 1)
			p2 = new Player(960 - 18,GameObject.FLOOR - 64,18,64, 2);
		if(mode == 0)
			p2 = new Player(960 - 18,3000,18,64, 2);
		c1 = new Camera(p1);
		c2 = new Camera(p2);
		
		
		Enemy fire1 = new Enemy(850, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire2 = new Enemy(870, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire3 = new Enemy(890, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire4 = new Enemy(830, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire5 = new Enemy(260, 1100, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire6 = new Enemy(240, 1100, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire7 = new Enemy(220, 1100, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire8 = new Enemy(480, 1760, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire9 = new Enemy(460, 1760, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire10 = new Enemy(960-850-22, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire11 = new Enemy(960-870-22, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire12 = new Enemy(960-890-22, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire13 = new Enemy(960-830-22, 1535, 22, 17, 0, 0, "sprites/fire", 3);
		Enemy fire14 = new Enemy(960-850-22, 1535, 22, 17, 0, 0, "sprites/fire", 3);
				
		Enemy dog1 = new SmartEnemy(220, 1080, 22, 34, 1000000000, "sprites/dog", 2);
		Enemy dog2 = new SmartEnemy(740, 1300, 22, 34, 1000000000, "sprites/dog", 2);
		Enemy dog3 = new SmartEnemy(700, 1080, 22, 34, 1000000000, "sprites/dog", 2);
		SmartEnemy e = new SmartEnemy(650, 450, 22, 17, 60, "sprites/drone", 3);
		SmartEnemy e2 = new SmartEnemy(650, 900, 22, 17, 60, "sprites/drone", 3);
		SmartEnemy e3 = new SmartEnemy(420, 530, 22, 17, 10, "sprites/heli", 2);
		SmartEnemy e4 = new SmartEnemy(120, 820, 22, 17, 10, "sprites/heli", 2);
		SmartEnemy e5 = new SmartEnemy(650, 312, 22, 17, 60, "sprites/drone", 3);
		SmartEnemy e8 = new SmartEnemy(300, 300, 22, 17, 60, "sprites/drone", 3);
		SmartEnemy e6 = new SmartEnemy(480 - 22, 1600, 22, 17, 60, "sprites/drone", 3);
		SmartEnemy e7 = new SmartEnemy(500, 690, 22, 17, 10, "sprites/heli", 2);
		
		
		TeleportPowerUp pu2 = new TeleportPowerUp(900, 1300);
		HealthPowerUp pu3 = new HealthPowerUp(325, 875);
		HealthPowerUp pu4 = new HealthPowerUp(468, 1360);
		HealthPowerUp pu6 = new HealthPowerUp(700, 830);
		HealthPowerUp pu = new HealthPowerUp(320, 320);
		TeleportPowerUp pu8 = new TeleportPowerUp(495, 800);
		TeleportPowerUp pu9 = new TeleportPowerUp(200, 900);
		TeleportPowerUp pu10 = new TeleportPowerUp(60-22, 1300);
		
		Ammo pu11 = new Ammo(100, 1700);
		GunPowerUp gpu = new GunPowerUp(200,1700);

		int distanceBetweenPlatforms = 110;

		Platform d1 = new Platform(900, GameObject.FLOOR - distanceBetweenPlatforms * 6, 100, 10, 6);
		Platform d2 = new Platform(125, GameObject.FLOOR - distanceBetweenPlatforms * 10, 100, 10, 10);
		Platform d3 = new Platform(330, GameObject.FLOOR - distanceBetweenPlatforms * 13, 270, 10, 13);
		Platform d4 = new Platform(260, GameObject.FLOOR - distanceBetweenPlatforms * 12, 100, 10, 12);
		Platform d5 = new Platform(730, GameObject.FLOOR - distanceBetweenPlatforms * 5, 100, 10, 5);
		Platform d6 = new Platform(300, GameObject.FLOOR - distanceBetweenPlatforms * 9, 100, 10, 9);
		Platform d7 = new Platform(100, GameObject.FLOOR - distanceBetweenPlatforms * 7, 760, 10, 7);
		Platform d9 = new Platform(300, GameObject.FLOOR - distanceBetweenPlatforms * 11, 100, 10, 11);
		Platform d10 = new Platform(570, GameObject.FLOOR - distanceBetweenPlatforms * 4, 100, 10, 4);
		Platform d11 = new Platform(700, GameObject.FLOOR - distanceBetweenPlatforms * 3, 200, 10, 3);
		Platform d12 = new Platform(430, GameObject.FLOOR - distanceBetweenPlatforms * 8, 100, 10, 8);
		Platform d13 = new Platform(600, GameObject.FLOOR - distanceBetweenPlatforms * 2, 100, 10, 2);
		Platform d14 = new Platform(360, GameObject.FLOOR - distanceBetweenPlatforms, 240, 10, 1);
		Platform d15 = new Platform(250, GameObject.FLOOR - distanceBetweenPlatforms * 14, 325, 10, 14);
		Platform d16 = new Platform(260, GameObject.FLOOR - distanceBetweenPlatforms * 15, 100, 10, 15);
		Platform d17 = new Platform(200, GameObject.FLOOR - distanceBetweenPlatforms * 16, 100, 10, 16);
		
		Platform d19 = new Platform(960 - 600 - 100, GameObject.FLOOR - distanceBetweenPlatforms * 2, 100, 10, 2);
		Platform d20 = new Platform(960 - 700 - 200, GameObject.FLOOR - distanceBetweenPlatforms * 3, 200, 10, 3);
		Platform d21 = new Platform(960 - 570 - 100, GameObject.FLOOR - distanceBetweenPlatforms * 4, 100, 10, 4);
		Platform d22 = new Platform(960 - 730 - 100, GameObject.FLOOR - distanceBetweenPlatforms * 5, 100, 10, 5);
		Platform d23 = new Platform(960 - 900 - 100, GameObject.FLOOR - distanceBetweenPlatforms * 6, 100, 10, 6);
		
		Platform test = new Platform(200,GameObject.FLOOR  - 120, 60, 40,0);
		
		Platform test2 = new Platform(100,GameObject.FLOOR  - 40, 60, 40,0);
		
		Platform floor = new Platform(0,GameObject.FLOOR, 960, 10,0);

		enemyShootDistance = 125;
		
		objects = new ArrayList<GameObject>();
		
		if(mode == 1)
			objects.add(p2);
		
		
		objects.add(d1);
		objects.add(fire1);
		objects.add(fire2);
		objects.add(fire3);
		objects.add(fire4);
		objects.add(fire5);
		objects.add(fire6);
		objects.add(fire7);
		objects.add(fire8);
		objects.add(fire9);
		objects.add(fire10);
		objects.add(fire11);
		objects.add(fire12);
		objects.add(fire13);
		objects.add(fire14);
		
		if (true) {
			objects.add(dog2);
			objects.add(dog3);
			objects.add(pu);
			objects.add(pu2);
			objects.add(pu3);
			objects.add(pu4);
			this.enemyShootDistance = 200;
		}
		
		objects.add(d2);
		objects.add(d3);
		objects.add(d4);
		objects.add(d5);
		objects.add(d6);
		objects.add(d7);
		objects.add(d9);
		objects.add(d10);
		objects.add(d11);
		objects.add(d12);
		objects.add(d13);
		objects.add(d14);
		objects.add(d15);
		objects.add(d16);
		objects.add(d17);
		objects.add(d19);
		objects.add(d20);
		objects.add(d21);
		objects.add(d22);
		objects.add(d23);
		objects.add(floor);
		
		objects.add(test);
		objects.add(test2);
		
		objects.add(dog1);
		
		if (diff == 0) {
			objects.add(pu);
			objects.add(pu2);
			objects.add(pu3);
			objects.add(pu4);
			objects.add(pu6);
			objects.add(pu8);
			objects.add(pu9);
			objects.add(pu10);
			objects.add(pu11);
			objects.add(gpu);
			this.enemyShootDistance = 75;
		}
	
		objects.add(e);
		objects.add(e2);
		objects.add(e3);
		objects.add(e4);
		objects.add(e5);
		objects.add(e6);
		objects.add(e7);
		objects.add(e8);
		
		
		backBuffer1 = new BufferedImage(960, 1080, BufferedImage.TYPE_INT_ARGB);
		g2_1 = (Graphics2D) backBuffer1.getGraphics();
		
		backBuffer2 = new BufferedImage(960, 1080, BufferedImage.TYPE_INT_ARGB);
		g2_2 = (Graphics2D) backBuffer2.getGraphics();
		
		background = new ImageIcon("sprites/background.png").getImage();
		
		sound = new JayLayer("audio/","audio/",false);
		
		sound.addSoundEffect("boing.mp3");
		sound.addSoundEffect("powerup.mp3");
		
		winTick = 0;
	}
	
	/**
	 * Sets the game to run
	 * @param running whether the game is running or not
	 */
	public void setRunning(boolean running)
	{
		this.running = running;
	}
	
	/**
	 * Runs the game
	 * @post The game is drawn on the screen.
	 */
	public void run()
	{
		long lasttime = System.nanoTime();
		double deltatime = 0;
	
		while(true)
		{
			long currenttime = System.nanoTime();
			deltatime += (long) (currenttime - lasttime);
			lasttime = currenttime;
			
			while(deltatime >= 1000000000.0 / 60.0)
			{
				if(running)
					update();
				deltatime -= 1000000000.0 / 60.0;
			}
			
			if(running)
			{
				drawBackBuffer1();
				//drawBackBuffer2();
				drawFrontBuffer();
			}
				
			
			//updateStats();
		}
	}
	
	/**
	 * The game is started.
	 */
	public void startGame()
	{
		c1.interpScale((int)c1.getScale(), 2, 60, "logarithmic");
		//c2.interpScale((int)c2.getScale(), 2, 60, "logarithmic");
	}

	private void updateStats() {
		if(System.nanoTime() > nextFPSTime)
		{
			System.out.println("Frames Per Second: " + fps + " Updates Per Second: " + ups);
			currentFPS = fps;
			currentUPS = ups;
			fps = 0;
			ups = 0;
			nextFPSTime = System.nanoTime() + 1000000000;
		}
	}
	
	/**
	 * Draws Player 1's perspective of the game
	 * @post Player 1's perspective of the game is drawn.
	 */
	public void drawBackBuffer1()
	{
		AffineTransform at = g2_1.getTransform();
		
		c1.render(g2_1);
		draw(g2_1);
		g2_1.setTransform(at);
	

	}
	
	/**
	 * Draws Player 2's perspective of the game
	 * @post Player 2's perspective of the game is drawn.
	 */
	public void drawBackBuffer2()
	{
		AffineTransform at = g2_2.getTransform();
		
		c2.render(g2_2);
		draw(g2_2);
		g2_2.setTransform(at);
		
	}
	
	/**
	 * Draws the game.
	 * @post The whole game is drawn.
	 */
	public void drawFrontBuffer()
	{
		fps++;
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		if(mode == 1)
		{
			g.drawImage(backBuffer2, getWidth() / 2, 0, getWidth() / 2, getHeight(), this);
			g.drawImage(backBuffer1, 0, 0, getWidth() / 2, getHeight(), this);
		}
		else if (mode == 0)
		{
			this.setBackground(Color.black);
			g.drawImage(backBuffer1, getWidth() / 4, 0, getWidth() / 2, getHeight(), this);
		}
		g.dispose();
	}
	
	private void draw(Graphics2D g2)
	{
        //g2.drawImage(background,0,0,null);
		g2.setColor(Color.white);
		        g2.fillRect(0, 0, 960, 10000);
        
        try {
        	for (GameObject go : objects) {
    			if(go != null)
    				go.draw(g2, this);
    		}
        	
        	p1.draw(g2, this);
        	
        	g2.setColor(Color.black);
        	
        	//p1.drawHitbox(g2);
        	
        } catch (ConcurrentModificationException e) {
        	
        }
	}
	
	/**
	 * Updates everything for the next frame, then flushes a drawing to the screen.
	 * @post GameObjects may be modified, and a frame is flushed to the screen.
	 */
	public void update()
	{
		ups++;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
	
		//collision player on platform
		
		int colltop = 0;
		int collside = 0;	
		int colenemy = 0;
		
		p1.update();
		
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject obj = objects.get(i);
			
			if(obj == null)
			{
				objects.remove(i);
				i--;
			}
			
			if(p1.collides(obj)) 
			{
				if(p1.onCollide(obj)[0] == true)
				{
					colenemy++;
				}
				
				if(obj instanceof Consumable)
				{
					objects.remove(i);
					i--;
				}
			}
			
			if(obj instanceof Platform)
			{
				if(p1.interactWithPlatform((Platform)obj)[0])
				{
					colltop++;
				}
				if(p1.interactWithPlatform((Platform) obj)[1])
				{
					collside++;
				}
			}
			
			if(obj instanceof Bullet)
			{
				if(obj.collides(p1))
				{
					p1.decreaseHealth();
					objects.remove(i);
					i--;
				}
				else if(obj.getX() > 960 || obj.getX() < 0)
				{
					objects.remove(i);
					i--;
				}
				
				
				for(int j = 0; j < objects.size(); j++)
				{
					GameObject otherobj = objects.get(j);
					if(otherobj instanceof Platform && obj.collides(otherobj))
					{
						objects.remove(i);
						i--;
					}
					if(otherobj instanceof Enemy && obj.collides(otherobj))
					{
						objects.remove(i);
						i--;
						objects.remove(j);
						j--;
					}
				}
				
			}
			
			if(obj instanceof SmartEnemy)
			{
				((SmartEnemy) obj).setTrackingLoc((int)(p1.getX() + p1.getWidth()/2), (int)(p1.getY()));
			}
			
			obj.update();
			
		}
		
		if(colltop == 0)
		{
			p1.setGrounded(false);
		}
		if(collside == 0)
		{
			p1.resetCanMove();
		}
		if(colenemy == 0)
		{
			p1.stopDecreaseHealth();
		}
		
		
		this.updatePlayer1Life();
		this.updatePlayer2Life();
		
		if(winTick == 1)
		{
			winner.interpScale((int)winner.getScale(), (int)winner.getScale() + 10, 60, "logarithmic");
		}
		else if(winTick > 60)
		{
			this.running = false;
		}
		
		c1.update();
		//c2.update();
		
		
		toolkit.sync();
			
	}
	
	private void updatePlayer1Life()
	{
		if(p1.getHealth() <= 0)
		{
			p1.setY(GameObject.FLOOR - p1.getHeight());
			p1.setX(0);
			p1.setyVel(0);
			p1.setxVel(0);
			c1.interpPos(c1.getX(), p1.getX() + p1.getWidth()/2, c1.getY(), p1.getY() + p1.getHeight()/2, 120, "spherical");
			p1.setHealth(5);
			p1.setGun(Player.GLOCK);
			p1.resetMagazine();
		}
	}
	
	private void updatePlayer2Life() {
		
		if(p2.getHealth() <= 0)
		{
			p2.setX(960 - p2.getWidth());
			p2.setY(GameObject.FLOOR - p2.getHeight());
			c2.interpPos(c2.getX(), p2.getX() + p2.getWidth()/2, c2.getY(), p2.getY() + p2.getHeight()/2, 120, "spherical");
			p2.setHealth(5);
		}

	}
	


	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_W)
		{
			p1.setUp(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_A)
		{
			p1.setLeft(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			p1.setDown(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_D)
		{
			p1.setRight(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			p1.requestShoot(true);
		}
		
		
		
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			p2.setUp(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			p2.setLeft(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN )
		{
			p2.setDown(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			p2.setRight(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD0 || e.getKeyCode() == KeyEvent.VK_SHIFT)
		{

		}
		
	}

	
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		//p1 controls
		if(e.getKeyCode() == KeyEvent.VK_W)
		{
			p1.setUp(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_A)
		{
			p1.setLeft(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			p1.setDown(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_D)
		{
			p1.setRight(false);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			p1.requestShoot(false);
		}
		
		//p2 controls
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			p2.setUp(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			p2.setLeft(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			p2.setDown(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			p2.setRight(false);
		}
		
		//global controls
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	
	}
	
}
