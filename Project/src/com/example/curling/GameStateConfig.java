package com.example.curling;

import android.graphics.Canvas;
import android.graphics.Color;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

/*
 * klassen hvor man bestemmer hvor mange runder man vil spille
 */

public class GameStateConfig extends State implements WidgetListener{
	
	private TextButton startGame;
	
	public GameStateConfig(){
		
		startGame = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.4f,"Start Game",GlobalConstants.menuFont);
		
		addTouchListener(startGame);
		startGame.addWidgetListener(this);
	}
	
	public void update(float dt){
		super.update(dt);
		
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		canvas.drawColor(Color.CYAN);
		startGame.draw(canvas);
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == startGame){
			getGame().pushState(new GameState(10));
		}
	}
	
}