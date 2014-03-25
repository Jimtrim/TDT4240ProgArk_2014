package com.example.curling;

import java.util.ArrayList;
import java.util.Comparator;

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

public class GameLayer extends Layer implements Comparator<CurlingStone>{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private Track track = new Track(new Image(R.drawable.curlingbackground));

	private int rounds,currentRound,totalStones,stones;
	private ArrayList<CurlingStone> stoneList = new ArrayList<CurlingStone>();
    private ArrayList<CurlingStone> removeStone = new ArrayList<CurlingStone>();
	private Player playerOne,playerTwo,currentPlayer;
	private Vector2 nullvector = new Vector2(0.0f, 0.0f); 
	private CurlingStone movingStone;
	private int playerOnePoints,playerTwoPoints;
	private Vector2 target;
	private Player winnerOfRound;
	
	
	public GameLayer(int rounds,int stones){
		this.rounds = rounds;
		this.totalStones = stones;
		this.stones = totalStones;
		this.currentRound = 0;
		this.totalStones = stones;
		playerOnePoints = 0;
		playerTwoPoints = 0;
		playerOne = new Player(0);
		playerTwo = new Player(1);
		currentPlayer = playerOne;
	}

	public void update(float dt) {
		//sjekker om det er starten til spilleren
		if (currentPlayer.getState() == 1){
			movingStone = new CurlingStone(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.5f,currentPlayer.getPlayerIndex(),target);
			stoneList.add(movingStone);
			stones = stones - 1;
			Log.d(TAG,Integer.toString(stoneList.size()));
			currentPlayer.setState(2);
		}
		if(noStonesMove() && currentPlayer.getState() > 1){
			if (movingStone.getMoved())	currentPlayer.setState(3);
		}
		
		for(CurlingStone i: stoneList){
			i.update(dt);
			for(CurlingStone cld: stoneList){
				if(i != cld){
					if(i.collides(cld)){
						if(cld.getCollidedStone() == null || cld != i.getCollidedStone()){
							i.collision(cld);
						}
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
		canvas.drawColor(Color.BLACK);
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
	
	public ArrayList<CurlingStone> sortStoneList(ArrayList<CurlingStone> stoneList){
		int comparison;
		CurlingStone a;
		CurlingStone b;
		int conflict = 0;
		while(conflict < stoneList.size()-2){
			for (int i = 0; i < stoneList.size()-1; i++){
				a = stoneList.get(i);
				b = stoneList.get(i+1);
				comparison = compare(a, b);
				if (comparison == -1){
					conflict += 1;
				}
				else if (comparison == 1){
					stoneList.set(i+1, a) ;
					stoneList.set(i, b);
					conflict = 0;
				}
				else {
					continue;
				}
			}
		}
		return stoneList;	
	}
	
    public int compare(CurlingStone a, CurlingStone b){
    	double targetY = GlobalConstants.SCREENHEIGHT*0.5;
		float targetX = track.getGoalPoint();
		double distanceA = Math.pow(Math.abs(a.getPosition().getX()-targetX), 2) + Math.pow(Math.abs(a.getPosition().getY()-targetY), 2);
		double distanceB = Math.pow(Math.abs(b.getPosition().getX()-targetX), 2) + Math.pow(Math.abs(b.getPosition().getY()-targetY), 2);
    	int startComparison = compare(distanceA, distanceB);
    	return startComparison;
   	}
    	
    private int compare(double a, double b) {
    	return a < b ? -1
	         : a > b ? 1
	         : 0;
    }
	
	public void addPoints(){
		currentRound = currentRound + 1;
		int points = 0;
		winnerOfRound = null;
		ArrayList<CurlingStone> sortedList = sortStoneList(stoneList);

        if (sortedList.size()!=0){
            winnerOfRound = win(sortedList);
            for(CurlingStone i: sortedList){
                if(sortedList.get(0).getStoneIndex() != i.getStoneIndex()){
                    break;
                }
                else {
                    winnerOfRound.setPoints(1);
                }
            }
            currentPlayer = getWinner();        }
	}

    public Player win(ArrayList<CurlingStone> stones){
        if (stones.get(0).getStoneIndex()==0){
            return playerOne;
        }
        return playerTwo;
    }
    public Player getWinner() {return winnerOfRound; }
	
	public void evaluateStones(){
		
	}

	public void showWinner(){
		if (playerOnePoints > playerTwoPoints) {
			//congratz.setVisible(true)
			//playerone.setVisible(true)
		}
		else if (playerTwoPoints > playerOnePoints) {
			//congratz.setVisible(true)
			//playertwo.setVisible(true)
		}
		else {
			//tie.setVisible(true)
		}
	}
	
	public void endTurn(){
		if (noStonesMove() && currentPlayer.getState() == 3){
			evaluateStones();
			nextPlayer();
			if(stones == 0){
				addPoints();

				newRound();
				if(currentRound == rounds){
					showWinner();
				}
			}
		}
	}
	
	public void newRound(){
		stones = totalStones;
		stoneList.clear();
	}
	
	public CurlingStone getStone(){
		return this.movingStone;
	}

    public void outOfBounds(Sprite stone){
        float posX = stone.getPosition().getX();
        float posY = stone.getPosition().getY();
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

    public Player getPlayerOne() { return playerOne; }
    public Player getPlayerTwo() { return playerTwo; }
    
    public Track getTrack(){
    	return this.track;
    }
    
    public void setTarget(Vector2 p){
    	this.target = p;
    }

}
