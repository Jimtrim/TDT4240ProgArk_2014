package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import sheep.game.Camera;
import sheep.game.State;
import sheep.game.World;
import sheep.input.TouchListener;
import sheep.math.Vector2;

/*
 * klassen hvor spillet faktisk blir spilst
 */

public class GameState extends State{
	
	private GameLayer gameLayer;
	private World world;
	private Camera camera;
	private float camerax,cameray;
	private DrawStats stats;
	
	public GameState(int rounds, int stones){
		world = new World();
		gameLayer = new GameLayer(rounds,stones);
		stats = new DrawStats(this);
		world.addLayer(gameLayer);				
		addTouchListener(stats.getTouchListener());
		addTouchListener(new Touch());
		camera = world.getCamera();
		camerax = gameLayer.getTrack().getGoalPoint()-GlobalConstants.SCREENWIDTH*0.5f;
		cameray = 0;
	}
	
	public void update(float dt){
		world.update(dt);
		stats.update(dt);
		if (camera != null){
			moveCamera();	
		}
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		try{
		world.draw(canvas);
		if (((CurlingGame)getGame()).getTopState().getClass() == this.getClass())
			stats.draw(canvas);
		if (gameLayer.getStone() != null)	canvas.drawText(Float.toString(gameLayer.getStone().getSpeedX()), 10, 10, new Paint());
		}catch (Exception e){};
	}
	
	public class Touch implements TouchListener{
		private ArrayList<float[]> touchList = new ArrayList<float[]>();
		private float localx,localy;
		
		public boolean onTouchDown(MotionEvent event) {
			if (gameLayer.getCurrentPlayer().getState() == 0){
				localx = event.getX();
				localy = event.getY();
				return true;
			}
			float[] point = {event.getX(),event.getY()};
			touchList.add(point);
			return true;
		}
		
		public boolean onTouchUp(MotionEvent event){
			if(gameLayer.getCurrentPlayer().getState() == 0){	
				camerax = camerax - (event.getX() - localx);
				cameray = cameray - (event.getY() - localy);
				return false;
			}else{
				if (touchList.size() >= 10){
					gameLayer.getStone().move(touchList.subList(touchList.size()-10, touchList.size()-1));
					gameLayer.getStone().resetBrooming();
				}
				touchList.clear();
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
			if (cameraWithinImage()){
				camera.setPosition(new Vector2(camerax,cameray));
			}
			
		}
		else if(gameLayer.getStone().getX() >= GlobalConstants.SCREENWIDTH*0.5f){
			camera.setPosition(new Vector2(gameLayer.getStone().getX()-GlobalConstants.SCREENWIDTH*0.5f,0));
		}	
	}

	private boolean cameraWithinImage() {
		if(cameray < -GlobalConstants.SCREENHEIGHT*.5f){
			cameray = -GlobalConstants.SCREENHEIGHT*.5f;
			return false;
		}
		else if(cameray > GlobalConstants.SCREENHEIGHT*0.5f){
			cameray = GlobalConstants.SCREENHEIGHT*0.5f;
			return false;
		}else if (camerax > gameLayer.getTrack().getLenght()-GlobalConstants.SCREENWIDTH*0.5f){
			camerax = gameLayer.getTrack().getLenght()-GlobalConstants.SCREENWIDTH*0.5f;
			return false;
		}else if(camerax < 0){
			camerax = 0;
			return false;
		}else{
			return true;
		}
	}

	public float getCamerax() {
		return camerax;
	}

	public float getCameray() {
		return cameray;
	}

	public GameLayer getGameLayer() {
		return gameLayer;
	}

}
