package org.usfirst.frc.team4501.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotPID extends PIDSubsystem {
	
	static double Kp = 0.12;
	static double Ki = 0.005;
	static double Kd = 0.05;
	
	static double MAX_RANGE = 1;
	
	NetworkTable table;
	double tx;
	
	public RobotPID() {
		super("RobotPID", Kp, Ki, Kd);
		System.out.println("RobotPID");
		getPIDController().setContinuous(false);
		getPIDController().setOutputRange(-MAX_RANGE, MAX_RANGE);
		//LiveWindow.addActuator("RobotPID", "pid", getPIDController());
		SmartDashboard.putData("RobotPID", getPIDController());
		NetworkTable.setIPAddress("10.95.1.55");
		table = NetworkTable.getTable("limelight");
	}

	@Override
	protected double returnPIDInput() {
		// Read limelight horizontal error
		tx = table.getNumber("tx", 0);
		return tx;
	}

	@Override
	protected void usePIDOutput(double output) {
		System.out.printf("TX=%.2g, L=%.2g R=%.2g\n", tx, output, -output);
		Robot.instance.setTankDrive(output, -output);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
