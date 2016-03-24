package com.dasugames.soulbeatbombscramble.objects;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dasugames.soulbeatbombscramble.Assets;
import com.dasugames.soulbeatbombscramble.DasuScreen;
import com.dasugames.soulbeatbombscramble.WorldCamera;

/**
 * This is a slightly more complex image object with rendering functionality 
 * @author darren.sue
 *
 */
public class ImageObjectSimple  {
	
	// These help determine where the image should be placed on the screen
	protected Vector2 positionVec;
	protected float parallaxScale;;
	
	// These can help maintain a sense of continuity of motion
	// For the purposes of this project, these won't be used
	protected float rotVelScale = 0;
	protected Vector2 velocityVec = new Vector2(0,0);
	protected Vector2 resizeVelVec = new Vector2(0,0);
	
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
		sprite.draw(batcher);
		
		
		
	}

	public float getRotVelScale() {
		return sprite.getRotation();
	}

	public void setRotVelScale(float rotVelScale) {
		this.rotVelScale = rotVelScale; 
		sprite.setRotation(rotVelScale);
		
	}
	
	public Vector2 getPositionVec() {
		return positionVec;
	}
	public void setPositionVec(Vector2 positionVec) {
		this.positionVec = positionVec;
	}

	public Vector2 getResizeVec() {
		return new Vector2(sprite.getScaleX(),sprite.getScaleY());
	}

	public void setResizeVec(Vector2 resizeVec) {
		// note that resize vectors can be negative to flip the image
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



	public Vector2 getVelocityVec() {
		return velocityVec;
	}



	public void setVelocityVec(Vector2 velocityVec) {
		this.velocityVec = velocityVec;
	}



	public Vector2 getResizeVelVec() {
		return resizeVelVec;
	}



	public void setResizeVelVec(Vector2 resizeVelVec) {
		this.resizeVelVec = resizeVelVec;
	}
	
}
