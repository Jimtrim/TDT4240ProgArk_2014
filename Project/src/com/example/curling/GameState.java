package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import sheep.game.Camera;
import sheep.game.State;
import sheep.game.World;
import sheep.input.TouchListener;
import sheep.math.Vector2;

/*
 * klassen hvor spilet faktisk blir spilst
 */

public class GameState extends State {
	
	
	private GameLayer gameLayer;
	private World world;
	private Matrix matrix;
	private Camera camera;
	
	public GameState(int rounds){
		world = new World();
		gameLayer = new GameLayer(rounds);
		world.addLayer(gameLayer);
		addTouchListener(new Touch());
		matrix = new Matrix();
		matrix.setScale(1, 1);
		camera = world.getCamera();
	}
	
	public class Touch implements TouchListener{
		private ArrayList<float[]> touchList = new ArrayList<float[]>();
		public boolean onTouchDown(MotionEvent event) {
			if (gameLayer.getCurrentPlayer().getState() == 0){
				gameLayer.getCurrentPlayer().setState(1);
				resetCamera();
				return false;
			}else{
			float[] point = {event.getX(),event.getY()};
			touchList.add(point);
			return true;
			}
		}
		
		public boolean onTouchUp(MotionEvent event){
			if (touchList.size() >= 10){
				gameLayer.getStone().move(touchList.subList(touchList.size()-10, touchList.size()-1));
			}
			touchList = new ArrayList<float[]>();
			return false;
		}

		public boolean onTouchMove(MotionEvent event) {
			float[] point = {event.getX(),event.getY()};
			if (touchList.size()>0)	if (!equalFloatPoints(touchList.get(touchList.size()-1),point)) touchList.add(point);
			if (touchList.size() >= 10){
				gameLayer.getStone().move(touchList.subList(touchList.size()-10, touchList.size()));
			}
			return true;
		}

		private boolean equalFloatPoints(float[] fs, float[] fs2) {
			if(fs[0] == fs2[0] && fs[1] == fs2[1]) return true;
			return false;
		}
		
	}
	
	public void update(float dt){
		world.update(dt);
		moveCamera();
	}
	
	public void draw(Canvas canvas){
		world.draw(canvas);
		if (gameLayer.getStone() != null)	canvas.drawText(Float.toString(gameLayer.getStone().getSpeedX()), 10, 10, new Paint(20));
	}
	
	public void resetCamera(){
		camera.setPosition(new Vector2(0,0));
	}
	
	public void moveCamera(){
		if (gameLayer.getCurrentPlayer().getState() == 0){
			camera.setPosition(new Vector2(gameLayer.getTrack().getHogLine(),0));
		}
		else if(gameLayer.getStone().getX() >= GlobalConstants.SCREENWIDTH*0.5f){
			camera.setPosition(new Vector2(gameLayer.getStone().getX()-GlobalConstants.SCREENWIDTH*0.5f,0));
		}	
	}

}
