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

	private int rounds,currentRound,totalStones;
	private ArrayList<CurlingStone> stoneList = new ArrayList<CurlingStone>();
    private ArrayList<CurlingStone> removeStone = new ArrayList<CurlingStone>();
	private Player playerOne,playerTwo,currentPlayer;
	private Vector2 nullvector = new Vector2(0.0f, 0.0f); 
	private CurlingStone movingStone;
	private int playerOnePoints,playerTwoPoints;
	private Vector2 target;
	private CurlingStone winningStone;
	private Player winnerOfRound;
	
	
	public GameLayer(int rounds){
		this.rounds = rounds;
		this.currentRound = 0;
		this.totalStones = 16;
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
			totalStones = totalStones - 1;
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
							
							double d = 48;
							
							double dx= Math.abs(cld.getPosition().getX()-i.getPosition().getX());
							double dy= (cld.getPosition().getY()-i.getPosition().getY());
							double length = Math.sqrt(dx*dx+dy*dy);
							
							Log.d(TAG,"Length : " + Double.toString(length));
							
							if (d >= length){

								i.setCollidedStone(cld);
								cld.setCollidedStone(i);
								
								double ax=dx/length, ay=dy/length;
								
								//component of velocity in the direction of (dx,dy). Projection of the velocities in these axes
								double va1=(i.getSpeedX()*ax+i.getSpeedY()*ay); 
								double vb1=(-i.getSpeedX()*ay+i.getSpeedY()*ax); 
								
								double va2=(cld.getSpeedX()*ax+cld.getSpeedY()*ay);
								double vb2=(-cld.getSpeedX()*ay+cld.getSpeedY()*ax);
								
								double ed = 1; //elastic collision
								double mass = 20;
								
								// New velocities in these axes (after collision)
								double vaP1=va1 + (1+ed)*(va2-va1)/(1+mass/mass);
								double vaP2=va2 + (1+ed)*(va1-va2)/(1+mass/mass);
								
								
								
								double vx1=vaP1*ax-vb1*ay,  vy1=vaP1*ay+vb1*ax;// new vx,vy for ball 1 after collision
								double vx2=vaP2*ax-vb2*ay,  vy2=vaP2*ay+vb2*ax;// new vx,vy for ball 2 after collision
								
								i.setSpeedX((float)vx1);
								i.setSpeedY((float)vy1);
								
								cld.setSpeedX((float)vx2);
								cld.setSpeedY((float)vy2);

								//cld.setSpeedX(((CurlingStone)i).getSpeedX());
								//i.setSpeedX(0);
								//i.setSpeed(0, 0);
							}

						}

						

					}
				}
				
			}
            //outOfBounds(i);
		}
        //stoneList.removeAll(removeStone);
        //removeStone.clear();
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
		ArrayList<CurlingStone> sortedList = new ArrayList<CurlingStone>();
		int comparison;
		CurlingStone a;
		CurlingStone b;
		int conflict = 0;
		while(conflict < stoneList.size()-1){
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
		return sortedList;	
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
		for(CurlingStone i: sortedList){
			if(sortedList.get(0).getStoneIndex() != i.getStoneIndex()){
				break;
			}
			else {
				points += 1;
			}
		}
		
		if (sortedList.get(0).getStoneIndex() == 0){
			playerOnePoints += points;
		}
		else if (sortedList.get(0).getStoneIndex() == 1){
			playerTwoPoints += points;
		}
		
	}
	
	public int getPLayerOnePoints(){
		return this.playerOnePoints;
	}
	
	public int getPLayerTwoPoints(){
		return this.playerTwoPoints;
	}
	
	
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
		totalStones = 16;
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
    
    public void setTarget(Vector2 p){
    	this.target = p;
    }

}
