package com.example.curling;

import java.util.List;

import android.graphics.Canvas;
import android.util.Log;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class CurlingStone extends Sprite{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private boolean moved = false;
	private static Image image = new Image(R.drawable.curling);
	
	public CurlingStone(float x,float y){
		super(image);
		setPosition(x, y);
	}
	
	public void update(float dt){
		super.update(dt);
		setPosition(getX(), getY());
	}
	
	public void move(List<float[]> touchList){
		Log.d(TAG,makeString(touchList));
		if (!moved){
			float speedx = 0;
			for(int i = 1; i < touchList.size(); i ++){
				speedx = speedx + touchList.get(i)[0] - touchList.get(0)[0];
			}
			setSpeed(speedx, 0);
			moved = true;
		}
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	public String makeString(List<float[]> list){
		String s = "";
		for(int i = 0; i < list.size();i++){
			s = s + Float.toString(list.get(i)[0]) + " : " + Float.toString(list.get(i)[0]) + " ";
		}
		return s;
	}

}
