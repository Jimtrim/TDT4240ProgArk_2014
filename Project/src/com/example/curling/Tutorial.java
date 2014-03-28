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
	private Image tutorial1 = new Image(R.drawable.tutorial1);
	private Image tutorial2 = new Image(R.drawable.tutorial2);
	private Image tutorial3 = new Image(R.drawable.tutorial3);
	private Image tutorial4 = new Image(R.drawable.tutorial4);
	private Image tutorial5 = new Image(R.drawable.tutorial5);
	private Image tutorial6 = new Image(R.drawable.tutorial6);
	private Image tutorial7 = new Image(R.drawable.tutorial7);
	private Image tutorial8 = new Image(R.drawable.tutorial8);
	private Image tutorial9 = new Image(R.drawable.tutorial9);
	private Image tutorial10 = new Image(R.drawable.tutorial10);
	private Image tutorial11 = new Image(R.drawable.tutorial11);
	private Image tutorial12 = new Image(R.drawable.tutorial12);
	private Image tutorial13 = new Image(R.drawable.tutorial13);
	private Image tutorial14 = new Image(R.drawable.tutorial14);
	private Image tutorial15 = new Image(R.drawable.tutorial15);
	private Image tutorial16 = new Image(R.drawable.tutorial16);
	private Image tutorial17 = new Image(R.drawable.tutorial17);
	private Image tutorial18 = new Image(R.drawable.tutorial18);
	private Image tutorial19 = new Image(R.drawable.tutorial19);
	private Image tutorial20 = new Image(R.drawable.tutorial20);
	private Image tutorial21 = new Image(R.drawable.tutorial21);
	private Image tutorial22 = new Image(R.drawable.tutorial22);
	private Image tutorial23 = new Image(R.drawable.tutorial23);
	private Image tutorial24 = new Image(R.drawable.tutorial24);
	private Image currentImage;
	private int imageIndex = 0;
	private static Tutorial Instance = new Tutorial();
	
	private Matrix matrix = new Matrix();
	
	public Tutorial(){
		this.tutorial.add(tutorial1);
		this.tutorial.add(tutorial2);
		this.tutorial.add(tutorial3);
		this.tutorial.add(tutorial4);
		this.tutorial.add(tutorial5);
		this.tutorial.add(tutorial6);
		this.tutorial.add(tutorial7);
		this.tutorial.add(tutorial8);
		this.tutorial.add(tutorial9);
		this.tutorial.add(tutorial10);
		this.tutorial.add(tutorial11);
		this.tutorial.add(tutorial12);
		this.tutorial.add(tutorial13);
		this.tutorial.add(tutorial14);
		this.tutorial.add(tutorial15);
		this.tutorial.add(tutorial16);
		this.tutorial.add(tutorial17);
		this.tutorial.add(tutorial18);
		this.tutorial.add(tutorial19);
		this.tutorial.add(tutorial20);
		this.tutorial.add(tutorial21);
		this.tutorial.add(tutorial22);
		this.tutorial.add(tutorial23);
		this.tutorial.add(tutorial24);
		this.matrix.setScale(GlobalConstants.SCREENWIDTH/tutorial1.getWidth(), GlobalConstants.SCREENHEIGHT/tutorial1.getHeight());
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
				getGame().popState();
				imageIndex = 0;
				currentImage = tutorial.get(imageIndex);
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
