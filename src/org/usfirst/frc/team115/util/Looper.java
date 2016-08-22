package org.usfirst.frc.team115.util;

import edu.wpi.first.wpilibj.Notifier;

public class Looper {
	Loopable loopable;
	String name;
	double period = 1.0 / 100.0;
	Notifier updater;
	
	public Looper(String name, Loopable loopable, double period) {
		this.name = name;
		this.loopable = loopable;
		this.period = period;
		this.updater = new Notifier(loopable);
	}
	
	public void start() {
		updater.startPeriodic(period);
	}
	
	public void stop() {
		updater.stop();
		loopable.stopLooping();
	}
	
}