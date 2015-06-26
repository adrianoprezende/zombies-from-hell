package com.powerup.zombie.core;

import android.graphics.Canvas;

public interface InteractiveObjectInterface {
	
	public void draw(Canvas canvas);
	
	public void handleActionDown(int eventX, int eventY);
	
	public void animate();

}
