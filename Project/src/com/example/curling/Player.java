package com.example.curling;

/*
 * state information
 * 
 * state 0: set target for stone
 * state 1: throw/slide stone
 * state 2: stone movement along the track
 * state 3: stone have stopped
 * state 4: start new round after all the stone have been used
 * 
 * player information
 * 
 * points given and stone information to the player
 * 
 */

public class Player {
	
	private int state;
	private int playerIndex;
    private int points;
    private int stones;
    private String name;

	public Player(int index,int stones, String name){
		this.state = 0;
        this.playerIndex = index;
        this.points = 0;
        this.stones = stones;
        this.name = name;

	}
	
	public int getState(){
		return this.state;
	}
	
	public void setState(int state){
		this.state = state;
	}

    public int getPlayerIndex(){
        return this.playerIndex;
    }
    
    public void setPlayerIndex(int playerIndex){
    	this.playerIndex=playerIndex;
    }

    public int getPoints() {
    	return this.points;
    }

    public void setPoints (int points){
    	this.points += points;
    }
    
    public int getNumberOfStones(){
    	return this.stones;
    }
    
    public void subtractOneStone(){
    	this.stones = this.stones - 1;
    }
    public String getName (){ return this.name; }
    
    public void setStones(int stones){
    	this.stones = stones;
    }
}
