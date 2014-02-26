package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;
import sheep.game.Layer;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;
import sheep.math.Vector2;

/*
 * modellen + logikken til spillet
 */

public class GameLayer extends Layer{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private Background background = new Background(new Image(R.drawable.curlingbackground));

	private int rounds,currentRound,totalStones;
	private ArrayList<CurlingStone> stoneList = new ArrayList<CurlingStone>();
	private Player playerOne,playerTow,currentPlayer;
	private Vector2 nullvector = new Vector2(0.0f, 0.0f); 
	private CurlingStone movingStone;
	private boolean newThrow = false;
	
	
	public GameLayer(int rounds){
		this.rounds = rounds;
		this.currentRound = 0;
		this.totalStones = 16;
		playerOne = new Player();
		playerTow = new Player();
		currentPlayer = playerOne;
	}

	public void update(float dt) {
		//sjekker om det er starten til spilleren
		if (currentPlayer.getState() == 0){
			movingStone = new CurlingStone(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.5f);
			stoneList.add(movingStone);
			totalStones = totalStones - 1;
			Log.d(TAG,Integer.toString(stoneList.size()));
			currentPlayer.setState(1);
		}
		
		if(movingStone.getSpeedX() == 0 && movingStone.getMoved()){
			currentPlayer.setState(2);
		}
		
		for(Sprite i: stoneList){
			i.update(dt);
		}
		endTurn();
	}
	
	public void draw(Canvas canvas, BoundingBox box) {
		background.draw(canvas);
		for(Sprite i: stoneList){
			i.draw(canvas);
		}
	}
	
	public boolean noStonesMove(){
		for(Sprite i: stoneList){
			if (i.getSpeed().getLength() != nullvector.getLength()){
				return false;
			}
		}
		return true;
	}
	
	public void nextPlayer(){
		newThrow = true;
		currentPlayer.setState(0);
		if(currentPlayer == playerOne) currentPlayer = playerTow;
		else currentPlayer = playerOne;
	}
	
	public void addPoints(){
		currentRound = currentRound + 1;
	}
	
	public void evaluateStones(){
		
	}
	
	public void showWinner(){
		
	}
	
	public void endTurn(){
		if (noStonesMove()&&currentPlayer.getState() == 2){
			evaluateStones();
			nextPlayer();
			Log.d(TAG,"noe feil med resten av programmet");
			if(totalStones == 0){
				addPoints();
				newRound();
				if(currentRound == rounds){
					showWinner();
				}
			}
		}
	}
	
	public void newRound(){
	}
	
	public CurlingStone getStone(){
		return this.movingStone;
	}
	
	public boolean getNewThrow(){
		return this.newThrow;
	}
	
	public void setNewThrow(boolean s){
		newThrow = s;
	}
}
