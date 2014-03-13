package com.example.curling;

import java.util.List;

import android.graphics.Canvas;
import android.util.Log;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

public class CurlingStone extends Sprite{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private CurlingStone collidedStone = null; 
	private boolean moved = false;
	private float speedX;
	private float speedY;
    private float differentiation;
	private float friction = 2.0f;
	
	private Vector2 target;
	
	private static Image red = new Image(R.drawable.curling);
	private static Image yellow = new Image(R.drawable.curlingyellow);
	
	public CurlingStone(float x,float y,int playerIndex, Vector2 target){
		super(red);
		this.speedX = 0;
		setPosition(x, y);
		if(playerIndex == 1) setView(yellow);
		this.target = target;
	}
	
	public void update(float dt){
		super.update(dt);
		if(speedX != 0){
			speedX = speedX - friction;
			if(speedX < 0){
				speedX = 0;
			}
			setSpeed(speedX, 0);
		}
	}
	
	public void move(List<float[]> touchList){
		Log.d(TAG,makeString(touchList));
		if (!moved){
			for(int i = 1; i < touchList.size(); i ++){
				speedX = speedX + touchList.get(i)[0] - touchList.get(0)[0];
			}
            differentiation = speedX/(9*(touchList.get(1)[0])-touchList.get(0)[0]);
			setSpeed(speedX, 0);
			moved = true;
		}
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	public String makeString(List<float[]> list){
		String s = "";
		for(int i = 0; i < list.size();i++){
			s = s + Float.toString(list.get(i)[0]) + " : " + Float.toString(list.get(i)[1]) + " ";
		}
		return s;
	}
	
	public float getSpeedX(){
		return this.speedX;
	}
	
	public float getSpeedY(){
		return this.speedX;
	}
	
	public boolean getMoved(){
		return this.moved;
	}
	
	public void setSpeedY(float SpeedY){
		this.speedY = speedY;
	}
	
	public void setCollidedStone(CurlingStone stone){
		this.collidedStone = stone; 
	}
	
	public CurlingStone getCollidedStone(){
		return this.collidedStone;
	}
	
	public void setSpeedX(float speedx){
		this.speedX = speedx;
	}

    public Vector2 setTarget (){
        return target;
    }

}
