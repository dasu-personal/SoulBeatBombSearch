package com.dasugames.soulbeatbombscramble;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	private Vector2 position;
	//private ImageObjectSimple debugImage;
	
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
		//debugImage = new ImageObjectSimple(screenWorld, "data/Siegfried-ffvi-ios-battle.png",new Vector2(0,0),1f,0,new Vector2(.007f,.007f));
	}
	
	public void render() {
		//Gdx.app.log("enemy","debugrender");
		//debugImage.setPositionVec(position);
		//debugImage.render();
	}
	
	

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
