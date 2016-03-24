package com.dasugames.soulbeatbombscramble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

// Just the position of the imageObject, and a render convenience method that can be overwtitten
public class ImageObjectSimple  {
	
	// These help determine where the image should be placed on the screen
	protected Vector2 positionVec;
	//protected float rotationScale = 0; // This will be in degrees
	protected float parallaxScale;;
	
	// These can help maintain a sense of continuity of motion
	private float rotVelScale = 0;
	private Vector2 velocityVec = new Vector2(0,0);
	private Vector2 resizeVelVec = new Vector2(0,0);
	
	// This is the resource that will be displayed
	private TextureRegion simpleImageResource;

	// These are used for the rendering of the aformentioned resource
	private DasuScreen screenWorld;
	private WorldCamera worldCamera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	Sprite sprite;
	
	public ImageObjectSimple(DasuScreen screenWorld, String asset, Vector2 nPositionVec, float nParallaxScale, float nRotationScale, Vector2 nResizeVec){
		this.screenWorld = screenWorld;
		worldCamera = screenWorld.getWorldCamera();
		simpleImageResource = Assets.loadTextureRegion(asset);

		//shapeRenderer = screenWorld.getShapeRenderer();
		batcher = screenWorld.getBatcher();

		
		positionVec = nPositionVec;
		parallaxScale = nParallaxScale;
		
		sprite = new Sprite(simpleImageResource);
		sprite.setRotation(nRotationScale);
		sprite.setScale(nResizeVec.x, nResizeVec.y);
		
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


		
		sprite.setCenter(originX, originY);
		//sprite.setOrigin(0, 0);
		sprite.draw(batcher);
		
		
		
	}

	public float getRotVelScale() {
		return sprite.getRotation();
		//return rotVelScale;
	}

	public void setRotVelScale(float rotVelScale) {
		//this.rotVelScale = rotVelScale;  // remove this
		sprite.setRotation(rotVelScale);
		//sprite.getRotat
		
	}
	
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
		return new Vector2(sprite.getScaleX(),sprite.getScaleY());
	}

	// resize vectors can be negative
	public void setResizeVec(Vector2 resizeVec) {
		//this.resizeVec = resizeVec;
		sprite.setScale(resizeVec.x, resizeVec.y);
	}
	public void setResizeScale(float resizeScale) {
		sprite.setScale(resizeScale);
		//this.resizeVec.x = resizeScale;
		//this.resizeVec.y = resizeScale;
	}
	public float getRotationScale() {
		return sprite.getRotation();
	}
	public void setRotationScale(float rotationScale) {
		sprite.setRotation(rotationScale);
	}

	public float getParallaxScale() {
		return parallaxScale;
	}
	public void setParallaxScale(float parallaxScale) {
		this.parallaxScale = parallaxScale;
	}
	public Vector2 getCurrentSize(){
		return new Vector2(sprite.getScaleX()*sprite.getWidth(), sprite.getScaleX()*sprite.getHeight());
	}
	
}
