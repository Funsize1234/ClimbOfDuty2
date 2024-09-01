package com.gptx.climbofduty2.panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;



/**
 * Represents a button
 * 
 * @author Liam Berman
 *
 */
public class Button extends JButton {

    private Image image;
    private Image hoverImage;
    private Image currentImage;
    private boolean hover;
    private int x, y, width, height;

    public Button(String image, String hoverImage, int x, int y, int width, int height) {
        this.image = new ImageIcon(image).getImage();
        this.hoverImage = new ImageIcon(hoverImage).getImage();
        this.x = x;
        this.y = y;
        this.width = width;	
        this.height = height;
        currentImage = this.image;
   
    }

    //check if paramters x and y is inside the button by checking x,y,width,height and set the currentImage the hovering image
   public boolean isHovering(int x, int y)
   {
	   boolean hovering = new Rectangle(this.x, this.y, this.width, this.height).contains(x, y);
	   		if (hovering) {
	               currentImage = hoverImage;
	           } else {
	               currentImage = image;
	           }
	           return hovering;
	       }
   
   
   
   public void setImage(Image img){
	   image = img;
   }
   
   public void setHoverImage(Image img){
	   hoverImage = img;
   }
   
   public void moveTo(int newX, int newY) {
	   super.move(newX, newY);
	   x = newX;
	   y = newY;
   }
 
    
    //draw the button 
    public void draw(Graphics g)
    {
    	 g.drawImage(currentImage, x, y, null);
    }
}
