package com.dasugames.soulbeatbombscramble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dasugames.soulbeatbombscramble.ScreenWorld.GameState;

public class InputHandler implements InputProcessor {
	private ScreenWorld screenWorld;
	public InputHandler(ScreenWorld screenWorld){
		this.screenWorld = screenWorld;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointer == 0){
			Vector3 worldVec = screenWorld.getWorldCamera().screenToWorld(new Vector3(screenX,screenY,0));
			//Gdx.app.log("gameStart: test", "x: "+worldVec.x+ ", y: " + worldVec.y + ", pointer: " + pointer);
			screenWorld.moveCharacter(worldVec.x, worldVec.y);
			return true;
		} else {
			return false;
		}
	}


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			if (pointer == 0){
				screenWorld.upCharacter();
			}
	
		return true;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		if (pointer == 0){
			// move the player position
			Vector3 worldVec = screenWorld.getWorldCamera().screenToWorld(new Vector3(screenX,screenY,0));
			//Gdx.app.log("touchDrag1: test", "x: "+worldVec.x+ ", y: " + worldVec.y + ", pointer: " + pointer);
			screenWorld.moveCharacter(worldVec.x, worldVec.y);
		}
		return true;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
