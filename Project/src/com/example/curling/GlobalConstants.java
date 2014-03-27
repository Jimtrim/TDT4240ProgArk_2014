package com.example.curling;

import sheep.graphics.Font;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

/*
 * globale variables and constants
 */

public class GlobalConstants {
	
	//sets variables in main on create. Therefore not final
	public static int SCREENWIDTH = 0;
	public static int SCREENHEIGHT = 0;
	
	public static Context context = null;

	public static final Paint[] menuFont = {new Font(0, 0, 0, 40, Typeface.SANS_SERIF, Typeface.BOLD),
											new Font(255, 0, 0, 40, Typeface.SANS_SERIF, Typeface.BOLD)};

}
