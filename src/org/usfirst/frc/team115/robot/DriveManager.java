package org.usfirst.frc.team115.robot;

import org.usfirst.frc.team115.util.VisionDataManager;
import org.usfirst.frc.team115.util.VisionPose;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class DriveManager {
	
	public static final double TURN_P = 0.02;
	public static final double TURN_I = 0.0;
	public static final double TURN_D = 0.0;
	public static final double TURN_OUTPUT_MAX = 0.5;
	public static final double TURN_ABSOLUTE_TOLERANCE = 2.0;
	public static final int TURN_TOLERANCE_BUFFER = 5;
	
	double latest_yaw_angle = 0; //represents the latest yaw angle reported *before* the PID controller started
	
	CANDriveTrain driveTrain;
	VisionDataManager visionDataManager;
	
	PIDController turnController;
	PIDOutput turnPIDOutput;
	
	public DriveManager(CANDriveTrain driveTrain, VisionDataManager visionManager){
		this.driveTrain = driveTrain;
		this.visionDataManager = visionManager;
		
		initTurnController();
		LiveWindow.addActuator("DriveManager", "TurnController", turnController);
	}
	
	public void update(Joystick joystick){
		if(joystick.getRawButton(RobotMap.TURN_BUTTON)) {
			turnController.setSetpoint(addAngles(latest_yaw_angle, 90));
			
			/*VisionPose p = visionDataManager.latestPose;
			long timeStamp = p.timestamp;
			//todo: lookup angle pose at timestamp
			turnController.setSetpoint(addAngles(a, b));
			turnController.enable(); */
		}
		else {
			latest_yaw_angle = driveTrain.getNavX().getYaw();
			turnController.disable();
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
