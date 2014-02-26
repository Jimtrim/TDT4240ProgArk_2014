package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.game.World;
import sheep.input.TouchListener;

/*
 * klassen hvor spilet faktisk blir spilst
 */

public class GameState extends State {
	
	private GameLayer gameLayer;
	private World world;
	
	public GameState(int rounds){
		world = new World();
		gameLayer = new GameLayer(rounds);
		world.addLayer(gameLayer);
		addTouchListener(new Touch());
	}
	
	public class Touch implements TouchListener{
		private ArrayList<float[]> touchList = new ArrayList<float[]>();
		public boolean onTouchDown(MotionEvent event) {
			float[] point = {event.getX(),event.getY()};
			touchList.add(point);
			return true;
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
			touchList.add(point);
			if (touchList.size() >= 10){
				gameLayer.getStone().move(touchList.subList(touchList.size()-10, touchList.size()));
			}
			return true;
		}
		
	}
	
	public void update(float dt){
		world.update(dt);
	}
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		world.draw(canvas);
	}

}
