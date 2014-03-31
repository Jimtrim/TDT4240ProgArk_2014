package curling;

import gamestates.GameState;
import gamestates.MainMenu;
import gamestates.PauseMenu;

import com.example.curling.R;

import sheep.game.GameThread;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private CurlingGame game;
	@Override
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
		
		game = new CurlingGame(this,null);
		game.setKeepScreenOn(true);
		setContentView(game);
	}
	
	public void onPause(){
		if(game.getTopState().getClass() == GameState.class) {
			Log.d(TAG,"legger til pause menu");
			game.pushState(new PauseMenu());
			Log.d(TAG,Integer.toString(game.getStateStack().size()));
		}
		super.onPause();
	}
	
	public void onResume(){
		super.onResume();
		Log.d(TAG,"pusher main menu...");
		game.setThread(new GameThread(game));
		if(game.getStateStack().size() >2){
			Log.d(TAG,Integer.toString(game.getStateStack().size()));
			game.resumeStates();
		}else{
			game.getStateStack().clear();
			game.pushState(new MainMenu());
		}
	}
	
	public void onBackPressed(){
		if (game.getTopState().getClass() == MainMenu.class){
			super.onBackPressed();
		}else{
			game.popState();
		}
	}
}
