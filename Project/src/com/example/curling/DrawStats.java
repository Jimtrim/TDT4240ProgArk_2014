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
	private Image targetYellow = new Image(R.drawable.aimyellow);
	private Image brooming = new Image(R.drawable.curlingbroomalt2),brooming2 = new Image(R.drawable.curlingbroom2),brooming3 = new Image(R.drawable.curlingbroom3),
	brooming4 = new Image(R.drawable.curlingbroom4),brooming5 = new Image(R.drawable.curlingbroom5);
	private Image broomingDown = new Image(R.drawable.curlingbroomdown),broomingDown2 = new Image(R.drawable.curlingbroomdown2),broomingDown3 = new Image(R.drawable.curlingbroomdown3),
	broomingDown4 = new Image(R.drawable.curlingbroomdown4),broomingDown5 = new Image(R.drawable.curlingbroomdown5);
	private Image broomingUp = new Image(R.drawable.curlingbroomup),broomingUp2 = new Image(R.drawable.curlingbroomup2),broomingUp3 = new Image(R.drawable.curlingbroomup3),
	broomingUp4 = new Image(R.drawable.curlingbroomup4),broomingUp5 = new Image(R.drawable.curlingbroomup5);
	private Image currentBrooming;

	private ImageButton setTarget,next;
	private GameState game;
	private ArrayList<Image> broomingAnimation = new ArrayList<Image>();
	private ArrayList<Image> broomingAnimationDown = new ArrayList<Image>();
	private ArrayList<Image> broomingAnimationUp = new ArrayList<Image>();
	private ArrayList<Image> currentBroomingAnimation;
	private float time = 0;
	private int imageIndex = 0;
	private Image redWon = new Image(R.drawable.redwon);
	private Image yellowWon = new Image(R.drawable.yellowwon);
	private Image trophy = new Image(R.drawable.trophy_golden);
	private Image redStone = new Image(R.drawable.redcurlingsmall);
	private Image yellowStone = new Image(R.drawable.yellowcurlingsmall);
	
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

        next = new ImageButton(GlobalConstants.SCREENWIDTH*0.4f,GlobalConstants.SCREENHEIGHT*0.75f,new Image(R.drawable.nextidle),new Image(R.drawable.nextpressed));
        next.addWidgetListener(this);

		broomingAnimation.add(brooming);
		broomingAnimation.add(brooming2);
		broomingAnimation.add(brooming3);
		broomingAnimation.add(brooming2);
		broomingAnimation.add(brooming);
		broomingAnimation.add(brooming4);
		broomingAnimation.add(brooming5);
		broomingAnimation.add(brooming4);
		
		broomingAnimationDown.add(broomingDown);
		broomingAnimationDown.add(broomingDown2);
		broomingAnimationDown.add(broomingDown3);
		broomingAnimationDown.add(broomingDown2);
		broomingAnimationDown.add(broomingDown);
		broomingAnimationDown.add(broomingDown4);
		broomingAnimationDown.add(broomingDown5);
		broomingAnimationDown.add(broomingDown4);
		
		broomingAnimationUp.add(broomingUp);
		broomingAnimationUp.add(broomingUp2);
		broomingAnimationUp.add(broomingUp3);
		broomingAnimationUp.add(broomingUp2);
		broomingAnimationUp.add(broomingUp);
		broomingAnimationUp.add(broomingUp4);
		broomingAnimationUp.add(broomingUp5);
		broomingAnimationUp.add(broomingUp4);
		currentBroomingAnimation = broomingAnimation;
		currentBrooming = currentBroomingAnimation.get(imageIndex);
	}
	
	public void update(float dt){
		if(game.getGameLayer().getStone() != null){
			if(game.getGameLayer().getStone().getBrooming()){
				if(currentBroomingAnimation != broomingAnimation){
					currentBroomingAnimation = broomingAnimation;
					imageIndex = 0;
					currentBrooming = currentBroomingAnimation.get(imageIndex);
				}
			}else if(game.getGameLayer().getStone().getBroomingUp()){
				if(currentBroomingAnimation != broomingAnimationUp){
					currentBroomingAnimation = broomingAnimationUp;
					imageIndex = 0;
					currentBrooming = currentBroomingAnimation.get(imageIndex);
				}
			}else if(game.getGameLayer().getStone().getBroomingDown()){
				if(currentBroomingAnimation != broomingAnimationDown){
					currentBroomingAnimation = broomingAnimationDown;
					imageIndex = 0;
					currentBrooming = currentBroomingAnimation.get(imageIndex);
				}
			}
			time = time + dt;
			if (time >= 0.05){
				imageIndex = imageIndex + 1;
				if(imageIndex == currentBroomingAnimation.size()){
					imageIndex = 0;
				}
				currentBrooming = currentBroomingAnimation.get(imageIndex);
				time = 0;
			}
		}
	}
	
	public void draw(Canvas canvas){
		if(game.getGameLayer().getCurrentPlayer().getState() == 0){
			if(game.getGameLayer().getCurrentPlayer().getPlayerIndex() == 0){
                    target.draw(canvas, GlobalConstants.SCREENWIDTH*.5f-target.getHeight()/2,GlobalConstants.SCREENHEIGHT*0.5f-target.getHeight()/2);
			}else{
				targetYellow.draw(canvas, GlobalConstants.SCREENWIDTH*.5f-target.getHeight()/2,GlobalConstants.SCREENHEIGHT*0.5f-target.getHeight()/2);
			}
			setTarget.draw(canvas);
		}else if(game.getGameLayer().getCurrentPlayer().getState() == 2){
			if(game.getGameLayer().getStone().getBrooming()){
				currentBrooming.draw(canvas, GlobalConstants.SCREENWIDTH*0.52f,game.getGameLayer().getStone().getY()-32);
			}else if(game.getGameLayer().getStone().getBroomingUp()){
				currentBrooming.draw(canvas, GlobalConstants.SCREENWIDTH*0.50f,game.getGameLayer().getStone().getY()-70);
			}else if(game.getGameLayer().getStone().getBroomingDown()){
				currentBrooming.draw(canvas, GlobalConstants.SCREENWIDTH*0.50f,game.getGameLayer().getStone().getY()+13);
			}
		}else if(game.getGameLayer().getCurrentPlayer().getState() == 4){
            if(game.getGameLayer().getShowWinner()){
                trophy.draw(canvas, GlobalConstants.SCREENWIDTH*0.2f, GlobalConstants.SCREENHEIGHT*0.2f);
                if(game.getGameLayer().getRedWon()){
                    redWon.draw(canvas, GlobalConstants.SCREENWIDTH*0.35f, GlobalConstants.SCREENHEIGHT*0.4f);
                }
                if(game.getGameLayer().getYellowWon()){
                    yellowWon.draw(canvas, GlobalConstants.SCREENWIDTH*0.35f, GlobalConstants.SCREENHEIGHT*0.4f);

                }
            }
			next.draw(canvas);
    	}
		
        canvas.drawText("Red", GlobalConstants.SCREENWIDTH*0.35f, GlobalConstants.SCREENHEIGHT*0.1f, red);
        canvas.drawText(Integer.toString(game.getGameLayer().getPlayerOne().getPoints()) + "  :  "+ Integer.toString(game.getGameLayer().getPlayerTwo().getPoints()), GlobalConstants.SCREENWIDTH*0.48f, GlobalConstants.SCREENHEIGHT*0.1f, gray);
        canvas.drawText("Yellow", GlobalConstants.SCREENWIDTH*0.6f, GlobalConstants.SCREENHEIGHT*0.1f, yellow);
        for(int i = 0; i < game.getGameLayer().getPlayerOne().getNumberOfStones(); i++){
        	redStone.draw(canvas,GlobalConstants.SCREENWIDTH*0.3f + (redStone.getWidth()*1.2f*i*-1),GlobalConstants.SCREENHEIGHT*0.065f);
        }
        for(int i = 0; i < game.getGameLayer().getPlayerTwo().getNumberOfStones(); i++){
        	yellowStone.draw(canvas,GlobalConstants.SCREENWIDTH*0.725f + (yellowStone.getWidth()*1.2f*i),GlobalConstants.SCREENHEIGHT*0.065f);
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
        if (game.getGameLayer().getCurrentPlayer().getState() == 4){
            if (action.getSource() == next){
                if (game.getGameLayer().getShowWinner()){
                    game.getGame().popState(2);
                }
                else{
                	game.getGameLayer().getCurrentPlayer().setState(0);
                    game.resetCamera();
                }
            }
        }
	}
	
	public ImageButton getTargetListener(){
		return this.setTarget;
	}
    public ImageButton getNextListener(){
        return this.next;
    }
}
