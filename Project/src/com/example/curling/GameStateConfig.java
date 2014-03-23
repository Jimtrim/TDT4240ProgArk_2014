package com.example.curling;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

/*
 * klassen hvor man bestemmer hvor mange runder man vil spille
 */

public class GameStateConfig extends State implements WidgetListener{
	
	private TextButton startGame, removeRound;
	ImageButton addRound;
	private Paint numberOfRounds;
	private int gameRounds = 10;
	private Random rand = new Random();
	private int T,B,G;
	private float time = 0;

	public GameStateConfig(){
		
		startGame = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.4f,"Start Game",GlobalConstants.menuFont);
		addRound = new ImageButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.6f,new Image(R.drawable.addidle),new Image(R.drawable.addpressed));
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
		time = time + dt;
		if (time >= 0.25){
			T = rand.nextInt(255);
			B = rand.nextInt(255);
			G = rand.nextInt(255);
			time = 0;
		}
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		try{
			canvas.drawColor(Color.rgb(T, B, G));
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
		if(action.getSource() == addRound && gameRounds>=2 && gameRounds<20) {
			gameRounds+=2;
		}
		if(action.getSource() == removeRound && gameRounds>2 && gameRounds<=20) {
			gameRounds-=2;
		}
	}
	
}
