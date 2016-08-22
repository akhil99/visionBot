
package org.usfirst.frc.team115.robot;

import org.usfirst.frc.team115.util.Looper;
import org.usfirst.frc.team115.util.VisionDataManager;

import edu.wpi.first.wpilibj.IterativeRobot;


public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    
    CANDriveTrain canDriveTrain;
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
    	
        canDriveTrain = new CANDriveTrain(RobotMap.TALON_BL, RobotMap.TALON_BR, RobotMap.TALON_FL, RobotMap.TALON_FR);
        driveLooper = new Looper("Drive", canDriveTrain, 1 / 200.0);
        driveManager = new DriveManager(canDriveTrain, visionDataManager);
        
        //visionDataManager.init();
        
        oi = new OperatorInterface();
    }
    

    public void autonomousInit() {}

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {}

    
    public void teleopInit() {
    	driveLooper.start();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        driveManager.update(oi.getDriveStick());
    }
    
    public void disabledInit() {
    	driveLooper.stop();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
