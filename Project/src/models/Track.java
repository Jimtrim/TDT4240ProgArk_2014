package models;

import curling.GlobalConstants;

import android.graphics.Canvas;

import android.graphics.Matrix;
import sheep.game.Sprite;
import sheep.graphics.Image;

/*
 * Track image and information
 * This class is the representation of the track in the game. It is here the game gets the 
 *
 */

public class Track extends Sprite{
	
	Matrix matrix = new Matrix();
	Image image;
	float scale;
	
	public Track(Image image){
		super(image);
		this.image = image;
		this.scale = GlobalConstants.SCREENHEIGHT/image.getHeight();
		matrix.setScale(scale, scale);
		
		setOffset(0, 0);
		setPosition(0,0);
	}
	public void update(float dt){
		super.update(dt);
		setPosition(0, 0);
	}
	public void draw(Canvas canvas){
		image.draw(canvas,matrix);
	}

	public float getHogLine(){
        return 3266*scale;
    }

	public float getLenght(){
		return image.getWidth()*scale;
	}

    public float getHeight(){
        return GlobalConstants.SCREENHEIGHT;
    }
    
    public float getGoalPoint(){
    	return 3901*scale;
    }
    
    public float getOuterCircle(){
    	return 3691 *scale;
    }
}
