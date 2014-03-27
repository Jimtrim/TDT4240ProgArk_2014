package com.example.curling;

import java.util.Random;

import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.graphics.Canvas;
import android.graphics.Matrix;

/*
 * hovedmenye til spillet
 */

public class MainMenu extends State implements WidgetListener{
	
	Random rand = new Random();
	private TextButton StartGame,Tutorial;
	private float scalex,scaley;
	private Matrix matrix = new Matrix();
	private Image mainMenuScreen = new Image(R.drawable.frontscreencurling);
	
	public MainMenu(){
		
		StartGame = new TextButton(GlobalConstants.SCREENWIDTH*0.1f, GlobalConstants.SCREENHEIGHT*0.4f, "Start Game",GlobalConstants.menuFont);
		Tutorial = new TextButton(GlobalConstants.SCREENWIDTH*0.1f, GlobalConstants.SCREENHEIGHT*0.55f,"Tutorial",GlobalConstants.menuFont);
		
		addTouchListener(StartGame);
		addTouchListener(Tutorial);
		
		StartGame.addWidgetListener(this);
		Tutorial.addWidgetListener(this);
		
		this.scaley = GlobalConstants.SCREENHEIGHT/mainMenuScreen.getHeight();
		this.scalex = GlobalConstants.SCREENWIDTH/mainMenuScreen.getWidth();
		this.matrix.setScale(scalex, scaley);
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		try{
		mainMenuScreen.draw(canvas, this.matrix);
		StartGame.draw(canvas);
		Tutorial.draw(canvas);
		}catch(Exception e){};
	}

	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == StartGame){
			getGame().pushState(new GameStateConfig());	
		}else if(action.getSource() == Tutorial){
			//TODO lag tutorial
		}
	}

}
