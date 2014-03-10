package com.example.curling;

import android.graphics.Canvas;
import android.graphics.Color;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.widget.SeekBar;

/*
 * klassen hvor man bestemmer hvor mange runder man vil spille
 */

public class GameStateConfig extends State implements WidgetListener{
	
	private TextButton startGame;
	private SeekBar numberOfRounds;
	
	
	public GameStateConfig(){
		
		startGame = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.4f,"Start Game",GlobalConstants.menuFont);
		numberOfRounds = new SeekBar(GlobalConstants.this);
		
		addTouchListener(startGame);
		startGame.addWidgetListener(this);
	}
	
	public void update(float dt){
		super.update(dt);
		
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		try{
		canvas.drawColor(Color.CYAN);
		startGame.draw(canvas);
		}catch (Exception e){};
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == startGame){
			getGame().pushState(new GameState(10));
		}
	}
	
}
