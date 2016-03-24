package com.dasugames.soulbeatbombscramble;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class PlayerCharacter {
	private Vector2 position;
	//private Vector2 position2;
	//private ImageObjectSimple debugImage;
	private List<LocationCircle> locationCircles;
	//private LocationCircle debugCircle;
	private ScreenWorld screenWorld;
	
	public PlayerCharacter(Vector2 position, ScreenWorld screenWorld){
		this.position = position;
		this.screenWorld = screenWorld;
		//debugImage = new ImageObjectSimple(screenWorld, "data/Siegfried-ffvi-ios-battle.png",new Vector2(0,0),1f,0,new Vector2(.007f,.007f));
		locationCircles = new ArrayList<LocationCircle>();
	}
	
	
	public void addLocationPulse(){
		if (position!=null){
			addLocationPulse(position);
		}

	}

	public void addLocationPulse(Vector2 center){
		LocationCircle locationCircleToAdd = new LocationCircle(screenWorld, ShapeType.Line, center, 1, 0, .01f);
		locationCircleToAdd.SetTargetRadius(1, 0.2f);
		locationCircles.add(locationCircleToAdd);
	}

	public void render() {
		//Gdx.app.log("player", "debugrender");
		/*
		if (position != null) {
			debugImage.setPositionVec(position);
			debugImage.render();
		}
		*/
		/*
		if (position2 != null) {
			debugImage.setPositionVec(position2);
			debugImage.render();
		}
		*/
		Iterator<LocationCircle> locationCircleIter = locationCircles
				.iterator();
		while (locationCircleIter.hasNext()) {
			locationCircleIter.next().render();
		}

	}
	
	public void update(float deltaTime){
		// update all circles and potentially remove them
		Iterator<LocationCircle> locationCircleIter = locationCircles.iterator();
		while (locationCircleIter.hasNext()){
			LocationCircle currentLocationCircle = locationCircleIter.next();
			currentLocationCircle.update(deltaTime);
			if (currentLocationCircle.isDead()){
				locationCircleIter.remove();
			}
			
		}
	}
	
	

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	/*
	public Vector2 getPosition2() {
		return position2;
	}

	public void setPosition2(Vector2 position) {
		this.position2 = position;
	}
	*/
}
