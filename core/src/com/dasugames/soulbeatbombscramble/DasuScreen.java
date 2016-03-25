package com.dasugames.soulbeatbombscramble;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * This abstract class is mostly to implement screen functionally with stubs as I am unlikely
 * to need anything custom there.
 * @author darren.sue
 *
 */
public abstract class DasuScreen implements Screen {
	protected SpriteBatch batcher;
	protected WorldCamera worldCamera;
	protected ShapeRenderer shapeRenderer;
	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
	
	public SpriteBatch getBatcher() {
		return batcher;
	}
	
	public WorldCamera getWorldCamera() {
		return worldCamera;
	}

	public void setWorldCamera(WorldCamera worldCamera) {
		this.worldCamera = worldCamera;
	}
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
