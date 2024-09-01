package com.gptx.climbofduty2.panel;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * 
 * Represents a cycle selector
 * 
 * @author Liam Berman
 *
 */
public class CycleButton extends Button{

	private Image [] images = new Image[6];
	private int iterations;
	
	
	public CycleButton(String image, String hoverImage, String image2, String hoverImage2,String image3, String hoverImage3, 
			int x, int y, int width, int height) {
		
		super(image, hoverImage, x, y, width, height);
		images[0] = new ImageIcon(image).getImage();
		images[1] = new ImageIcon(image2).getImage();
		images[2] = new ImageIcon(image3).getImage();
		images[3] = new ImageIcon(hoverImage).getImage();
		images[4] = new ImageIcon(hoverImage2).getImage();
		images[5] = new ImageIcon(hoverImage3).getImage();
		
	}

	
	public void alternate() {
		
		iterations++;
		
		if (iterations + 3 > 5) 
			iterations = 0;
			
		setHoverImage(images[iterations + 3]);
		setImage(images[iterations]);
		
		
	}
	
	public int getIterations() {
		//System.out.print(iterations);
		return iterations;
		
	}
	

}


