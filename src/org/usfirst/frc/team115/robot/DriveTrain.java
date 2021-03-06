package org.usfirst.frc.team115.robot;

import org.usfirst.frc.team115.util.Loopable;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain extends Loopable {
	
	static final boolean INVERT_MOVE = true;
	static final boolean INVERT_TURN = true;
	
	private volatile double moveCommand;
	private volatile double turnCommand;
	
	RobotDrive robotDrive;
	AHRS navX;
	
	public DriveTrain(int backLeft, int backRight, int frontLeft, int frontRight){
		robotDrive = new RobotDrive(new CANTalon(frontLeft), new VictorSP(backLeft), new VictorSP(frontRight), new VictorSP(backRight));		
		
		navX = new AHRS(SPI.Port.kMXP);
	}

	public void drive(double move, double turn){
		if(INVERT_MOVE)move *= -1;
		if(INVERT_TURN)turn *= -1;
		this.moveCommand = move;
		this.turnCommand = turn;
	}
	
	@Override
	public void update() {
		robotDrive.arcadeDrive(moveCommand, turnCommand);
	}
	
	@Override
	public void stopLooping() {
		robotDrive.arcadeDrive(0, 0);
	}
	
	public AHRS getNavX() {
		return navX;
	}
	
}
