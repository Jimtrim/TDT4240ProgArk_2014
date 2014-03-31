package curling;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import sheep.graphics.Image;
import sheep.gui.Widget;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.math.BoundingBox;

/*
 * An extension for buttons used in the game
 */

public class ImageButton extends Widget{

	
	private Image idle,pressedDown,currentViwe;
	private float x,y;
	private BoundingBox box;
	private LinkedList<WidgetListener> listeners;
	
	public ImageButton(float x, float y, Image idle, Image pressedDown){
		this.listeners = new LinkedList<WidgetListener>();
		this.idle = idle;
		this.pressedDown = pressedDown;
		this.currentViwe = idle;
		this.x = x;
		this.y = y;
		updateBox();
	}
	
	public void addWidgetListener(WidgetListener listener) {
		listeners.add(listener);
	}
	
	public void removeWidgetListener(WidgetListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void draw(Canvas canvas) {
		currentViwe.draw(canvas, x,y);
	}
	
	private void updateBox() {
		Rect bounds = new Rect(0,0,(int)currentViwe.getWidth(),(int)currentViwe.getHeight());
		bounds.offset((int)x, (int)y);
		this.box = new BoundingBox(bounds);
	}
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(box.contains(event.getX(), event.getY())) {
			this.currentViwe = pressedDown;
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		if(box.contains(event.getX(), event.getY())) {
			this.currentViwe = pressedDown;
			return true;
		}
		this.currentViwe = idle;
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(box.contains(event.getX(), event.getY())) {
			this.currentViwe = idle;	
			for(WidgetListener l : this.listeners){
				l.actionPerformed(new WidgetAction(this));
			}
			return true;
		}
		return false;
	}

}
