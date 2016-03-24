package com.dasugames.soulbeatbombscramble.objects;


import com.badlogic.gdx.math.Vector2;
import com.dasugames.soulbeatbombscramble.ScreenWorld;

/**
 * This class represent an invisible enemy bomb.
 * @author darren.sue
 *
 */
public class Enemy {

	private Vector2 position;
	
	private final float maxHealth = 0.5f;
	private float currentHealth = maxHealth;
	
	public boolean isDead(){
		return currentHealth < 0;
	}
	
	public void applyContactDamage(float runTime){
		currentHealth -= runTime;
	}
	
	public void applyContactMiss(float runTime){
		currentHealth = Math.min(maxHealth, currentHealth + runTime * 2);
	}
	
	public Enemy(Vector2 position, ScreenWorld screenWorld){
		this.position = position;
	}
	
	public void render() {
		// Invisible enemies due to the nature of this game.
	}
	
	

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
