package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

public class Tutorial extends State{

	private ArrayList<Image> tutorial = new ArrayList<Image>();
private Image currentImage;
	private int imageIndex = 0;
	private static Tutorial Instance = new Tutorial();
	
	private Matrix matrix = new Matrix();
	
	public Tutorial(){
		this.tutorial.add(new Image(R.drawable.tutorial1));
		this.tutorial.add(new Image(R.drawable.tutorial2));
		this.tutorial.add(new Image(R.drawable.tutorial3));
		this.tutorial.add(new Image(R.drawable.tutorial4));
		this.tutorial.add(new Image(R.drawable.tutorial5));
		this.tutorial.add(new Image(R.drawable.tutorial6));
		this.tutorial.add(new Image(R.drawable.tutorial7));
		this.tutorial.add(new Image(R.drawable.tutorial8));
		this.tutorial.add(new Image(R.drawable.tutorial9));
		this.tutorial.add(new Image(R.drawable.tutorial10));
		this.tutorial.add(new Image(R.drawable.tutorial11));
		this.tutorial.add(new Image(R.drawable.tutorial12));
		this.tutorial.add(new Image(R.drawable.tutorial13));
		this.tutorial.add(new Image(R.drawable.tutorial14));
		this.tutorial.add(new Image(R.drawable.tutorial15));
		this.tutorial.add(new Image(R.drawable.tutorial16));
		this.tutorial.add(new Image(R.drawable.tutorial17));
		this.tutorial.add(new Image(R.drawable.tutorial18));
		this.tutorial.add(new Image(R.drawable.tutorial19));
		this.tutorial.add(new Image(R.drawable.tutorial20));
		this.tutorial.add(new Image(R.drawable.tutorial21));
		this.tutorial.add(new Image(R.drawable.tutorial22));
		this.tutorial.add(new Image(R.drawable.tutorial23));
		this.tutorial.add(new Image(R.drawable.tutorial24));
		this.matrix.setScale(GlobalConstants.SCREENWIDTH/tutorial.get(0).getWidth(), GlobalConstants.SCREENHEIGHT/tutorial.get(0).getHeight());
		this.currentImage = tutorial.get(imageIndex);
		this.addTouchListener(new Touch());
	}
	
	public static Tutorial getInstance(){
		return Instance;
	}
	
	public void draw(Canvas canvas){
		currentImage.draw(canvas, matrix);
		canvas.drawText("touch for next", GlobalConstants.SCREENWIDTH*0.8f, GlobalConstants.SCREENHEIGHT*0.8f, new Paint());
	}
	
	private class Touch implements TouchListener{
	
		public boolean onTouchDown(MotionEvent event) {
			return true;
		}
	
		@Override
		public boolean onTouchUp(MotionEvent event) {
			if (imageIndex == tutorial.size()-1){
				imageIndex = 0;
				currentImage = tutorial.get(imageIndex);
				getGame().popState();
			}else{
				imageIndex = imageIndex + 1;
				currentImage = tutorial.get(imageIndex);
			}
			return false;
		}
	
		@Override
		public boolean onTouchMove(MotionEvent event) {
			return true;
		}	
	}
}
