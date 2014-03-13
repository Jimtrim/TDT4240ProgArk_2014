package com.example.curling;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import sheep.game.Game;
import sheep.game.State;

public class CurlingGame extends Game {

	private ArrayList<State> stateStack = new ArrayList<State>(); 
	
	public CurlingGame(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public void pushState(State state) {
		super.pushState(state);
		stateStack.add(state);
	}
	
	public void popState() {
		super.popState();
		stateStack.remove(stateStack.size()-1); 
	}
	
	public ArrayList<State> getTopState() {
		return stateStack;
	}
	
}
