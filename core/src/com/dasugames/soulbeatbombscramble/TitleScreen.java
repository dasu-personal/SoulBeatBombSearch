package com.dasugames.soulbeatbombscramble;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.dasugames.soulbeatbombscramble.objects.ImageObjectDecolor;



public class TitleScreen extends DasuScreen implements InputProcessor  {
	private BitmapFont smallText;
	private BitmapFont largeText;
	private I18NBundle textResourceBundle;
	private SoulBeatBombScramble game;
	private List<ImageObjectDecolor> backgroundElements;
	
	
	
	
	public TitleScreen(SoulBeatBombScramble soulBeatBombScramble){
		game = soulBeatBombScramble;
		
		batcher = new SpriteBatch();
		batcher.enableBlending();
		
		worldCamera = new WorldCamera();

		largeText = new BitmapFont();
		largeText.setScale(.020f);
		largeText.setUseIntegerPositions(false);
		largeText.setColor(1,1,1,1);
		
		smallText = new BitmapFont();
		smallText.setScale(.015f);
		smallText.setUseIntegerPositions(false);
		smallText.setColor(1,1,1,1);
		
		FileHandle baseFileHandle = Gdx.files.internal("i18n/GameBundle");
		textResourceBundle = I18NBundle.createBundle(baseFileHandle,Locale.getDefault());
		Gdx.input.setInputProcessor(this);
		
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
				game.startTitleMusic();
		    }
		}, 1);

	}
	
	private void loadAssetsObjects(){
		backgroundElements = new ArrayList<ImageObjectDecolor>();
		String[] backgroundImageResources =  textResourceBundle.format("titleImages").split(",");
		for (String backgroundImageResourceCurr : backgroundImageResources){
			//Gdx.app.log("load title iage:", backgroundImageResourceCurr);
			ImageObjectDecolor imageObjectDecolor = new ImageObjectDecolor(this, backgroundImageResourceCurr,new Vector2(0,0),1f,0, new Vector2(0.1f,0.1f));
			imageObjectDecolor.setConstantColor(new Color(0.7f,0.7f,0.7f,1f));
			//imageObjectDecolor.SetConstantColor(Color.WHITE);
			backgroundElements.add(imageObjectDecolor);
			//currentBackgroundElement.SetConstantColor(Color.WHITE);

		}
	}
	
	@Override
	public void show() {
		// this is here rather in the constructor due to the fact that some assets depend on screen dimensions
		loadAssetsObjects();
		
	}

	private int fading_index = 0;
	private int appear_index = 0;
	private float elapsed_time = 10.1f;
	private void update(float delta){
		elapsed_time = elapsed_time +  delta;
		//Gdx.app.log("render:", ""+elapsed_time);
		if (elapsed_time >= 10){
			elapsed_time = 0;
			fading_index = appear_index;
			appear_index ++;
			if (appear_index >= backgroundElements.size()){
				appear_index = 0;
			}
			float widthMargin = visibleWidth - backgroundElements.get(appear_index).getCurrentSize().x;
			float heightMargin = visibleHeight - backgroundElements.get(appear_index).getCurrentSize().y;
			float startX = ((float) Math.random() - 0.5f) * widthMargin;
			float startY = ((float) Math.random() - 0.5f) * heightMargin;
			float endX = ((float) Math.random() - 0.5f) * widthMargin;
			float endY = ((float) Math.random() - 0.5f) * heightMargin;
			backgroundElements.get(appear_index).setConstantColor(new Color(0.7f,0.7f,0.7f,0));
			backgroundElements.get(appear_index).setColorTarget(new Color(0.7f,0.7f,0.7f,1),3);
			backgroundElements.get(appear_index).setPositionVec(new Vector2(startX,startY));
			backgroundElements.get(appear_index).setTargetPosition(new Vector2(endX,endY), 13);
			backgroundElements.get(fading_index).setConstantColor(new Color(0.7f,0.7f,0.7f,1));
			backgroundElements.get(fading_index).setColorTarget(new Color(0.7f,0.7f,0.7f,0),3);
			
			
		}
		backgroundElements.get(appear_index).update(delta);
		backgroundElements.get(fading_index).update(delta);
	}
	
	@Override
	public void render(float delta) {
		
		update(delta);
		// render
		// Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// set camera matric and stuff
		Matrix4 cameraTransformMatrix = worldCamera.generateCameraMatrix();
		batcher.setProjectionMatrix(cameraTransformMatrix);
		batcher.begin();
		
		
		
		// Background
		backgroundElements.get(appear_index).render();
		backgroundElements.get(fading_index).render();
		
		
		// Title
		largeText.drawMultiLine(batcher, textResourceBundle.format("title"), 0, 2, 0, BitmapFont.HAlignment.CENTER);
		
		// Instructions
		smallText.drawMultiLine(batcher, textResourceBundle.format("instructions"), 0, 0, 0,BitmapFont.HAlignment.CENTER);
		
		// HighScore
		smallText.drawMultiLine(batcher, textResourceBundle.format("highscore", game.getScore()), 0, -2, 0,BitmapFont.HAlignment.CENTER);

		batcher.end();
		
	}

	float visibleWidth;
	float visibleHeight;
	
	@Override
	public void resize(int width, int height) {
		
		float aspect = Math.abs((float) Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight());
		Gdx.app.log("resize", "width = " + width+"; height = " + height + "; gdxwidth = " + Gdx.graphics.getWidth() + "; gdxheight = " + Gdx.graphics.getHeight());


		
		visibleWidth = 5;
		visibleHeight = 5f/aspect;
		//worldCamera = new WorldCamera();
		
		// set worldCamera stuff
		worldCamera.setMinDimensionVec(new Vector2(5f,5f/aspect));
		worldCamera.setAspectRatio(aspect);
		worldCamera.generateCameraMatrix();
		
		// resize the background image to cover the entire area
		Iterator<ImageObjectDecolor>backgroundElementsIter=backgroundElements.iterator();
		while (backgroundElementsIter.hasNext()) {
			ImageObjectDecolor imageObjectDecolor = backgroundElementsIter
					.next();
			Vector2 currentSize = imageObjectDecolor.getCurrentSize();
			Vector2 resize = imageObjectDecolor.getResizeVec();
			float resizeWidth = visibleWidth / currentSize.x * resize.x;
			float resizeHeight = visibleHeight / currentSize.y * resize.y;
			imageObjectDecolor.setResizeScale(Math.max(resizeWidth,
					resizeHeight));
		}
		//game.startTitleMusic();

	}
	
	private void unloadAssetsAndObjects() {
		Gdx.app.log("unload", "blah blah");
		// enemies = null;
		backgroundElements = null;
		// TODO is this even neccessary?
		//Assets.clearAssets();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		unloadAssetsAndObjects();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("touchDown", "Creating a new game");
        dispose();
        game.endMusic();
        game.startLevelMusic();
        ScreenWorld screenWorld = new ScreenWorld(game);
		
        game.setScreen(screenWorld);
        screenWorld.afterResizeInitilize();
        screenWorld.afterLoadStart();
        screenWorld.setFingerDownCoordinates(screenX, screenY);
        
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	

}
