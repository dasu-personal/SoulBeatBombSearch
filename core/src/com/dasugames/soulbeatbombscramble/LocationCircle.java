package com.dasugames.soulbeatbombscramble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class LocationCircle {
	protected Vector2 positionVec;
	protected float parallaxScale;
	
	protected float resizeScale;
	protected float targetResizeScale;
	protected float remainingTime = -1;

	// These are used for the rendering of the aformentioned resource
	private ScreenWorld screenWorld;
	private WorldCamera worldCamera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	ShapeType shapeType;
	public LocationCircle(ScreenWorld screenWorld, ShapeType shapeType, Vector2 nPositionVec, float nParallaxScale, float nRotationScale, float nResizeScale){
		this.screenWorld = screenWorld;
		worldCamera = screenWorld.getWorldCamera();

		shapeRenderer = screenWorld.getShapeRenderer();
		batcher = screenWorld.getBatcher();
		positionVec = nPositionVec;
		parallaxScale = nParallaxScale;
		this.resizeScale = nResizeScale;
		
	}
	

	
	public void render(){


		
		//Gdx.app.log("test", "render");
		// Remember everything in my engine is relation to a center zero
		float originX = positionVec.x;
		float originY = positionVec.y;
		
		// relative coordinates with respect to camera center
		Vector2 cameraPosition = worldCamera.getPositionVec();
		originX -= cameraPosition.x;
		originY -= cameraPosition.y;
		
		// handles parallax scrolling
		originX *= parallaxScale;
		originY *= parallaxScale;

		
		// TODO fix linewidth
		Color screenColor = screenWorld.getCurrentColor();
		
		shapeRenderer.setColor(screenColor.mul( 0.8f));
		Gdx.gl20.glLineWidth( 200f);
		
		shapeRenderer.circle(originX, originY, resizeScale,25);
		/*
		sprite.setCenter(originX, originY);
		//sprite.setOrigin(0, 0);
		sprite.draw(batcher);
		*/
		
		
	}

	
	public Vector2 getPositionVec() {
		return positionVec;
	}
	public void setPositionVec(Vector2 positionVec) {
		this.positionVec = positionVec;
	}

	public float getParallaxScale() {
		return parallaxScale;
	}
	public void setParallaxScale(float parallaxScale) {
		this.parallaxScale = parallaxScale;
	}
	
	
	public void SetTargetRadius(float radius, float timeLength){

		this.remainingTime = timeLength;
		this.targetResizeScale = radius;
	}
	
	public void SetConstantRadius(float radius){
		this.resizeScale = radius;
		remainingTime = -1;
	}
	
	public void update(float deltaTime){
		if (remainingTime > 0){
			float interpolationCoefficient = Math.max(Math.min(deltaTime / remainingTime,1),0);
		    resizeScale = resizeScale * (1- interpolationCoefficient) + targetResizeScale * interpolationCoefficient;
			remainingTime -= deltaTime;
		}
		
	}



	public boolean isDead() {
		// TODO Auto-generated method stub
		return remainingTime <0;
	}

}
