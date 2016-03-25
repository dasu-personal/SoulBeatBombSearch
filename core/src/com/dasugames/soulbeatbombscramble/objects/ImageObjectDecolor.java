package com.dasugames.soulbeatbombscramble.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dasugames.soulbeatbombscramble.DasuScreen;

/**
 * Given the visual style of this game, I need to be able to add / remove
 * color components to a given (black and white) image.
 * @author darren.sue
 *
 */
public class ImageObjectDecolor extends ImageObjectSimple {
	private float elapsedTime = -1;
	private float targetTime = -1;
	private final Color WHITE_STABLE = new Color(1,1,1,1);
	private Color targetColor = WHITE_STABLE;
	private Color startColor = WHITE_STABLE;

	

	public ImageObjectDecolor(DasuScreen screenWorld, String asset,
			Vector2 nPositionVec, float nParallaxScale, float nRotationScale,
			Vector2 nResizeVec) {
		super(screenWorld, asset, nPositionVec, nParallaxScale, nRotationScale,
				nResizeVec);
		sprite.setColor(WHITE_STABLE);
	}
	

	public void setColorTarget(Color color, float timeLength){

		this.targetTime = timeLength;
		this.elapsedTime = 0;
		this.targetColor = color;
	}
	
	
	float remainingTimePosition = -1;
	Vector2 targetPosition;
	public void setTargetPosition(Vector2 vector, float timeLength){
		remainingTimePosition = timeLength;
		targetPosition = vector;
	}
	
	public void setConstantColor(Color color){
		sprite.setColor(color);
		this.targetColor = color;
		this.startColor = color;
		targetTime = -1;
		this.elapsedTime = -1;
	}
	
	public void update(float deltaTime){
		if (targetTime > elapsedTime && targetTime > 0){
			elapsedTime += deltaTime;
			float interpolationCoefficient = Math.max(Math.min(	elapsedTime / targetTime,1),0);

			sprite.setColor(startColor.cpy().lerp(targetColor, interpolationCoefficient));
			
		}
		if (remainingTimePosition > 0){
			float interpolationCoefficient = Math.max(Math.min(	deltaTime / remainingTimePosition,1),0);
			positionVec.lerp(targetPosition, interpolationCoefficient);
			remainingTimePosition -= deltaTime;
		}
	}
	
	public Color getCurrentColor(){
		return sprite.getColor();
	}
}
