package com.example.curling;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Background extends Sprite{

	public Background(Image image){
		super(image);
		setOffset(0, 0);
		setPosition(0,0);
	}
	public void update(float dt){
		super.update(dt);
		setPosition(0, 0);
	}
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
}
