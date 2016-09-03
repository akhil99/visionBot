package org.usfirst.frc.team115.util;

public class TimestampedInfo {
	
	public long timestamp;
	public double angle;
	
	public TimestampedInfo() {}
	
	public TimestampedInfo(long time, double yaw) {
		timestamp = time;
		angle = yaw;
	}
	
	public void set (long time, double yaw) {
		timestamp = time;
		angle = yaw;
	}
	
	public void setTimestamp(long time) {
		timestamp = time;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setAngle(double yaw) {
		angle = yaw;
	}
	
	public double getAngle() {
		return angle;
	}

}
