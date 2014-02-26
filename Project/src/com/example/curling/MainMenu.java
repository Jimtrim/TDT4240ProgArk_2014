package com.example.curling;

import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.graphics.Canvas;
import android.graphics.Color;

/*
 * hovedmenye til spillet
 */

public class MainMenu extends State implements WidgetListener{
	
	private TextButton StartGame,Exit,Tutorial;
	
	public MainMenu(){
		
		StartGame = new TextButton(GlobalConstants.SCREENWIDTH*0.3f, GlobalConstants.SCREENHEIGHT*0.2f, "Start Game",GlobalConstants.menuFont);
		Tutorial = new TextButton(GlobalConstants.SCREENWIDTH*0.3f, GlobalConstants.SCREENHEIGHT*0.4f,"Tutorial",GlobalConstants.menuFont);
		Exit = new TextButton(GlobalConstants.SCREENWIDTH*0.3f, GlobalConstants.SCREENHEIGHT*0.6f,"Exit",GlobalConstants.menuFont);
		
		addTouchListener(StartGame);
		addTouchListener(Tutorial);
		addTouchListener(Exit);
		
		StartGame.addWidgetListener(this);
		Tutorial.addWidgetListener(this);
		Exit.addWidgetListener(this);
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		canvas.drawColor(Color.BLACK);
		StartGame.draw(canvas);
		Tutorial.draw(canvas);
		Exit.draw(canvas);
	}

	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == StartGame){
			getGame().pushState(new GameStateConfig());
		}else if(action.getSource() == Exit){
			//TODO finn ut av hvordan sheep får kalt main activity finish metode
		}else if(action.getSource() == Tutorial){
			//TODO lag tutorial
		}
	}

}
