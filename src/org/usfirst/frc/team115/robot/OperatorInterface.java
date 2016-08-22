package org.usfirst.frc.team115.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

	Joystick driveStick;
	
	public OperatorInterface(){
		driveStick = new Joystick(RobotMap.JOYSTICK_DRIVE);
	}
	
	public Joystick getDriveStick(){
		return driveStick;
	}
	
}
