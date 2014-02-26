package com.example.curling;

import android.graphics.Canvas;
import android.graphics.Matrix;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Background extends Sprite{

	Matrix matrix = new Matrix();
	Image image;
	
	public Background(Image image){
		super(image);
		this.image = image;
		float scale = GlobalConstants.SCREENHEIGHT/image.getHeight();
		
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
	
}
