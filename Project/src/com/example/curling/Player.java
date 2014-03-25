package com.example.curling;

import android.util.Log;

/*
 * kjapp kode for aa faa til rundene i curling
 * state 0: set target for stone
 * state 1: steinen har ikke blitt sendt av goerde ennaa
 * state 2: steinen har blitt sendt
 * state 3: steinen har stoppet
 */

public class Player {
	
	private int state;
	private int playerIndex;
    private int points;

	public Player(int index){
		state = 0;
        playerIndex = index;
        points = 0;
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

    public int getPoints() { return this.points; }

    public void setPoints (int points){ this.points += points;}
	
}
