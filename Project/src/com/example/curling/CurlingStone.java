package com.example.curling;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import sheep.collision.Polygon;
import sheep.collision.Shape;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

public class CurlingStone extends Sprite{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private Matrix matrix;	
	private Sprite collidedStone = null; 
	private boolean moved = false,brooming = false,broomingUp = false,broomingDown = false;
	private float speedX,speedY,ax,ay,spin;
    private float startMarkerX = GlobalConstants.SCREENWIDTH*0.3f;
	private Vector2 target;
	private float friction = 1;
	private static Image red = new Image(R.drawable.curling);
	private static Image yellow = new Image(R.drawable.curlingyellow);
	private int stoneIndex;
	private double picLength;
	private Shape shape;
	private float spinFriction = 0.9f;
	
	public CurlingStone(float x,float y,int playerIndex, Vector2 target){
		super(red);
		this.speedX = 0;
		this.setStoneIndex(0);
		this.target = target;
        this.ax = 50;
        this.spin = 0;
        this.picLength = red.getHeight();
        this.matrix = new Matrix();
        this.shape = new Polygon(red.getBoundingBox().getPoints());
		if(playerIndex == 1) {
			setView(yellow);
			this.setStoneIndex(1);
		}
        updateMatrix();
        setPosition(x, y);
	}
	
	public void update(float dt){
		super.update(dt);
		updateMatrix();
		shape.update(dt, matrix);
		if (spin != 0) {
			spin = (float) (spin*0.995); 
			rotate(-spin);
			if(speedX==0)
				spin=0;
		}
		if(brooming){
			if(friction > 0.5f) friction = friction-0.01f;
		}else{
			if (friction <1){
				friction = friction + 0.01f;
			}
		}
		if(speedX != 0 || speedY != 0){
			speedX = speedX - (this.ax*dt)*friction;
			speedY = speedY - (this.ay*dt)*friction;

			if(speedX <= 0){
				speedX = 0;
                speedY = 0;
            }
			setSpeed(speedX, speedY);
		}
	}
	
	public void draw(Canvas canvas){
		getView().draw(canvas, matrix);
	}
	
	public void move(List<float[]> touchList){
		if (!moved&&getFactor(touchList)>0){
            speedX = velocity()*getFactor(touchList);
            speedY = speedX*diff();
            Log.d(TAG,"akselerasjonen i y rettning: " + Float.toString(ay));
			setSpin(touchList);
			setAy();
//			Log.d(TAG,Float.toString(getFactor(touchList)));
			moved = true;
			setSpeed(speedX, speedY);
		}else{
			//TODO legge til kostefunksjon
			float avarageY = 0;
			for(float[] i:touchList){
				avarageY = avarageY + i[1];
			}
			avarageY = avarageY/touchList.size();
//			Log.d(TAG,Float.toString(avarageY));
			if(avarageY < GlobalConstants.SCREENHEIGHT*0.25){
				//TODO legg til spinn i riktig rettning
				resetBrooming();
			}else if(avarageY > GlobalConstants.SCREENHEIGHT*0.75){
				//TODO legg til spinn i riktig rettning
				resetBrooming();
			}else{
				resetBrooming();
				this.brooming = true;
			}
		}
	}
	
	public void resetBrooming(){
		this.brooming = false;
		this.broomingDown = false;
		this.broomingUp = false;
	}
	
	public Shape getShape(){
		return this.shape;
	}
	
	public boolean collides(Sprite sprite) {
		
		// Check mask.
		if(((getGroup() & sprite.getMask()) != 0) || ((sprite.getGroup() & getMask()) != 0))
			return false;
		
		// If one of the sprites have no shape, then they can't collide.
		if(shape == null || ((CurlingStone)sprite).getShape() == null)
			return false;
		
		boolean collided = shape.collides(((CurlingStone)sprite).getShape());
		
		return collided;
	}
	
	public void setSpin(List<float[]> touchList){
		float spinDiff = touchList.get(0)[1] - touchList.get(touchList.size()-1)[1];
		if ((Math.abs(spinDiff)>(GlobalConstants.SCREENHEIGHT*0.10)) && (Math.abs(spinDiff) < GlobalConstants.SCREENHEIGHT*0.90)) {
			if (spinDiff > 0) {
				spin = 7; 
			}else{
				spin = -7;
			}
		}
	}
	
	public float getFactor(List<float[]> touchList){
		float factor = 0;
		for(int i = 1; i < touchList.size(); i ++){
            factor = factor + touchList.get(i)[0] - touchList.get(0)[0];
        }
        if (factor-200 >= velocity()){
        	if ((factor-200)/velocity() >1.25f){
        		return 1.25f;
        	}
            return (factor-200)/velocity();
        }
        else if (factor+200 <= velocity()){
            return (factor+200)/velocity();
        }
		return 1;
	}
	
	public void setAy(){
    	this.ay = (this.speedY)/(1/(this.ax/this.speedX));
    }

    //find speed in y-direction to get the scaling correct with the x-speed
    public float diff(){
        return (target.getY()-GlobalConstants.SCREENHEIGHT*0.5f)/(target.getX()-startMarkerX);
    }

    //perfect velocity
    public float velocity(){
        return ((float) Math.sqrt((double) 2*(this.ax)*(target.getX()-getX())));
    }	
	
	private void updateMatrix() {
		matrix.reset();
		matrix.preTranslate((getPosition().getX()-getOffset().getX()), (getPosition().getY()-getOffset().getY())); 
		matrix.preRotate(getOrientation(), red.getWidth()/2, red.getHeight()/2);
		matrix.preScale(1.0f, 1.0f);
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
			
			this.setAy();
			
			this.setSpin(this.getSpin() * spinFriction);
			
			((CurlingStone)sprite).setSpin(-1 * this.getSpin() * spinFriction);
			
			((CurlingStone)sprite).setSpeedX((float) vx2);
			((CurlingStone)sprite).setSpeedY((float)vy2);
			
			((CurlingStone)sprite).setAy();
		}
	}
	
	public double getLengthBetweenStone(double dx, double dy){
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public double getDx(Sprite stoneHitter, Sprite stoneHurt){
		return Math.abs(stoneHurt.getX()-stoneHitter.getX());
	}
	
	public double getDy(Sprite stoneHitter, Sprite stoneHurt){
		return stoneHurt.getY()-stoneHitter.getY();
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
	
	public int getStoneIndex() {
		return stoneIndex;
	}

	public int setStoneIndex(int stoneIndex) {
		this.stoneIndex = stoneIndex;
		return stoneIndex;
	}
	
	public double getLengthOfStone(){
		return picLength; 
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
    
    public void setSpeedY(float speed){ 
    	this.speedY = speed; 
    }
    
    public boolean getBrooming(){
    	return this.brooming;
    }
    
    public float getSpin(){
    	return this.spin;
    }
    
    public void setSpin(float spin){
    	this.spin = spin;
    }
}
