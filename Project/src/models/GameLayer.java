package models;

import java.util.ArrayList;
import java.util.Comparator;

import com.example.curling.R;

import curling.GlobalConstants;

import android.graphics.Canvas;
import android.graphics.Color;
import sheep.game.Layer;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;
import sheep.math.Vector2;

/*
 * 
 * game logic. Rounds, stone and player controller
 */

public class GameLayer extends Layer implements Comparator<CurlingStone>{
	
	private Track track = new Track(new Image(R.drawable.curlingbackground));

	private int rounds,currentRound,totalStones;
	private ArrayList<CurlingStone> stoneList = new ArrayList<CurlingStone>();
    private ArrayList<CurlingStone> removeStone = new ArrayList<CurlingStone>();
	private Player playerOne, playerTwo,currentPlayer;
	private CurlingStone movingStone;
	private Vector2 target;
	private Player winnerOfRound;
	private boolean showWinner = false;
	private boolean yellowWon = false;
	private boolean redWon = false;
	
	
	public GameLayer(int rounds,int stones){
		this.rounds = rounds;
		this.totalStones = stones;
		this.currentRound = 0;
		playerOne = new Player(0,totalStones, "Red");
		playerTwo = new Player(1,totalStones, "Yellow");
		currentPlayer = playerOne;
	}

	public void update(float dt) {
		//prepare curlingstone for throw/slide
		if (currentPlayer.getState() == 1){
			movingStone = new CurlingStone(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.5f,currentPlayer.getPlayerIndex(),target);
			stoneList.add(movingStone);
			currentPlayer.subtractOneStone();
			currentPlayer.setState(2);
		}
		
		
		if(noStonesMove() && currentPlayer.getState() == 2){
			if (movingStone.getMoved())	
				currentPlayer.setState(3);
		}
		
		//check for collision
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
			outOfBounds(i); //check if the stone is outside the bound
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
			if (i.getSpeed().getLength() != 0){
				return false;
			}
		}
		return true;
	}
	
	public void nextPlayer(){
		if(currentPlayer == playerOne) currentPlayer = playerTwo;
		else currentPlayer = playerOne;
	}
	
	//sort the stones closest to the target(goal)
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
	
	//between stone and the target(goal)
	public double getDistanceY(CurlingStone i){
		double distanceY = Math.abs(i.getPosition().getY()-GlobalConstants.SCREENHEIGHT*0.5);
		return distanceY;
	}
	
	//between stone and the target(goal)
	public double getDistanceX(CurlingStone i){
		double distanceX = Math.abs(i.getPosition().getX()-track.getGoalPoint());
		return distanceX;
	}
	
	//compare the distance between to stone for finding the stone closest to the target(goal)
    public int compare(CurlingStone a, CurlingStone b){
    	double distanceA = Math.pow(getDistanceX(a), 2) + Math.pow(getDistanceY(a), 2);
    	double distanceB = Math.pow(getDistanceX(b), 2) + Math.pow(getDistanceY(b), 2);
		
    	int startComparison = compare(distanceA, distanceB);
    	return startComparison;
   	}
    	
    private int compare(double a, double b) {
    	return a < b ? -1
	         : a > b ? 1
	         : 0;
    }
	
    //give points to the player
	public void addPoints(){
		currentRound = currentRound + 1;
		winnerOfRound = null;
		ArrayList<CurlingStone> sortedList = sortStoneList(stoneList);
        startPlayer(sortedList);
        if (sortedList.size()!=0){
            for(CurlingStone i: sortedList){
                if(sortedList.get(0).getStoneIndex() != i.getStoneIndex()){
                    break;
                }
                else {
                    currentPlayer.setPoints(1);
                }
            }
       }
	}

    public void startPlayer(ArrayList<CurlingStone> stones){
        if(stones.size()==0){
            nextPlayer();
        }
        else{
            if (stones.get(0).getStoneIndex()==0){
                currentPlayer.setState(0);
                currentPlayer = playerOne;
            }
            else if (stones.get(0).getStoneIndex()==1){
                currentPlayer.setState(0);
                currentPlayer = playerTwo;
            }
        }
    }
    
    //evaluate which stone is inside the target(goal)
	public void evaluateStones(){
		double radius = Math.abs(track.getGoalPoint()-track.getOuterCircle());
		double stoneDistance;
		for(CurlingStone i: stoneList){
			stoneDistance = Math.sqrt(Math.pow(getDistanceX(i), 2) + Math.pow(getDistanceY(i), 2));
			if (stoneDistance > radius){
				removeStone.add(i);
			}
		}
		stoneList.removeAll(removeStone);
		removeStone.clear();
	}

	public void showWinner(){
		if (playerOne.getPoints() > playerTwo.getPoints()) {
			showWinner = true;
			redWon = true;
		}
		else if (playerTwo.getPoints() > playerOne.getPoints()) {
			showWinner = true;
			yellowWon = true;
		}
	}
	
	public void endTurn(){
		if (noStonesMove() && currentPlayer.getState() == 3){
			if((playerOne.getNumberOfStones()+ playerTwo.getNumberOfStones()) == 0){
                evaluateStones();
				addPoints();
				newRound();
				if(currentRound >= rounds && playerOne.getPoints() != playerTwo.getPoints()){
					showWinner();
				}
			}
            else{
                currentPlayer.setState(0);
                nextPlayer();
            }
		}
	}
	
	public void newRound(){
		playerOne.setStones(totalStones);
		playerTwo.setStones(totalStones);
		stoneList.clear();
        getCurrentPlayer().setState(4);
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
    
    public Player getWinner() {
    	return winnerOfRound;
    }
    
    public CurlingStone getStone(){
		return this.movingStone;
	}
    
    public boolean getShowWinner(){
    	return this.showWinner;
    }
    
    public boolean getRedWon(){
    	return this.redWon;
    }
    
    public boolean getYellowWon(){
    	return this.yellowWon;
    }
      
    public Player getCurrentPlayer(){
    	return this.currentPlayer;
    }

    public Player getPlayerOne() {
    	return playerOne;
    }
    
    public Player getPlayerTwo() {
    	return playerTwo;
    }
    
    public Track getTrack(){
    	return this.track;
    }
    
    public void setTarget(Vector2 p){
    	this.target = p;
    }

    public int getCurrentRound() { return currentRound; }

}
