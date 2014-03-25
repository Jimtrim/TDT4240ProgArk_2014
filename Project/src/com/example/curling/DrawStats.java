package com.example.curling;

import java.util.ArrayList;

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
	private Image brooming = new Image(R.drawable.curlingbroomalt2),brooming2 = new Image(R.drawable.curlingbroom2),brooming3 = new Image(R.drawable.curlingbroom3),
	brooming4 = new Image(R.drawable.curlingbroom4),brooming5 = new Image(R.drawable.curlingbroom5);
	private Image currentBrooming;
	private ImageButton setTarget;
	private GameState game;
	private ArrayList<Image> broomingAnimation = new ArrayList<Image>();
	private float time = 0;
	private int imageIndex = 0;
	private Image redWon = new Image(R.drawable.redwon);
	private Image yellowWon = new Image(R.drawable.yellowwon);
	private Image trophy = new Image(R.drawable.trophy_golden);
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
		
		broomingAnimation.add(brooming);
		broomingAnimation.add(brooming2);
		broomingAnimation.add(brooming3);
		broomingAnimation.add(brooming2);
		broomingAnimation.add(brooming);
		broomingAnimation.add(brooming4);
		broomingAnimation.add(brooming5);
		broomingAnimation.add(brooming4);
		currentBrooming = broomingAnimation.get(imageIndex);
	}
	
	public void update(float dt){
		time = time + dt;
		if (time >= 0.05){
			imageIndex = imageIndex + 1;
			if(imageIndex == broomingAnimation.size()){
				imageIndex = 0;
			}
			currentBrooming = broomingAnimation.get(imageIndex);
			time = 0;
		}
	}
	
	public void draw(Canvas canvas){
		if(game.getGameLayer().getCurrentPlayer().getState() == 0){
			target.draw(canvas, GlobalConstants.SCREENWIDTH*.5f-target.getHeight()/2,GlobalConstants.SCREENHEIGHT*0.5f-target.getHeight()/2);
			setTarget.draw(canvas);
		}
		if(game.getGameLayer().getCurrentPlayer().getState() == 2 && game.getGameLayer().getStone().getBrooming()){
			currentBrooming.draw(canvas, GlobalConstants.SCREENWIDTH*0.55f,game.getGameLayer().getStone().getY()-32);
		}
        canvas.drawText("Red", GlobalConstants.SCREENWIDTH*0.35f, GlobalConstants.SCREENHEIGHT*0.1f, red);
        canvas.drawText(Integer.toString(game.getGameLayer().getPlayerOne().getPoints()) + "  :  "+ Integer.toString(game.getGameLayer().getPlayerTwo().getPoints()), GlobalConstants.SCREENWIDTH*0.48f, GlobalConstants.SCREENHEIGHT*0.1f, gray);
        canvas.drawText("Yellow", GlobalConstants.SCREENWIDTH*0.6f, GlobalConstants.SCREENHEIGHT*0.1f, yellow);
        
        if(game.getGameLayer().showWinner == true){
        	trophy.draw(canvas, GlobalConstants.SCREENWIDTH*0.2f, GlobalConstants.SCREENHEIGHT*0.2f);
        	if(game.getGameLayer().redWon == true){
        		redWon.draw(canvas, GlobalConstants.SCREENWIDTH*0.35f, GlobalConstants.SCREENHEIGHT*0.4f);
        	}
        	if(game.getGameLayer().yellowWon == true){
        		yellowWon.draw(canvas, GlobalConstants.SCREENWIDTH*0.35f, GlobalConstants.SCREENHEIGHT*0.4f);
        	}
        }
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
