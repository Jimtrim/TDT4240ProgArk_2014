package com.example.curling;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

public class CurlingStone extends Sprite{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	Matrix matrix;
	
	
	
	private CurlingStone collidedStone = null; 
	private boolean moved = false;
	private float speedX;
	private float speedY;
    private float startMarkerX = GlobalConstants.SCREENWIDTH*0.3f;
    private float acceleration;
	private float friction = 2.0f;
    private float SPIN;
	private Vector2 target;
    private float factor;
    private float diff;
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
        this.acceleration = 50;
        this.SPIN = 0;
        this.diff = diff();
        
        matrix = new Matrix();
        updateMatrix();
	}
	
	public void update(float dt){
		super.update(dt);
		updateMatrix();
		if (SPIN != 0) {
			SPIN = (float) (SPIN*0.99); 
			this.rotate(-SPIN);
			if(speedX==0)
				SPIN=0;
		}
		if(speedX != 0 || speedY != 0){
			speedX = speedX - (this.acceleration*dt);
            speedY = speedY - (this.acceleration*dt*diff);
			if(speedX <= 0){
				speedX = 0;
                speedY = 0;
            }
            else if(speedX <= 0 && speedY!=0){
                speedX = 0;
                speedY = 0;
            }
			setSpeed(speedX, speedY);
		}
	}
	
	public void move(List<float[]> touchList){
		Log.d(TAG,makeString(touchList));
		if (!moved){
			for(int i = 1; i < touchList.size(); i ++){
                factor = factor + touchList.get(i)[0] - touchList.get(i-1)[0];
            }
            factor = factor/(touchList.size()-1);
            factor = factor/(touchList.get(5)[0]-touchList.get(4)[0]);
            Log.d(TAG,Float.toString(factor));
            speedX = velocity();
            speedY = diff()*speedX;

			setSpeed(speedX, speedY);
			moved = true;
			
			float spinDiff = touchList.get(0)[1] - touchList.get(touchList.size()-1)[1];
			if ((Math.abs(spinDiff)>(GlobalConstants.SCREENHEIGHT*0.24)) && (Math.abs(spinDiff) < GlobalConstants.SCREENHEIGHT*0.51)) {
				if (spinDiff > 0) {
					SPIN = 10; 
				}
				else {
					SPIN = -10;
				}
		}
		
			
		}
	}
	
	public void draw(Canvas canvas){
//		super.draw(canvas);
		getView().draw(canvas, matrix);
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
		return this.speedY;
	}
    public void setSpeedY(float speed) { this.speedY = speed; }

    //find speed in y-direction to get the scaling correct with the x-speed
    public float diff(){
        return (target.getY()-GlobalConstants.SCREENHEIGHT*0.5f)/(target.getX()-startMarkerX);
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
    //perfect velocity
    public float velocity(){
        return ((float) Math.sqrt((double) 2*(this.acceleration)*(target.getX()-getX())));
    }

	public int getStoneIndex() {
		return stoneIndex;
	}

	public int setStoneIndex(int stoneIndex) {
		this.stoneIndex = stoneIndex;
		return stoneIndex;
	}
		
	
	private void updateMatrix() {
		matrix.reset();
		matrix.preRotate(getOrientation(), red.getWidth()/2, red.getHeight()/2);
		matrix.postTranslate((getPosition().getX()-getOffset().getX()), (getPosition().getY()-getOffset().getY())); 
	}
	

}
