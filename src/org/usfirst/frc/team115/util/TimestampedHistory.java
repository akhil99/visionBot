package org.usfirst.frc.team115.util;

public class TimestampedHistory {
	
	private TimestampedInfo[] history;
	private int historySize;
	private int currentIndex;
	private int validSamp;
	
	public TimestampedHistory(int numSamples) {
		historySize = numSamples;
		history = new TimestampedInfo[historySize];
		for (int i = 0; i < history.length; i++) {
			history[i] = new TimestampedInfo();
		}
		currentIndex = 0;
		validSamp = 0;
	}
	
	public void add (int timestamp, double angle) {
		history[currentIndex].set(timestamp, angle);
		if ( currentIndex < (historySize - 1)) {
			currentIndex++;
    	} else {
    		currentIndex = 0;
    	}
		if (validSamp < historySize) {
			validSamp++;
		}
	}
	
	public TimestampedInfo getTimestampedAngle(int requestedTimestamp) {
		TimestampedInfo match = null;
		int timestamp = 0;
		int closeTimestamp = history[0].getTimestamp();
		int closeIndex = 0;
		for ( int i = 0; i < validSamp; i++ ) { // for samples
    		timestamp = history[i].getTimestamp();
    		if ( timestamp == requestedTimestamp ) {
    			match = history[i];
    			break;
    		} else {
    			if (Math.abs(timestamp - requestedTimestamp) < Math.abs(closeTimestamp - requestedTimestamp)) {
    				closeTimestamp = timestamp;
    				closeIndex = i;
    			}
    		}
    	}
		if (match == null) {
			match = history[closeIndex];
		}
		return match;
	}
	
	public double getCalculatedAngle(int timestamp) {
		TimestampedInfo ta = getTimestampedAngle(timestamp);
		double angle = ta.getAngle(); // do some calculations
		return angle;
	}
	
	public double getTimestampedYaw(int timestamp) {
		TimestampedInfo ta = getTimestampedAngle(timestamp);
		return ta.getAngle();
	}

}
