package edu.wpi.first.wpilibj;

import com.androb4.roborioemulator.utils.RobotClassReader;

public abstract class RobotBase {
	protected final DriverStation ds;
	protected final RoboRio rio;

	protected RobotBase() {
		ds = DriverStation.getInstance();
		rio = RoboRio.getInstance();
	}
	
	public boolean isEnabled() {
		return ds.isEnabled();
	}
	
	public boolean isDisabled() {
		return ds.isDisabled();
	}
	
	public boolean isAutonomous() {
		return ds.isAutonomous();
	}
	
	public boolean isTest() {
		return ds.isTest();
	}
	
	public boolean isOperatorControl() {
		return ds.isOperatorControl();
	}
	
	public abstract void startCompetition();
	
	public static void main(String[] args) {
		System.out.println("Main");
		
		RobotState.SetImplementation(DriverStation.getInstance());
		
		RobotBase robot = null;
		try {
			robot = (RobotBase)RobotBase.class.getClassLoader().loadClass(RobotClassReader.getRobotClass()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		robot.startCompetition();
	}
}
