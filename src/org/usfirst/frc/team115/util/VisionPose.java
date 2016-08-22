package org.usfirst.frc.team115.util;

public class VisionPose {

	public long timestamp;
	public double vertAngle;
	public double horizAngle;
	
	public VisionPose(long time, double vertA, double horizA){
		this.timestamp = time;
		this.vertAngle = vertA;
		this.horizAngle = horizA;
	}
	
}
