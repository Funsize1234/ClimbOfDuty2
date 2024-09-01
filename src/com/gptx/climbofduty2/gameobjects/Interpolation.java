package com.gptx.climbofduty2.gameobjects;

public class Interpolation {

	/**
	 * Linear Interpolation Function
	 * 
	 * @param start starting position
	 * @param end ending position
	 * @param time  must be 0 to 1
	 * @return position in between start and end depending on time
	 */
	public static float lerp(float start, float end, float time)
	{
		return start + (end - start) * (float)time;
	}
	
	
	/**
	 * 
	 * COPY OF UNITY SMOOTH DAMP PULLED FROM UNITY C# REFERENCE
	 * 
	 * @param currentPos
	 * @param targetPos
	 * @param v Velocity
	 * @param smoothTime
	 * @return
	 */
	public static float smoothDamp(float currentPos, float targetPos, Velocity v, float smoothTime)
	{
		float deltaTime = 1f/60f;
		float omega = 2f/smoothTime;
		
		float x = omega * deltaTime;
		float exp = 1f/(1f + x + 0.48f * x * x + 0.235f * x * x * x);
		float delta = currentPos - targetPos;
		
		
		float temp = (v.getVelocity() + omega * delta) * deltaTime;
		v.setVelocity((v.getVelocity() - omega * temp) * exp);
		
		float result = targetPos + (delta+temp)*exp;
		
		if(targetPos - currentPos > 0 == result > targetPos)
		{
			result = targetPos;
			v.setVelocity((result - targetPos)/deltaTime);
		}
		
		return result;
	}
	
	
	
	public static float goTo(float current, float target, float increment)
	{
		if(current < target)
		{
			if(current + increment < target)
				return current + increment;
			else if(current + increment >= target)
				return target;
		}
		
		else if (target < current)
		{
			if(current - increment > target)
				return current - increment;
			else if (current - increment <= target)
				return target;
		}
		else if (target == current)
		{
			return target;
		}
		return target;
	}
	
}
