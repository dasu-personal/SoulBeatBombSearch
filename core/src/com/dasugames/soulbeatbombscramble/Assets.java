package com.dasugames.soulbeatbombscramble;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Assets {
	private static Map<String, Texture> loadedTextures = new HashMap<String,Texture>();
	private static Map<TextureRegionKey,TextureRegion> loadedTextureRegions = new HashMap<TextureRegionKey,TextureRegion>();
	private static void clearTextures(){
		for (Texture oldTexture: loadedTextures.values()){
			oldTexture.dispose();
		}
		loadedTextures = new HashMap<String,Texture>();
	}
	private static void clearTextureRegions(){
		loadedTextureRegions = new HashMap<TextureRegionKey,TextureRegion>();
	}
	public static void clearAssets(){
		clearTextureRegions();
		clearTextures();
	}
	
	public static Texture loadTexture(String textureName){
		if (loadedTextures.containsKey(textureName)){
			return loadedTextures.get(textureName);
		} else {
			Texture texture = new Texture(Gdx.files.internal(textureName));
			texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			loadedTextures.put(textureName, texture);
			return texture;
		}
	}
	
	public static TextureRegion loadTextureRegion(String textureName){
		Texture texture = loadTexture(textureName);
		Vector2 lowerLeft = new Vector2(0,0);
		// TODO I dont know whether this should be just width or height
		// or whether I legitimately need to decrease this by one
		// but probably doesn't matter at this point
		long height = texture.getHeight();
		long width = texture.getWidth();
		Vector2 upperRight = new Vector2(width,height);
		
		TextureRegionKey textureRegionKey = new TextureRegionKey(textureName, lowerLeft,upperRight);
		if (loadedTextureRegions.containsKey(textureRegionKey)){
			return loadedTextureRegions.get(textureRegionKey);
		} else {
			TextureRegion textureRegion = new TextureRegion(texture);
			loadedTextureRegions.put(textureRegionKey, textureRegion);
			return textureRegion;
		}
	}
	
	public static TextureRegion loadTextureRegion(String textureName, Vector2 lowerLeft, Vector2 upperRight){
		TextureRegionKey textureRegionKey = new TextureRegionKey(textureName, lowerLeft,upperRight);
		if (loadedTextureRegions.containsKey(textureRegionKey)){
			return loadedTextureRegions.get(textureRegionKey);
		} else {
			Texture texture = loadTexture(textureName);
			TextureRegion textureRegion = new TextureRegion(texture,lowerLeft.x,lowerLeft.y,upperRight.x,upperRight.y);
			loadedTextureRegions.put(textureRegionKey, textureRegion);
			return textureRegion;
		}
	}
	
	private static class TextureRegionKey{
		public String textureName;
		public Vector2 lowerLeft, upperRight;
	    public TextureRegionKey(String nTextureName, Vector2 nLowerLeft, Vector2 nUpperRight) {
	    	textureName = nTextureName;
	    	lowerLeft = nLowerLeft;
	    	upperRight = nUpperRight;
	    }
	    @Override
	    public int hashCode(){
	    	return (textureName + ",upperLeft:(" + lowerLeft.x +","+ lowerLeft.y
	    			+"),lowerRight:(" + upperRight.x +","+ upperRight.y + ")").hashCode();
	    }
	    
	    @Override
	    public  boolean equals(Object other){
	    	 TextureRegionKey otherTRK = (TextureRegionKey)other;
	    	 return textureName.equals(otherTRK.textureName)
	    			 && lowerLeft.x == otherTRK.lowerLeft.x && lowerLeft.y == otherTRK.lowerLeft.y
	    			 && upperRight.x == otherTRK.upperRight.x && upperRight.y == otherTRK.upperRight.y;
	    }
	    
	}
	
	// these following two do not really obey the model that I am using here,
	// as this class is only designed to coordinate assets between many objects
	/*
	public void putTextures(String nTextureName){
		if (loadedTextures.containsKey(nTextureName)) return;
		Texture texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
	public Texture getTextures(String textureName){
		if (loadedTextures.containsKey(textureName)){
			return loadedTextures.get(textureName);
		} else {
			return null;
		}
			
	}
	*/
}
