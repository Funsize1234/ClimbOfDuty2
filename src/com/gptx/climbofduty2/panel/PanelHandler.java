package com.gptx.climbofduty2.panel;
import java.awt.CardLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Handles both screens
 * 
 * @author Colin Lou
 * 
 */
public class PanelHandler extends JFrame {

	private JPanel cardPanel;
	private GamePanel game;
	private MenuPanel menu;
	
	public PanelHandler(String title) {
		super(title);
	
		ImageIcon ico = new ImageIcon("sprites/cod2icon.png");
		this.setIconImage(ico.getImage());
		
		char os = System.getProperty("os.name").charAt(0);
	
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		
		this.setBounds((width-1920)/2, (height-1080)/2, 1920, 1080);
		
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    setResizable(true);
	    setUndecorated(false);
	 
	    
		setExtendedState(JFrame.MAXIMIZED_VERT);
	    this.setUndecorated(true);
	    
	    cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
	    
		menu = new MenuPanel(this);    
		game = new GamePanel(this);
		
		//this.setBorder(BorderFactory.createLineBorder(Color.green, 12));
	   
		
	    cardPanel.add(menu,"1"); // Card is named "1"
	    cardPanel.add(game,"2"); // Card is named "2"
	    
	    add(cardPanel);
	    
	    this.getContentPane().addMouseMotionListener(menu);
	    this.getContentPane().addMouseListener(menu);
	    
	    menu.initialize();
	    setVisible(true);
	    this.addKeyListener(game);
	    game.run();
	  
	}
	
	public void changePanel(String name) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel,name);
		requestFocus();
		
		if(name.equals("2"))
			game.initialize(menu.getMode(), menu.getDifficulty());
			game.setRunning(true);
			game.startGame();
		
	}
	
}
