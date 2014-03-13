package com.example.curling;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import sheep.game.Game;
import sheep.game.GameThread;
import sheep.game.State;
import sheep.input.Keyboard;
import sheep.input.Touch;

public class CurlingGame extends Game {

	private static final String TAG = MainActivity.class.getSimpleName();
	private ArrayList<State> stateStack = new ArrayList<State>(); 
	GameThread thread;
	
	public CurlingGame(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void resumeStates() { 
		for (int i=0; i<stateStack.size(); i++) {
			stateStack.get(i).setGame(this);
			thread.pushState(stateStack.get(i));
		}
	}
	
	public void pushState(State state) {
		state.setGame(this);
		thread.pushState(state);
		stateStack.add(state);
	}
	
	public State getTopState() {
		return stateStack.get(stateStack.size()-1);
	}
	
	public ArrayList<State> getStateStack() {
		return stateStack;
	}
		
	public void popState() {
		thread.popState(1);
		stateStack.remove(stateStack.size()-1); 
	}
	
	public void popState(int n) {
		thread.popState(n);
	}
	
	public State getPreviousState() {
		return thread.getPreviousState();
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.d(TAG,"lager surface");
		Keyboard.getInstance().addKeyboardListener(thread);
		Touch.getInstance().addTouchListener(thread);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		thread.setRunning(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setThread(GameThread t){
		this.thread = t;
	}
}
