package com.dasugames.soulbeatbombscramble.objects;

import com.badlogic.gdx.math.Vector2;

/**
 * A very simple representation of an image.
 * Consider getting rid of this as I think that ImageObjectSimple
 * is preferable moving forwards.
 * @author darren.sue
 *
 */
public class  ImageObject {
	protected Vector2 positionVec = new Vector2(0,0);
	protected Vector2 resizeVec = new Vector2(1,1);
	protected float rotationScale = 0; // in velocity
	protected float parallaxScale = 1;
	
	public Vector2 getPositionVec() {
		return positionVec;
	}
	public void setPositionVec(Vector2 positionVec) {
		this.positionVec = positionVec;
	}

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

	public float getParallaxScale() {
		return parallaxScale;
	}
	public void setParallaxScale(float parallaxScale) {
		this.parallaxScale = parallaxScale;
	}
	
	

}
