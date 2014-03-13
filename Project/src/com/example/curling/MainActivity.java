package com.example.curling;

import sheep.game.Game;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	
	private Game game;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//fjerner unødvedige bannere
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		Point screenSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(screenSize);
		
		GlobalConstants.context = getApplicationContext();
		GlobalConstants.SCREENHEIGHT = screenSize.y;
		GlobalConstants.SCREENWIDTH = screenSize.x;
		
		game = new Game(this,null);
		game.pushState(new MainMenu());
		game.setKeepScreenOn(true);
		setContentView(game);
	}
	
	public void onPause(){
//		//fjerner unødvedige bannere
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.activity_pause);
//		Point screenSize = new Point();
//		getWindowManager().getDefaultDisplay().getSize(screenSize);
		if (game.getPreviousState().getClass() == GameStateConfig.class){
			game.pushState(new PauseMenu());
			game.setKeepScreenOn(true);
		}
		super.onPause();
	}
	
	public void onResume(){
		super.onResume();
	}
	
	public void onStop(){
		super.onStop();
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
	
	public void onStart(){
		super.onStart();
	}
	
	public void onRestart(){
		super.onRestart();
	}
	
	public void onBackPressed(){
		if (game.getPreviousState().getClass() == sheep.game.State.class){
			super.onBackPressed();
		}
		else {game.popState();}
	}
}
