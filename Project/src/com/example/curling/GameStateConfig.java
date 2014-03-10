package com.example.curling;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.widget.SeekBar;
import sheep.gui.Widget;

/*
 * klassen hvor man bestemmer hvor mange runder man vil spille
 */

public class GameStateConfig extends State implements WidgetListener{
	
	private TextButton startGame, addRound, removeRound;
	private Paint numberOfRounds;
	private int gameRounds = 10;
	
	
	public GameStateConfig(){
		
		startGame = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.4f,"Start Game",GlobalConstants.menuFont);
		addRound = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.6f,"Add Rounds", GlobalConstants.menuFont);
		removeRound = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.8f, "Remove Rounds",GlobalConstants.menuFont);
		numberOfRounds = new Paint();
		
		addTouchListener(startGame);
		addTouchListener(addRound);
		addTouchListener(removeRound);
		startGame.addWidgetListener(this);
		addRound.addWidgetListener(this);
		removeRound.addWidgetListener(this);
		
		numberOfRounds.setColor(Color.BLACK);
		numberOfRounds.setTextSize(40);
	}
	
	public void update(float dt){
		super.update(dt);
		
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		try{
		canvas.drawColor(Color.YELLOW);
		startGame.draw(canvas);
		addRound.draw(canvas);
		removeRound.draw(canvas);
		canvas.drawText(gameRounds + " Rounds", GlobalConstants.SCREENWIDTH*0.6f, GlobalConstants.SCREENHEIGHT*0.4f, numberOfRounds);
		}catch (Exception e){};
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == startGame){
			getGame().pushState(new GameState(gameRounds));
		}
		if(action.getSource() == addRound && gameRounds>=0 && gameRounds<20) {
			gameRounds+=2;
		}
		if(action.getSource() == removeRound && gameRounds>0 && gameRounds<=20) {
			gameRounds-=2;
		}
	}
	
}
