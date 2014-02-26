package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;
import sheep.game.Layer;
import sheep.game.Sprite;
import sheep.math.BoundingBox;
import sheep.math.Vector2;

/*
 * modellen + logikken til spillet
 */

public class GameLayer extends Layer{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private int rounds,currentRound,totalStones;
	private ArrayList<CurlingStone> stoneList = new ArrayList<CurlingStone>();
	private Player playerOne,playerTow,currentPlayer;
	private Vector2 nullvector = new Vector2(0, 0); 
	private CurlingStone movingStone;
	
	public GameLayer(int rounds){
		this.rounds = rounds;
		this.currentRound = 0;
		this.totalStones = 16;
		playerOne = new Player();
		playerTow = new Player();
		currentPlayer = playerOne;
	}

	public void update(float dt) {
		if (currentPlayer.getState() == 0){
			movingStone = new CurlingStone(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.5f);
			stoneList.add(movingStone);
			totalStones = totalStones - 1;
			Log.d(TAG,Integer.toString(stoneList.size()));
			currentPlayer.setState(1);
		}
		
		for(Sprite i: stoneList){
			i.update(dt);
		}
		
		if (noStonesMove()&&currentPlayer.getState() == 2){
			evaluateStones();
			nextPlayer();
			if(totalStones == 0){
				addPoints();
				if(currentRound == rounds){
					showWinner();
				}
			}
		}
	}
	
	public void draw(Canvas canvas, BoundingBox box) {
		for(Sprite i: stoneList){
			i.draw(canvas);
		}
	}
	
	public boolean noStonesMove(){
		for(Sprite i: stoneList){
			if (i.getSpeed() != nullvector){
				return false;
			}
		}
		return true;
	}
	
	public void nextPlayer(){
		currentPlayer.setState(0);
		currentPlayer = playerTow;
		
	}
	
	public void addPoints(){
		currentRound = currentRound + 1;
	}
	
	public void evaluateStones(){
		
	}
	
	public void showWinner(){
		
	}
	
	public CurlingStone getStone(){
		return this.movingStone;
	}
}
