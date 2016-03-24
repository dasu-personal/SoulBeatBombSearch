package com.dasugames.soulbeatbombscramble;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// Just the position of the camera and nothing more.
public class WorldCamera {
	private Vector2 positionVec = new Vector2(0,0); // sensible default
	
	// gets applied after determining the width and height based on minDimensionVec and aspectRatio
	private Vector2 resizeVec = new Vector2(1,1); // sensible default
	
	private Vector2 minDimensionVec = new Vector2(500,500); // really dumb default
	
	private float rotationScale = 0; // sensible default

	private float aspectRatio = 0.7f; // really dumb default. This should be the default of the  
	// add more variables here as necessary

	private OrthographicCamera cam = new OrthographicCamera(500,500); // really dumb default
	
	public WorldCamera(){	
		//cam.setToOrtho(false);
	}
	
	public float getRotationScale() {
		return rotationScale;
	}

	public void setRotationScale(float rotationScale) {
		this.rotationScale = rotationScale;
	}

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

	// aspect ratio can never be less than 0
	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public Vector2 getMinDimensionVec() {
		return minDimensionVec;
	}

	public void setMinDimensionVec(Vector2 minDimensionVec) {
		this.minDimensionVec = minDimensionVec;
	}
	
	public Matrix4 generateCameraMatrix(){
		// set camera dimensions such that the minimum dimensions are obeyed and the game mantains the given aspect ratio.
		//cam.setToOrtho(false);
		float nWidth;
		float nHeight;
		if (minDimensionVec.y <= 0 || minDimensionVec.x/minDimensionVec.y > aspectRatio){
			nWidth = minDimensionVec.x;
			nHeight = nWidth / aspectRatio;
		} else {
			nHeight = minDimensionVec.y;
			nWidth = nHeight * aspectRatio;
		}
		cam.viewportWidth = resizeVec.x * nWidth;
		cam.viewportHeight = resizeVec.y * nHeight;
		

		// cant translate as that would be applying the translation twice. Once manually by the image objects and here as well
		cam.rotate(rotationScale);
		cam.update();
		Matrix4 cameraMatrix = cam.combined.cpy();
		cam.rotate(-rotationScale);
		//cam.transform(cameraOld);
		//cam.translate(positionVec.scl(-1));
		//cam.rotate(-rotationScale);
		// un apply transformations
		//positionVec.scl(-1);
		
		return cameraMatrix;
	}
	
	public Vector3 worldToScreen(Vector3 worldCoordinates){
		return cam.project(worldCoordinates);
	}
	
	public Vector3 screenToWorld(Vector3 screenCoordinates){
		return cam.unproject(screenCoordinates);
	}
	
}
