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
    private float startMarkerX = GlobalConstants.SCREENWIDTH*0.3f;
    private float acceleration;
	private float friction = 2.0f;
    private float v0 = 1000 ; //speed to hit marker
    private int SPIN; 
	private Vector2 target;
	private static Image red = new Image(R.drawable.curling);
	private static Image yellow = new Image(R.drawable.curlingyellow);
	private int stoneIndex;
	
	
	
	public CurlingStone(float x,float y,int playerIndex, Vector2 target){
		super(red);
		this.speedX = 0;
		this.setStoneIndex(0);
		setPosition(x, y);
		if(playerIndex == 1) {
			setView(yellow);
			this.setStoneIndex(1);
		}
		this.target = target;
        this.acceleration  = acceleration();
        this.SPIN = 0;
	}
	
	public void update(float dt){
		super.update(dt);
		if(speedX != 0 || speedY != 0){
			speedX = changeSpeedX();
            speedY = speedX*diff();
			if(speedX <= 0){
				speedX = 0;
                speedY = 0;
			}
			setSpeed(speedX, speedY);	
		}
	}
	
	public void move(List<float[]> touchList){
		Log.d(TAG,makeString(touchList));
		if (!moved){
			/*for(int i = 1; i < touchList.size(); i ++){
				speedX = speedX + touchList.get(i)[0] - touchList.get(0)[0];
			}*/
            speedX = v0;
            speedY = diff()*speedX;

			setSpeed(speedX, speedY);
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

    public void setSpeedX(float speed){
        this.speedX = speed;
    }

    public float getSpeedY(){
		return this.speedX;
	}

    //find speed in y-direction to get the scaling correct with the x-speed
    public float diff(){
        return (target.getY()-GlobalConstants.SCREENHEIGHT*0.5f)/(target.getX()-startMarkerX);
    }
    public float changeSpeedX(){
        if ((target.getX()-getX()) > 0){
            return ((float) Math.sqrt((double) 2*(this.acceleration)*(target.getX()-getX())));
        }
        return 0;
    }

    public boolean getMoved(){
        return this.moved;
    }


    public void setCollidedStone(CurlingStone stone){
		this.collidedStone = stone; 
	}
	
	public CurlingStone getCollidedStone(){
		return this.collidedStone;
	}
    //acceleration to get v0 a perfect speed
    public float acceleration(){
        return ((float) Math.pow((double) v0, 2))/(2*(target.getX()-startMarkerX));
    }

	public int getStoneIndex() {
		return stoneIndex;
	}

	public int setStoneIndex(int stoneIndex) {
		this.stoneIndex = stoneIndex;
		return stoneIndex;
	}


}
