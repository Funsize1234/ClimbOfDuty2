package com.gptx.climbofduty2.panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Represents the menu screen
 * @author Liam Berman
 */
public class MenuPanel extends JPanel implements MouseMotionListener, MouseListener {
	
	private PanelHandler p;
	private Button play;
	private CycleButton mode;
	private CycleButton difficulty;
	
	public MenuPanel(PanelHandler p) {	
	    super();
	    this.p = p;
	}
	
	public void initialize() {
	    this.play = new Button("sprites/playbutton.png", "sprites/playbuttonhover.png", 710,400, 500, 125);

	    this.mode = new CycleButton("sprites/solobutton.png", "sprites/solobuttonhover.png","sprites/localbutton.png","sprites/localbuttonhover.png","sprites/onlinebutton.png", "sprites/onlinebuttonhover.png", 710, 550, 500, 125);

	    this.difficulty = new CycleButton("sprites/easybutton.png", "sprites/easybuttonhover.png","sprites/normalbutton.png", 
	    		"sprites/normalbuttonhover.png","sprites/hardbutton.png", "sprites/hardbuttonhover.png",710,700, 500, 125);
	}

	
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    Image image = new ImageIcon("sprites/menu.png").getImage();
	    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	    play.moveTo((int) (710.0 * p.getWidth() / 1920), (int) (400 * p.getHeight() / 1080));
	    mode.moveTo((int) (710.0 * p.getWidth() / 1920), (int) (550 * p.getHeight() / 1080));
	    difficulty.moveTo((int) (710.0 * p.getWidth() / 1920), (int) (700 * p.getHeight() / 1080));
	    play.draw(g);
	    mode.draw(g);
	    difficulty.draw(g);
	    
	    if(mode.getIterations() == 2)
	    {
	    	g.setColor(Color.white);
	    	g.fillRect((int) (705.0 * p.getWidth() / 1920), (int) (315.0 * p.getHeight() / 1080), 527, 51);
	    	g.drawImage(new ImageIcon("sprites/comingsoon.png").getImage(), (int) (710.0 * p.getWidth() / 1920), (int) (320.0 * p.getHeight() / 1080), null);
	    }
	    
	    repaint();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//check if mouse position using isHovering method for button
	@Override
	public void mouseMoved(MouseEvent e) {
		if(mode.getIterations() != 2)
			play.isHovering(e.getX(), e.getY());
		mode.isHovering(e.getX(), e.getY());
		difficulty.isHovering(e.getX(), e.getY());
	}

	//check if mouse position using isHovering method then if true then call panel handler p change screen method
	@Override
	public void mouseClicked(MouseEvent e) {
			repaint();
		   int x = e.getX();
		    int y = e.getY();
		    if (play.isHovering(x, y) && mode.getIterations() != 2) {
		        p.changePanel("2");
		    } 
		    if(mode.isHovering(x, y)){
		    	mode.alternate();
		    	
		    } 
		    if (difficulty.isHovering(x, y)) {
		    	difficulty.alternate();
		    }
		    
		}
	
	public int getDifficulty()
	{
		return difficulty.getIterations();
	}

	public int getMode()
	{
		return mode.getIterations();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

	}


	@Override
	public void mouseReleased(MouseEvent e) {

	}


	@Override
	public void mouseEntered(MouseEvent e) {
	
		
	}


	@Override
	public void mouseExited(MouseEvent e) {

		
	}

    
	
}
