package com.dasugames.soulbeatbombscramble;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SoulBeatBombScramble extends Game {
	SpriteBatch batch;
	Texture img;
	Preferences prefs;
	
	Music levelMusic;
	Music titleMusic;
	Music currentMusic;
	public SoulBeatBombScramble(){
		

	}
	
	@Override
	public void create () {
		prefs = Gdx.app.getPreferences("soulBeatBombSearch");
		levelMusic = Gdx.audio.newMusic(Gdx.files.internal("music/testForNow.ogg"));
		titleMusic = Gdx.audio.newMusic(Gdx.files.internal("music/titleAttempt2.ogg"));
		setScreen(new TitleScreen(this));

		
		
	}
	
	public boolean offerScore(int currentScore){
		if (currentScore >= prefs.getInteger("highScore", 0)){
			prefs.putInteger("highScore", currentScore);
			prefs.flush();
			return true;
		} else {
			return false;
		}
	}

	
	public int getScore(){
		return prefs.getInteger("highScore",0);
	}


	public void startLevelMusic(){
		currentMusic = levelMusic;
		currentMusic.setLooping(false);
		constantMusicVolume(1.0f);
		currentMusic.play();
	}
	
	public void startTitleMusic(){
		currentMusic = titleMusic;
		currentMusic.setLooping(true);
		constantMusicVolume(1.0f);
		currentMusic.play();
	}
	public void endMusic(){
		if (currentMusic != null){
			currentMusic.stop();
		}

	}
	
	
	public void update(float time){
		if (remainingTimeForFade > 0){
			currentMusic.setVolume(Math.min(time/remainingTimeForFade,1) * (targetVolume - levelMusic.getVolume()) + levelMusic.getVolume());
			remainingTimeForFade -= time;
		}
	}
	
	public void setTargetMusicVolume(float volume, float timeForFade){
		remainingTimeForFade = timeForFade;
		targetVolume = volume;
	}
	

	private float remainingTimeForFade;
	private float targetVolume = 1;
	public void constantMusicVolume(float volume){
		currentMusic.setVolume(volume);
		targetVolume = 1;
		remainingTimeForFade = -1;
	}
	
	@Override
	public void dispose(){
		levelMusic.dispose();
		levelMusic = null;
		titleMusic.dispose();
		titleMusic = null;
		currentMusic = null;
	}
}
