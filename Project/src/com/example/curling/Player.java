package com.example.curling;

/*
 * kjapp kode for � f� til rundene i curling
 * state 0: steinen har ikke blitt snedt av g�rde enn�
 * state 1: steinen har blitt sendt
 * state 2: steinen har stoppet
 */

public class Player {
	
	private int state;
	private int playerIndex;

	public Player(int index){
		state = 0;
        playerIndex = index;
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
    public void setPlayerIndex(int playerIndex){ this.playerIndex=playerIndex;}
	
	

}
