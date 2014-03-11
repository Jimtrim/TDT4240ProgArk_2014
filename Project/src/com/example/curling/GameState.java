package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import sheep.game.Camera;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Image;
import sheep.input.TouchListener;
import sheep.math.Vector2;

/*
 * klassen hvor spilet faktisk blir spilst
 */

public class GameState extends State {
	
	
	private Image target = new Image(R.drawable.target);
	private GameLayer gameLayer;
	private World world;
	private Camera camera;
	
	private float camerax,cameray;
	
	public GameState(int rounds){
		world = new World();
		gameLayer = new GameLayer(rounds);
		world.addLayer(gameLayer);
		addTouchListener(new Touch());
		camera = world.getCamera();
		camerax = gameLayer.getTrack().getHogLine();
		cameray = 0;
	}
	
	public void update(float dt){
		world.update(dt);
		moveCamera();
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		try{
		world.draw(canvas);
		if(gameLayer.getCurrentPlayer().getState() == 0){
			target.draw(canvas, GlobalConstants.SCREENWIDTH*.5f-target.getHeight()/2,GlobalConstants.SCREENHEIGHT*0.5f-target.getHeight()/2);
		}
		if (gameLayer.getStone() != null)	canvas.drawText(Float.toString(gameLayer.getStone().getSpeedX()), 10, 10, new Paint());
		}catch (Exception e){};
	}
	
	public class Touch implements TouchListener{
		private ArrayList<float[]> touchList = new ArrayList<float[]>();
		private float localx,localy;
		public boolean onTouchDown(MotionEvent event) {
			if (gameLayer.getCurrentPlayer().getState() == 0){
				if(event.getX() > GlobalConstants.SCREENWIDTH*.5f-target.getHeight()/2 && event.getX() < GlobalConstants.SCREENWIDTH*.5f+target.getHeight()/2
						&& event.getY() > GlobalConstants.SCREENHEIGHT*.5f-target.getHeight()/2 && event.getY() < GlobalConstants.SCREENHEIGHT*.5f+target.getHeight()/2){
					gameLayer.setTarget(new Vector2(camerax,cameray));
					gameLayer.getCurrentPlayer().setState(1);
					resetCamera();
				}
				localx = event.getX();
				localy = event.getY();
				return true;
			}else{
			float[] point = {event.getX(),event.getY()};
			touchList.add(point);
			return true;
			}
		}
		
		public boolean onTouchUp(MotionEvent event){
			if(gameLayer.getCurrentPlayer().getState() == 0){
				camerax = camerax - (event.getX() - localx);
				cameray = cameray - (event.getY() - localy);
				return false;
			}else{
				if (touchList.size() >= 10){
					gameLayer.getStone().move(touchList.subList(touchList.size()-10, touchList.size()-1));
				}
				touchList = new ArrayList<float[]>();
				return false;
			}
		}

		public boolean onTouchMove(MotionEvent event) {
			if(gameLayer.getCurrentPlayer().getState() == 0){
				camerax = camerax - (event.getX() - localx);
				cameray = cameray - (event.getY() - localy);
				localx = event.getX();
				localy = event.getY();
				return true;
			}else{
				float[] point = {event.getX(),event.getY()};
				if (touchList.size()>0)	if (!equalFloatPoints(touchList.get(touchList.size()-1),point)) touchList.add(point);
				if (touchList.size() >= 10){
					gameLayer.getStone().move(touchList.subList(touchList.size()-10, touchList.size()));
				}
				return true;
			}
		}

		private boolean equalFloatPoints(float[] fs, float[] fs2) {
			if(fs[0] == fs2[0] && fs[1] == fs2[1]) return true;
			return false;
		}
	}
	
	public void resetCamera(){
		camera.setPosition(new Vector2(0,0));
	}
	
	public void moveCamera(){
		if (gameLayer.getCurrentPlayer().getState() == 0){
			camera.setPosition(new Vector2(camerax,cameray));
		}
		else if(gameLayer.getStone().getX() >= GlobalConstants.SCREENWIDTH*0.5f){
			camera.setPosition(new Vector2(gameLayer.getStone().getX()-GlobalConstants.SCREENWIDTH*0.5f,0));
		}	
	}

}
