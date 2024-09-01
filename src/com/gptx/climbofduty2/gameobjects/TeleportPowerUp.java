package com.gptx.climbofduty2.gameobjects;

import javax.swing.ImageIcon;

/**
 * Represents a power-up that includes teleporting
 * 
 * @author Colin
 *
 */
public class TeleportPowerUp extends Consumable {
	
	public TeleportPowerUp(int x, int y) {
		super(x, y, 30, 22, "sprites/tp", 6);
	}
	
	public void boost()
	{
		
	}
}
