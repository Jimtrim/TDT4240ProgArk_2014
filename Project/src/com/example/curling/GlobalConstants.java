package com.example.curling;

import sheep.graphics.Font;
import sheep.graphics.Image;
import android.graphics.Paint;
import android.graphics.Typeface;

/*
 * globale vareiable og konstanter
 */

public class GlobalConstants {
	
	
	//setter variablene i main on create det er derfor de ikke er final
	public static int SCREENWIDTH = 0;
	public static int SCREENHEIGHT = 0;
	
	public static final Paint[] menuFont = {new Font(0, 0, 0, 40, Typeface.SANS_SERIF, Typeface.BOLD),
											new Font(255, 0, 0, 40, Typeface.SANS_SERIF, Typeface.BOLD)};

}
