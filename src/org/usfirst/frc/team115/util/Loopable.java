package org.usfirst.frc.team115.util;

public abstract class Loopable implements Runnable{
	
	public abstract void update();
	
	public void run() {
		update();
	}
	
	public void stopLooping() {}

}