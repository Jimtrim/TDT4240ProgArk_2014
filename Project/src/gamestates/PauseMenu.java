package gamestates;

import curling.CurlingGame;
import curling.GlobalConstants;

import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import android.graphics.Canvas;
import android.graphics.Color;

/*
 * Pause menu - buttons, text
 */
public class PauseMenu extends State implements WidgetListener {
	
	private TextButton resumeButton, exitButton;
	private int c = Color.argb(60, 0, 0, 0);
	
	public PauseMenu() {
		resumeButton = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.4f,"Resume Game",GlobalConstants.menuFont);
		exitButton = new TextButton(GlobalConstants.SCREENWIDTH*0.3f,GlobalConstants.SCREENHEIGHT*0.6f,"Exit Game",GlobalConstants.menuFont);
		
		addTouchListener(resumeButton);
		addTouchListener(exitButton);
		resumeButton.addWidgetListener(this);
		exitButton.addWidgetListener(this);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		getGame().getPreviousState().draw(canvas);
		try{
			canvas.drawColor(c);
			resumeButton.draw(canvas);
			exitButton.draw(canvas);
		}catch (Exception e){};
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == resumeButton && ((CurlingGame)getGame()).getTopState().getClass() == this.getClass()){
			getGame().popState();
		}
		if(action.getSource() == exitButton && ((CurlingGame)getGame()).getTopState().getClass() == this.getClass()) {
			getGame().popState();
			getGame().popState();
			getGame().popState();
		}
		
	}
}
