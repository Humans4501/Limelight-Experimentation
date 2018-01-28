package org.usfirst.frc.team4501.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */

public class Robot extends SampleRobot {
	public static Robot instance;
	RobotDrive myRobot = new RobotDrive(0, 1); // class that handles basic drive
												// operations

	RobotPID robotPID;

	Joystick leftStick = new Joystick(0); // set to ID 1 in DriverStation
	Joystick rightStick = new Joystick(1); // set to ID 2 in DriverStation

	NetworkTable table;

	public Robot() {
		instance = this;
		myRobot.setExpiration(0.1);
		NetworkTable.setIPAddress("10.95.1.55");
		table = NetworkTable.getTable("limelight");

		robotPID = new RobotPID();
	} 
	/**
	 * Runs the motors with tank steering.
	 */
	@Override
	public void operatorControl() {
		System.out.println("OperatorControl: isOperatorControl = " + isOperatorControl());
		myRobot.setSafetyEnabled(true);
		robotPID.enable();
		while (isOperatorControl() && isEnabled()) {
			double tx = table.getNumber("tx", 0);
			double ty = table.getNumber("ty", 0);
			double targetArea = table.getNumber("ta", 0);
			double targetSkew = table.getNumber("ts", 0);
			double targetView = table.getNumber("tv", 0);

			SmartDashboard.putNumber("targetView", targetView);
			SmartDashboard.putString("tx", String.format("%.2g", tx));
			SmartDashboard.putString("ty", String.format("%.2g", ty));
			
		
		}
		robotPID.disable();
	}

	public void setTankDrive(double left, double right) {
		myRobot.tankDrive(left, right);
	}
}
