package com.gptx.climbofduty2.gameobjects;

public class Velocity {

	private float vel;
	
	public Velocity(float v)
	{
		vel = v;
	}
	
	public void setVelocity(float v)
	{
		vel = v;
	}
	
	public float getVelocity()
	{
		return vel;
	}
	
	public void addVelocity(float v)
	{
		vel += v;
	}
	
}
