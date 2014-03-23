package com.example.curling;

import sheep.graphics.Image;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.math.Vector2;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DrawStats implements WidgetListener{
	
	private Paint red,yellow,gray;
	
	private Image target = new Image(R.drawable.aim);
	
	private ImageButton setTarget;
	
	private GameState game;
	
	public DrawStats(GameState game){
		this.game = game;
		
		red = new Paint();
        red.setColor(Color.RED);
        red.setTextSize(30);
        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setTextSize(30);
        gray = new Paint();
        gray.setColor(Color.GRAY);
        gray.setTextSize(20);
        
        setTarget = new ImageButton(GlobalConstants.SCREENWIDTH*0.35f,GlobalConstants.SCREENHEIGHT*0.875f,new Image(R.drawable.setaimidle),new Image(R.drawable.setaimdown));
		setTarget.addWidgetListener(this);
	}
	
	public void draw(Canvas canvas){
		if(game.getGameLayer().getCurrentPlayer().getState() == 0){
			target.draw(canvas, GlobalConstants.SCREENWIDTH*.5f-target.getHeight()/2,GlobalConstants.SCREENHEIGHT*0.5f-target.getHeight()/2);
			setTarget.draw(canvas);
		}
        canvas.drawText("Red", GlobalConstants.SCREENWIDTH*0.35f, GlobalConstants.SCREENHEIGHT*0.1f, red);
        canvas.drawText(Integer.toString(game.getGameLayer().getPLayerOnePoints()) + "  :  "+ Integer.toString(game.getGameLayer().getPLayerTwoPoints()), GlobalConstants.SCREENWIDTH*0.48f, GlobalConstants.SCREENHEIGHT*0.1f, gray);
        canvas.drawText("Yellow", GlobalConstants.SCREENWIDTH*0.6f, GlobalConstants.SCREENHEIGHT*0.1f, yellow);
	}

	public void actionPerformed(WidgetAction action) {
		if (game.getGameLayer().getCurrentPlayer().getState() == 0){
			if (action.getSource() == setTarget){
				game.getGameLayer().setTarget(new Vector2(game.getCamerax()+GlobalConstants.SCREENWIDTH*0.5f,game.getCameray()+GlobalConstants.SCREENHEIGHT*0.5f));
				game.getGameLayer().getCurrentPlayer().setState(1);
				game.resetCamera();
			}
		}
	}
	
	public ImageButton getTouchListener(){
		return this.setTarget;
	}
}
