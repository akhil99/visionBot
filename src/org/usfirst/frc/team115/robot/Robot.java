
package org.usfirst.frc.team115.robot;

import org.usfirst.frc.team115.util.Looper;
import org.usfirst.frc.team115.util.VisionDataManager;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	DriveTrain driveTrain;
	Looper driveLooper;
	DriveManager driveManager;
	OperatorInterface oi;

	VisionDataManager visionDataManager;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		visionDataManager = new VisionDataManager();
		visionDataManager.init();
		
		driveTrain = new DriveTrain(RobotMap.VICTOR_BL, RobotMap.VICTOR_BR, RobotMap.VICTORBOT_TALON, RobotMap.VICTOR_FR);
		driveLooper = new Looper("Drive", driveTrain, 1 / 200.0);
		driveManager = new DriveManager(driveTrain, visionDataManager);

		oi = new OperatorInterface();
	}

	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		driveLooper.start();
		driveManager.logDrive();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		driveManager.update(oi.getDriveStick());
	}

	public void disabledInit() {
		driveTrain.drive(0, 0);
		driveLooper.stop();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
