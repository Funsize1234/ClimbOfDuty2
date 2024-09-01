package com.gptx.climbofduty2.gameobjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class represents a platform.
 * @author Sanatan Mishra
 */
public class Platform extends GameObject {
	
	private int level;
	
	/**
	 * Constructor to make a platform.
	 * @param x x-coordinate of the platform
	 * @param y y-coordinate of the platform
	 * @param width width of the platform
	 * @param height height of the platform
	 * @param lvl the level the player will have attained upon reaching this platform
	 */
	public Platform(int x, int y, int width, int height, int lvl) {
		super(x, y, width, height, 0, 0);
		super.setG(0);
		level = lvl;
	}
	
	/**
	 * Returns the level at which the player will be upon reaching this platform.
	 * @return The level at which the player will be upon reaching this platform
	 */
	public int getLevel() {
		return level;
	}
	
	@Override
	public void draw(Graphics g, ImageObserver io) {
		super.draw(g, io);
		g.setColor(Color.GRAY);
		g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());

	}

}
