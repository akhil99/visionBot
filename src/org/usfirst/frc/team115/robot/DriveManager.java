package org.usfirst.frc.team115.robot;

import org.usfirst.frc.team115.util.VisionDataManager;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveManager {
	
	public static final double TURN_P = 0.08;
	public static final double TURN_I = 0.00;
	public static final double TURN_D = 0.1;
	public static final double TURN_OUTPUT_MAX = 0.65;
	public static final double TURN_ABSOLUTE_TOLERANCE = 2.0;
	public static final int TURN_TOLERANCE_BUFFER = 5000000;
	
	double latest_yaw_angle = 0; //represents the latest yaw angle reported *before* the PID controller started
	
	DriveTrain driveTrain;
	VisionDataManager vdm;
	AHRS navX;
	
	PIDController turnController;
	PIDOutput turnPIDOutput;
	
	public DriveManager(DriveTrain driveTrain, VisionDataManager visionManager){
		this.driveTrain = driveTrain;
		this.vdm = visionManager;
		this.navX = driveTrain.getNavX();
		
		initTurnController();
	}
	
	public void logDrive() {
		LiveWindow.addActuator("DriveManager", "TurnController", turnController);
	}
	
	public void update(Joystick joystick){
		
		if(joystick.getRawButton(RobotMap.TURN_BUTTON)) {
			synchronized (vdm.latestPose) {
				if(vdm.latestPose.timestamp < 0)return; //we don't have vision info yet
				double yaw = navX.getYawAtTime(vdm.latestPose.timestamp);
				turnController.setSetpoint(addAngles(yaw, Math.toDegrees(vdm.latestPose.horizAngle)));
			}
			
			if(!turnController.isEnabled()) turnController.enable();
			
			SmartDashboard.putNumber("Setpoint", turnController.getSetpoint());
			SmartDashboard.putNumber("Error", turnController.getError());
		}
		else {
			if(turnController.isEnabled()) turnController.disable();
			driveTrain.drive(joystick.getY(), joystick.getX());
		}
	}
	
	public void initTurnController() {
		turnPIDOutput = new PIDOutput() {
			
			public void pidWrite(double output) {
				driveTrain.drive(0, output);
			}
			
		};
		
		turnController = new PIDController(TURN_P, TURN_I, TURN_D, driveTrain.getNavX(), turnPIDOutput);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1 * TURN_OUTPUT_MAX, 1 * TURN_OUTPUT_MAX);
		turnController.setAbsoluteTolerance(TURN_ABSOLUTE_TOLERANCE);
		turnController.setContinuous(true);
		turnController.setToleranceBuffer(TURN_TOLERANCE_BUFFER);
	}
	
	/**
	 * Adds two angles between -180 and 180 degrees, returning a result between -180 and -180
	 * @param a: Angle between -180 and 180
	 * @param b: Angle between -180 and 180
	 * @return an angle x where x = a + b, and -180 <= x <= 180
	 */
	public double addAngles(double a, double b){
		return (540 + a + b)%360 - 180;
	}
	
}
