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
	
	private Sprite collidedStone = null; 

	private boolean moved = false;
	private float speedX;
	private float speedY;
    private float startMarkerX = GlobalConstants.SCREENWIDTH*0.3f;
    private float ax;
    private float ay;
	private float friction = 2.0f;
    private float SPIN;
	private Vector2 target;
    private float factor;
    private float diff;
	private static Image red = new Image(R.drawable.curling);
	private static Image yellow = new Image(R.drawable.curlingyellow);
	private int stoneIndex;
	
	private double picLength; 
	
	
	
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
        this.ax = 50;
        this.SPIN = 0;
        this.diff = diff();

        matrix = new Matrix();
        updateMatrix();
        this.ay = this.ax*this.diff;
        this.picLength = red.getHeight();
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
			speedX = speedX - (this.ax*dt);
            speedY = speedY - (this.ay*dt);
			if(speedX <= 0){
				speedX = 0;
            }
            if ((float)Math.abs((double)speedY)<1){
                speedY = 0;
            }
			setSpeed(speedX, speedY);
		}
	}
	
	public void move(List<float[]> touchList){
		Log.d(TAG, makeString(touchList));
		if (!moved){
			for(int i = 1; i < touchList.size(); i ++){
                factor = factor + touchList.get(i)[0] - touchList.get(i-1)[0];
            }
            factor = factor/(touchList.size()-1);
            factor = factor/(touchList.get(5)[0]-touchList.get(4)[0]);
            Log.d(TAG,Float.toString(factor));
            speedX = velocity();
            speedY = speedX*diff();

			setSpeed(speedX, speedY);
			moved = true;
			
			float spinDiff = touchList.get(0)[1] - touchList.get(touchList.size()-1)[1];
			if ((Math.abs(spinDiff)>(GlobalConstants.SCREENHEIGHT*0.19)) && (Math.abs(spinDiff) < GlobalConstants.SCREENHEIGHT*0.51)) {
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
    
    public void setAy(float ay){
    	this.ay = ay;
    }
    public void setAx(float ax){
        this.ax = ax;
    }
    
    public void setSpeedY(float speed){ 
    	this.speedY = speed; }

    //find speed in y-direction to get the scaling correct with the x-speed
    public float diff(){
        return (target.getY()-GlobalConstants.SCREENHEIGHT*0.5f)/(target.getX()-startMarkerX);
    }

    public boolean getMoved(){
        return this.moved;
    }

    public void setCollidedStone(Sprite stone){
		this.collidedStone = stone; 
	}
	
	public Sprite getCollidedStone(){
		return this.collidedStone;
	}
    //perfect velocity
    public float velocity(){
        return ((float) Math.sqrt((double) 2*(this.ax)*(target.getX()-getX())));
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

	
	public double getLengthOfStone(){
		return picLength; 
	}

	public double getLengthBetweenStone(double dx, double dy){
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public double getDx(Sprite stoneHitter, Sprite stoneHurt){
		return Math.abs(stoneHurt.getPosition().getX()-stoneHitter.getPosition().getX());
	}
	
	public double getDy(Sprite stoneHitter, Sprite stoneHurt){
		return stoneHurt.getPosition().getY()-stoneHitter.getPosition().getY();
	}
	
	public void collision(Sprite sprite){
		double dx = getDx(this, sprite);
		double dy = getDy(this, sprite);
		
		if (getLengthOfStone() >= getLengthBetweenStone(dx,dy)){

			this.setCollidedStone(sprite);
			((CurlingStone)sprite).setCollidedStone(this);
			
			double ax=dx/getLengthOfStone(), ay=dy/getLengthOfStone();
			
			//component of velocity in the direction of (dx,dy). Projection of the velocities in these axes
			double va1=(this.getSpeedX()*ax+this.getSpeedY()*ay); 
			double vb1=(-this.getSpeedX()*ay+this.getSpeedY()*ax); 
			
			double va2=(((CurlingStone)sprite).getSpeedX()*ax+((CurlingStone)sprite).getSpeedY()*ay);
			double vb2=(-((CurlingStone)sprite).getSpeedX()*ay+((CurlingStone)sprite).getSpeedY()*ax);
			
			double ed = 1; //elastic collision
			double mass = 20;
			
			// New velocities in these axes (after collision)
			double vaP1=va1 + (1+ed)*(va2-va1)/(1+mass/mass);
			double vaP2=va2 + (1+ed)*(va1-va2)/(1+mass/mass);
			
			double vx1=vaP1*ax-vb1*ay,  vy1=vaP1*ay+vb1*ax;// new vx,vy for ball 1 after collision
			double vx2=vaP2*ax-vb2*ay,  vy2=vaP2*ay+vb2*ax;// new vx,vy for ball 2 after collision
			
			this.setSpeedX((float)vx1);
			this.setSpeedY((float)vy1);
			
			//this.setAy(((float) ay));

			((CurlingStone)sprite).setSpeedX((float) vx2);
			((CurlingStone)sprite).setSpeedY((float)vy2);
			
			//((CurlingStone)sprite).setAy(((float)ay));
		}
		
	}
}
