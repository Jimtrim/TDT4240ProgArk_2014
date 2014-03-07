package com.example.curling;

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
	
	private TextButton StartGame,Exit,Tutorial;
	private float scalex,scaley;
	private Matrix matrix = new Matrix();
	public Image mainMenuScreen = new Image(R.drawable.frontscreencurling);
	
	public MainMenu(){
		
		StartGame = new TextButton(GlobalConstants.SCREENWIDTH*0.1f, GlobalConstants.SCREENHEIGHT*0.4f, "Start Game",GlobalConstants.menuFont);
		Tutorial = new TextButton(GlobalConstants.SCREENWIDTH*0.1f, GlobalConstants.SCREENHEIGHT*0.55f,"Tutorial",GlobalConstants.menuFont);
		Exit = new TextButton(GlobalConstants.SCREENWIDTH*0.1f, GlobalConstants.SCREENHEIGHT*0.7f,"Exit",GlobalConstants.menuFont);
		
		addTouchListener(StartGame);
		addTouchListener(Tutorial);
		addTouchListener(Exit);
		
		StartGame.addWidgetListener(this);
		Tutorial.addWidgetListener(this);
		Exit.addWidgetListener(this);
		
		this.scaley = GlobalConstants.SCREENHEIGHT/mainMenuScreen.getHeight();
		this.scalex = GlobalConstants.SCREENWIDTH/mainMenuScreen.getWidth();
		this.matrix.setScale(scalex, scaley);
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		mainMenuScreen.draw(canvas, this.matrix);
		StartGame.draw(canvas);
		Tutorial.draw(canvas);
		Exit.draw(canvas);
	}

	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == StartGame){
			getGame().pushState(new GameStateConfig());
		}else if(action.getSource() == Exit){
			//TODO finn ut av hvordan sheep f�r kalt main activity finish metode
		}else if(action.getSource() == Tutorial){
			//TODO lag tutorial
		}
	}

}
