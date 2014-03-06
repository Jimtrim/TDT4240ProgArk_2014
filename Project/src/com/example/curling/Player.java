package com.example.curling;

/*
 * kjapp kode for � f� til rundene i curling
 * state 0: steinen har ikke blitt sendt av g�rde enn�
 * state 1: steinen har blitt sendt
 * state 2: steinen har stoppet
 */

public class Player {
	
	private int state;
	
	public Player(){
		state = 0;
	}
	
	public int getState(){
		return this.state;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
}
