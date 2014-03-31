package curling;

import java.util.Stack;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import sheep.game.Game;
import sheep.game.GameThread;
import sheep.game.State;
import sheep.input.Keyboard;
import sheep.input.Touch;

/*
 * An extension of sheep's 'game' to make it possible to pause without crash.
 */

public class CurlingGame extends Game {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Stack<State> stateStack = new Stack<State>(); 
	private GameThread thread;
	
	public CurlingGame(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void resumeStates() { 
		for (int i=0; i<stateStack.size(); i++) {
			thread.pushState(stateStack.get(i));
			Log.d(TAG,stateStack.get(i).getClass().toString());
		}
		stateStack.peek().setGame(this);
	}
	
	public void pushState(State state) {
		state.setGame(this);
		this.thread.pushState(state);
		stateStack.add(state);
	}
	
	public State getTopState() {
		return stateStack.peek();
	}
	
	public Stack<State> getStateStack() {
		return stateStack;
	}
		
	public void popState() {
		this.thread.popState(1);
		stateStack.pop();
		Log.d(TAG,"bruker riktig klasse :p");
	}
	
	public void popState(int n) {
		this.thread.popState(n);
		for(int i = 0; i < n; i++){
			stateStack.pop();
		}
	}
	
	public State getPreviousState() {
		return thread.getPreviousState();
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.d(TAG,"lager surface");
		Keyboard.getInstance().addKeyboardListener(thread);
		Touch.getInstance().addTouchListener(thread);
		this.thread.setRunning(true);
		this.thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		this.thread.setRunning(false);
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setThread(GameThread t){
		this.thread = t;
	}
}
