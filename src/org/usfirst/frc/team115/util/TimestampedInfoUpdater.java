package org.usfirst.frc.team115.util;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TimestampedInfoUpdater {
	
	Looper updateLooper;
	
	TimestampedHistory history;
	AHRS navX;
	
	public TimestampedInfoUpdater(AHRS navX) {
		this.navX = navX;
		
		history = new TimestampedHistory(500);
		
		updateLooper = new Looper("NavX History", updateLoopable, 1/100.0);
		updateLooper.start();
	}
	
	private Loopable updateLoopable = new Loopable() {
		
		@Override
		public void update() {
			history.add(System.currentTimeMillis(), navX.getYaw());
			SmartDashboard.putNumber("CurrentAngle", navX.getYaw());
			SmartDashboard.putNumber("PastAngle", history.getTimestampedYaw(System.currentTimeMillis() - 2000));
		}
	};

}
