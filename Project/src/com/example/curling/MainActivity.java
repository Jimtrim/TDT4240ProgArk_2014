package com.example.curling;

import sheep.game.Game;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//fjerner unødvedige bannere
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		// da jeg prøvde å bruke den nye funksjonen fikk jeg feilmedling med sheep...
		GlobalConstants.SCREENHEIGHT = getWindowManager().getDefaultDisplay().getHeight();
		GlobalConstants.SCREENWIDTH = getWindowManager().getDefaultDisplay().getWidth();
		
		Game game = new Game(this,null);
		game.pushState(new MainMenu());
		game.setKeepScreenOn(true);
		setContentView(game);
	}
}
