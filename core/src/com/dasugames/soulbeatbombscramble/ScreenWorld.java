package com.dasugames.soulbeatbombscramble;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.dasugames.soulbeatbombscramble.objects.Enemy;
import com.dasugames.soulbeatbombscramble.objects.ImageObjectDecolor;
import com.dasugames.soulbeatbombscramble.objects.PlayerCharacter;


/**
 * This contains the general play screen.
 * @author darren.sue
 *
 */
public class ScreenWorld extends DasuScreen {

	private int levelScore = 0;
	private float visibleWidth;
	private float visibleHeight;
	private Sound clickSound; 
	private Sound bombSound;
	private PlayerCharacter playerCharacter;
	private List<Enemy> enemies;
	private ImageObjectDecolor currentBackgroundElement;
	private I18NBundle textResourceBundle;	
	
	// Used to coordinate the lose animation
	private float loseTimer = 0;
	private boolean isRumble = false;
	
	public enum GameState{
		PLAY, LOSE, WIN
	}
	
	private GameState gameState = GameState.PLAY;
	private float timeUntilGameEnd = 8.0f;
	
	public void setCurrentLevel(int nLevelScore){
		this.levelScore = nLevelScore;
	}
	




	SoulBeatBombScramble game;
	public ScreenWorld(SoulBeatBombScramble soulBeatBombScramble){
		game = soulBeatBombScramble;

		
		worldCamera = new WorldCamera();
		
		shapeRenderer = new ShapeRenderer();
		batcher = new SpriteBatch();
		batcher.enableBlending();

		clickSound = Gdx.audio.newSound(Gdx.files.internal("data/testBeat.wav"));
		bombSound = Gdx.audio.newSound(Gdx.files.internal("data/bomb_explode.wav"));
		
		FileHandle baseFileHandle = Gdx.files.internal("i18n/GameBundle");
		textResourceBundle = I18NBundle.createBundle(baseFileHandle,Locale.getDefault());
		
		Gdx.input.setInputProcessor(new InputHandler(this));

	}
	
	public void afterResizeInitilize(){
		loadAssetsObjects();
		obeyDimensions();
		setBombTimer();
        setupEnemyLocations();	
        playerCharacter = new PlayerCharacter(null, this);
	}
	
	private void setBombTimer(){
		timeUntilGameEnd = 30;
		if (levelScore == 0){
			timeUntilGameEnd = 120;
		} else if (levelScore <= 5){
			timeUntilGameEnd = 30;
		} else if (levelScore <= 10){
			timeUntilGameEnd = 10;
		} else if (levelScore <= 20) {
			timeUntilGameEnd = 8;
		} else {
			timeUntilGameEnd = 7;
		}
	}
	
	private void loadAssetsObjects(){
		
		String[] backgroundImageResources =  textResourceBundle.format("backgroundImages").split(",");
		//currentBackgroundElement = backgroundElements.get(0); // dumb default
		Color startColor = new Color((float) (1 - Math.random()*0.5),(float) (1 - Math.random()*0.5),(float) (1 - Math.random()*0.5),1f);
		
		int randomBackgroundElementInt=(levelScore + 10)%backgroundImageResources.length;
		String currentBackgroundImageResource = backgroundImageResources[randomBackgroundElementInt];
		
		currentBackgroundElement = new ImageObjectDecolor(this, currentBackgroundImageResource,new Vector2(0,0),1f,0, new Vector2(1,1));

		currentBackgroundElement.setConstantColor(startColor.cpy());
	}
	
	public void setFingerDownCoordinates(float screenX, float screenY){
		Vector3 worldVec = getWorldCamera().screenToWorld(new Vector3(screenX,screenY,0));
		moveCharacter(worldVec.x, worldVec.y);		
	}
	

	
	private float enemyFoundTimer = 0;
	private final float enemyFoundDuration = 1.3f; // wow this is long
	private Vector2 enemyDeadLocation;
	public void updatePlay(float runTime){
		// see whether we can add more enemies
		timeUntilGameEnd -= runTime;
		
		playerCharacter.update(runTime);
		


			Iterator<Enemy> enemyIter = enemies.iterator();
		

			while (enemyIter.hasNext()) {
				Enemy enemy = enemyIter.next();
				if (enemy.isDead()) {
					enemyDeadLocation = enemy.getPosition().cpy();
					enemyIter.remove();
					enemyFoundTimer = enemyFoundDuration;
					game.setTargetMusicVolume(0.3f, 1f);
					currentBackgroundElement.setConstantColor(Color.WHITE);
				}
			
		}
		
		// check distance between player and all enemies
		// handle vibrations and image feedback
		if (enemyFoundTimer <= 0){
			handleDistance(runTime);
			
			// if we doing a win beat, there is no reason to check for losses
			if (timeUntilGameEnd < 0){
					gameState = GameState.LOSE;
					currentBackgroundElement.setConstantColor(Color.WHITE);
					game.setTargetMusicVolume(0.3f, 1f);
		
			}
		} else {
			float nextEnemyFoundTimer = enemyFoundTimer - runTime;
			if (enemyFoundDuration * 0.45 < enemyFoundTimer && enemyFoundDuration * 0.45 >= nextEnemyFoundTimer){
				playerCharacter.addLocationPulse(enemyDeadLocation);
				Gdx.input.vibrate(3);
				clickSound.play();
			} else if (enemyFoundDuration * 0.55 < enemyFoundTimer && enemyFoundDuration * 0.55 >= nextEnemyFoundTimer){
				//Gdx.app.log("pulsestart", "enemy location = " + enemyDeadLocation);
				playerCharacter.addLocationPulse(enemyDeadLocation);
				Gdx.input.vibrate(3);
				clickSound.play();
			} else if (0 >= nextEnemyFoundTimer){
				// just to clear the screen
				//currentBackgroundElement.render();
				game.setTargetMusicVolume(1f, 1.5f);
				gameState = GameState.WIN;
			}
			enemyFoundTimer = nextEnemyFoundTimer;
			
		}
	}
	
	

	float testVibrate = 0;
	boolean isVibrate = false;
	
	@Override
	public void render(float runTime) {
		if (!active) return;
		game.update(runTime);
		
		// Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (gameState == GameState.PLAY) {
			updatePlay(runTime);
		} else if (gameState == GameState.LOSE) {
			updateLose(runTime);
		} else {
			updateWin(runTime);
		}
		render();

	 }
	
	private void updateWin(float runTime){
		// All I have to do here is pause and then generate an entirely new screenworld
		//winTimer += runTime;
		playerCharacter.update(runTime);

		//Gdx.app.log("wintimer", " ");
		ScreenWorld nextLevel = new ScreenWorld(game);
		dispose();
		nextLevel.setCurrentLevel(levelScore + 1);
		game.setScreen(nextLevel);
		nextLevel.afterResizeInitilize();
		if (playerCharacter.getPosition() != null) {
			nextLevel.moveCharacter(playerCharacter.getPosition().x,
					playerCharacter.getPosition().y);
		}
		nextLevel.afterLoadStart();
	}


	private boolean active = false;
	public void afterLoadStart() {
		active = true;
	}

	private void updateLose(float runTime) {
		
		playerCharacter.update(runTime);
		
		loseTimer += runTime;
		if (loseTimer >= 1 && loseTimer < 4 && !isRumble){
			Gdx.input.vibrate(2000);
			game.endMusic();
			bombSound.play(0.7f);
			Color loseColor = new Color(0.4f,0f,0f, 1f);
			currentBackgroundElement.setConstantColor(loseColor);
			currentBackgroundElement.setResizeScale(currentBackgroundElement.getResizeVec().x*1.2f);
			isRumble = true;
		} else if (loseTimer >= 1 && loseTimer < 3 && isRumble){
			currentBackgroundElement.setRotationScale((float) ((Math.random()-0.5)*2*5));
			currentBackgroundElement.setPositionVec(new Vector2((float) ((Math.random()-0.5)*2*0.3),(float) ((Math.random()-0.5)*2*0.3)));
		} else if (loseTimer >= 3){
			bombSound.stop();
			loseTimer = 0;
			isRumble = false;
			game.offerScore(levelScore);
			
			dispose();
			//Gdx.app.log("lose timer", " ");
			game.setScreen(new TitleScreen(game));
	        
		}
		
		
	}

	private void render() {

		Matrix4 cameraTransformMatrix= worldCamera.generateCameraMatrix();
        shapeRenderer.setProjectionMatrix(cameraTransformMatrix);
        batcher.setProjectionMatrix(cameraTransformMatrix);

		batcher.begin();
		shapeRenderer.begin(ShapeType.Line);


		currentBackgroundElement.render();

		playerCharacter.render();
		
		 
		batcher.end();
		shapeRenderer.end();
	}



	
	private void obeyDimensions(){

		float aspect = Math.abs((float)Gdx.graphics.getWidth()/ (float)Gdx.graphics.getHeight());
			
		visibleWidth = 5;
		visibleHeight = 5f/aspect;
		
		// set worldCamera stuff
		worldCamera.setMinDimensionVec(new Vector2(5f,5f/aspect));
		worldCamera.setAspectRatio(aspect);
		worldCamera.generateCameraMatrix();
		
		// resize the background image to cover the entire area
		Vector2 currentSize =currentBackgroundElement.getCurrentSize();
		Vector2 resize = currentBackgroundElement.getResizeVec();
		float resizeWidth = visibleWidth / currentSize.x * resize.x;
		float resizeHeight = visibleHeight / currentSize.y * resize.y;
		currentBackgroundElement.setResizeScale(Math.max(resizeWidth,resizeHeight));
	}


	@Override
	public void dispose() {
		//Gdx.app.log("dispose", "blah");
		unloadAssetsAndObjects();
		clickSound.dispose();
		clickSound = null;
		bombSound.dispose();
		bombSound = null;
	}

	public GameState getGameState() {
		return gameState;
	}


	public void upCharacter(){
		playerCharacter.setPosition(null);
	}

	public void moveCharacter(float x, float y) {
		playerCharacter.setPosition(new Vector2(x,y));
		
	}

	public void gameOver() {
		gameState = GameState.LOSE;
		
	}
	
	private void setupEnemyLocations() {
		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < 1 ; i ++){
			enemies.add(new Enemy(new Vector2((float) (Math.random()-0.5)*visibleWidth,(float)(Math.random()-0.5)*visibleWidth),this));
		}
	}
	
	private float elapsedTime = 0;
	private void handleDistance(float runTime){
		//if (enemies == null) return;
		elapsedTime += runTime;

		float minDistance = Float.MAX_VALUE;
		

		if (playerCharacter.getPosition() != null) {
			// otherwise the max value is fine
			for (Enemy enemy : enemies) {
				float enemyDistance = playerCharacter.getPosition().dst(
						enemy.getPosition());

				if (enemyDistance <= 0.5) {
					enemy.applyContactDamage(runTime);
				} else {
					enemy.applyContactMiss(runTime);
				}

				if (enemyDistance <= minDistance) {
					minDistance = enemyDistance;
				}
			}
		} else {
			for (Enemy enemy : enemies) {
				enemy.applyContactMiss(runTime);
			}

		}
		
		
		// arbitrarily, I am going to have the range of the beats be one per
		// second to five per second
		// I am also going to have the sensitivity to distances be from 3 units
		// to 1 unit
		float maxBeatDistance = 4f;
		float minBeatDistance = 0f;
		float maxBeatWavelength = 0.5f;
		float minBeatWavelength = 0.05f;
		float defaultBeatWavelength = 0.7f;
		float frequencyOfBeat;
		if (playerCharacter.getPosition() != null) {
			float relateiveBeatDistnace = Math.max(
					Math.min((minDistance - minBeatDistance)
							/ (maxBeatDistance - minBeatDistance), 1), 0);
			frequencyOfBeat = relateiveBeatDistnace
					* (maxBeatWavelength - minBeatWavelength)
					+ minBeatWavelength;
		} else {
			frequencyOfBeat = defaultBeatWavelength;
		}
		if (elapsedTime >= frequencyOfBeat) {
			elapsedTime = 0;

			Gdx.input.vibrate(3);
			clickSound.play();
			playerCharacter.addLocationPulse();

		}

	}

	private void unloadAssetsAndObjects() {
		// TODO get rid of this?
		//Assets.clearAssets();
	}
	
	public Color getCurrentColor(){
		return currentBackgroundElement.getCurrentColor();
	}


}
