package com.example.curling;

import sheep.game.Game;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	
	private Game game;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//fjerner unødvedige bannere
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		Point screenSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(screenSize);
		// da jeg prøvde å bruke den nye funksjonen fikk jeg feilmedling med sheep... tror ikke sheep støtter en særlig høy api
		GlobalConstants.SCREENHEIGHT = screenSize.y;
		GlobalConstants.SCREENWIDTH = screenSize.x;
		
		game = new Game(this,null);
		game.pushState(new MainMenu());
		game.setKeepScreenOn(true);
		setContentView(game);
	}
	
	public void onPause(){
		super.onPause();
	}
	
	public void onResume(){
		super.onResume();
	}
	
	public void onStop(){
		super.onStop();
	}
	
	public void onBackPressed(){
		game.popState();
	}
}
