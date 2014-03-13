package com.example.curling;

import java.util.Random;

import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.graphics.Canvas;
import android.graphics.Color;

public class PauseMenu extends State implements WidgetListener {
	
	private Random rand;
	private TextButton resumeButton, exitButton, optionsButton;
	
	public PauseMenu() {
		resumeButton = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.3f,"Resume Game",GlobalConstants.menuFont);
		optionsButton = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.5f,"Options Game",GlobalConstants.menuFont);
		exitButton = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.7f,"Exit Game",GlobalConstants.menuFont);
		
		addTouchListener(resumeButton);
		addTouchListener(optionsButton);
		addTouchListener(exitButton);
		resumeButton.addWidgetListener(this);
		optionsButton.addWidgetListener(this);
		exitButton.addWidgetListener(this);
		
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		try{
			
			canvas.drawColor(Color.CYAN);
		}catch (Exception e){};
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		// TODO Auto-generated method stub
		if(action.getSource() == resumeButton){
			getGame().popState();
		}
		if(action.getSource() == optionsButton) {
			
		}
		if(action.getSource() == exitButton) {
			
		}
		
	}
}
