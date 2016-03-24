package com.dasugames.soulbeatbombscramble;

import com.badlogic.gdx.math.Vector2;

public class  ImageObject {
	protected Vector2 positionVec = new Vector2(0,0);
	//private Vector2 velocityVec = new Vector2(0,0);
	protected Vector2 resizeVec = new Vector2(1,1);
	protected float rotationScale = 0; // This will be in degrees
	//private float rotVelScale = 0;
	protected float parallaxScale = 1;
	
	public Vector2 getPositionVec() {
		return positionVec;
	}
	public void setPositionVec(Vector2 positionVec) {
		this.positionVec = positionVec;
	}
	/* decided about making a simpler image object as the actual objects are going to be very 
	public Vector2 getVelocityVec() {
		return velocityVec;
	}
	public void setVelocityVec(Vector2 velocityVec) {
		this.velocityVec = velocityVec;
	}
	*/
	public Vector2 getResizeVec() {
		return resizeVec;
	}

	public void setResizeVec(Vector2 resizeVec) {
		this.resizeVec = resizeVec;
	}
	public void setResizeScale(float resizeScale) {
		this.resizeVec.x = resizeScale;
		this.resizeVec.y = resizeScale;
	}
	public float getRotationScale() {
		return rotationScale;
	}
	public void setRotationScale(float rotationScale) {
		this.rotationScale = rotationScale;
	}
	/*
	public float getRotVelScale() {
		return rotVelScale;
	}
	public void setRotVelScale(float rotVelScale) {
		this.rotVelScale = rotVelScale;
	}
	*/
	public float getParallaxScale() {
		return parallaxScale;
	}
	public void setParallaxScale(float parallaxScale) {
		this.parallaxScale = parallaxScale;
	}
	
	

}
