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
	
	public void add (long timestamp, double angle) {
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
	
	public TimestampedInfo getTimestampedAngle(long requestedTimestamp) {
    	TimestampedInfo match = null;
		
		int initial_index = currentIndex;
    	long lowest_timestamp = Long.MAX_VALUE;
    	int lowest_timestamp_index = -1;
    	long highest_timestamp = Long.MIN_VALUE;
    	int highest_timestamp_index = -1;
		
		synchronized(this) {
			for ( int i = 0; i < validSamp; i++ ) {
	    		long entry_timestamp = history[initial_index].timestamp;
	    		if ( entry_timestamp < lowest_timestamp ) {
	    			lowest_timestamp = entry_timestamp;
	    			lowest_timestamp_index = i;
	    		}
	    		if ( entry_timestamp > highest_timestamp ) {
	    			highest_timestamp = entry_timestamp;
	    			highest_timestamp_index = i;
	    		}
	    		if ( entry_timestamp == requestedTimestamp) {
	    			match = history[initial_index];
	    			break;
	    		}
	    		initial_index--;
	    		if ( initial_index < 0 ) {
	    			initial_index = historySize - 1;
	    		}
	    	}
    	}
		
		if (match == null) {
			double highest_angle = history[highest_timestamp_index].angle;
			double lowest_angle = history[lowest_timestamp_index].angle;
			
			if(requestedTimestamp - lowest_timestamp < highest_timestamp - requestedTimestamp) {
				match = new TimestampedInfo(requestedTimestamp, lowest_angle); 
			}else{
				match = new TimestampedInfo(requestedTimestamp, highest_angle);
			}
		}
		return match;
	}
	
	public double getCalculatedAngle(int timestamp) {
		TimestampedInfo ta = getTimestampedAngle(timestamp);
		double angle = ta.getAngle(); // do some calculations
		return angle;
	}
	
	public double getTimestampedYaw(long l) {
		TimestampedInfo ta = getTimestampedAngle(l);
		return ta.angle;
	}

}
