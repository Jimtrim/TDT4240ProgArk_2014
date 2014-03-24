package com.example.curling;

import android.graphics.Canvas;
import android.graphics.Matrix;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Broom extends Sprite{

	private float place; 
	private Matrix matrix;
	private static Image broom = new Image(R.drawable.curlingbroom); //temp image
	
	public Broom(float x, float y, float place) {
		super(broom);
		this.place = place;
		matrix = new Matrix();
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		getView().draw(canvas, matrix);
	}
	
}
	
	
