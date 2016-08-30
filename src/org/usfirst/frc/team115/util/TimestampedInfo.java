package org.usfirst.frc.team115.util;

public class TimestampedInfo {
	
	private int timestamp;
	private double angle;
	
	public TimestampedInfo() {}
	
	public TimestampedInfo(int time, int yaw) {
		timestamp = time;
		angle = yaw;
	}
	
	public void set (int time, double yaw) {
		timestamp = time;
		angle = yaw;
	}
	
	public void setTimestamp(int time) {
		timestamp = time;
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	
	public void setAngle(double yaw) {
		angle = yaw;
	}
	
	public double getAngle() {
		return angle;
	}

}
