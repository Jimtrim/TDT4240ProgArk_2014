package com.example.curling;

import android.graphics.Canvas;
import android.graphics.Matrix;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Broom extends Sprite{
 
	private Matrix matrix;
	private static Image broom = new Image(R.drawable.curlingbroom); //temp image
	
	public Broom(float x, float y) {
		super(broom);
		matrix = new Matrix();
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		getView().draw(canvas, matrix);
	}
	
}
	
	
