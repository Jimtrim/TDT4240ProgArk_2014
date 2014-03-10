package com.example.curling;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
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
	
	private Track track = new Track(new Image(R.drawable.curlingbackground));

	private int rounds,currentRound,totalStones;
	private ArrayList<CurlingStone> stoneList = new ArrayList<CurlingStone>();
    private ArrayList<CurlingStone> removeStone = new ArrayList<CurlingStone>();
	private Player playerOne,playerTwo,currentPlayer;
	private Vector2 nullvector = new Vector2(0.0f, 0.0f); 
	private CurlingStone movingStone;
	
	
	public GameLayer(int rounds){
		this.rounds = rounds;
		this.currentRound = 0;
		this.totalStones = 16;
		playerOne = new Player(0);
		playerTwo = new Player(1);
		currentPlayer = playerOne;
	}

	public void update(float dt) {
		//sjekker om det er starten til spilleren
		if (currentPlayer.getState() == 1){
			movingStone = new CurlingStone(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.5f,currentPlayer.getPlayerIndex());
			stoneList.add(movingStone);
			totalStones = totalStones - 1;
			Log.d(TAG,Integer.toString(stoneList.size()));
			currentPlayer.setState(2);
		}
		
		if(noStonesMove() && currentPlayer.getState() > 1){
			if (movingStone.getMoved())	currentPlayer.setState(3);
		}
		
		for(Sprite i: stoneList){
			i.update(dt);
			
			for(Sprite cld: stoneList){
				if(i != cld){
					if(i.collides(cld)){
						cld.setSpeed(((CurlingStone)i).getSpeedX(), 0);
						i.setSpeed(0,0);
					}
				}
				
			}
            outOfBounds(i);
		}
        stoneList.removeAll(removeStone);
        removeStone.clear();
		endTurn();
	}
	
	public void draw(Canvas canvas, BoundingBox box) {
		canvas.drawColor(Color.WHITE);
		track.draw(canvas);
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
		currentPlayer.setState(0);
		if(currentPlayer == playerOne) currentPlayer = playerTwo;
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
		if (currentPlayer.getState() == 3){
			evaluateStones();
			nextPlayer();
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

    public void outOfBounds(Sprite stone){
        Float posX = stone.getPosition().getX();
        Float posY = stone.getPosition().getY();
        if (posX > track.getLenght() || (posX < track.getHogLine() && currentPlayer.getState()==3)){
            removeStone.add((CurlingStone) stone);
        }
        else if (posY > track.getHeight() || posY < 0){
            removeStone.add((CurlingStone) stone);
        }
    }
      
    public Player getCurrentPlayer(){
    	return this.currentPlayer;
    }
    
    public Track getTrack(){
    	return this.track;
    }
}
