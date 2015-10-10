package edu.wpi.first.wpilibj;

public abstract class RobotBase {
	protected final DriverStation ds;

	protected RobotBase() {
		ds = DriverStation.getInstance();
	}
	
	public boolean isEnabled() {
		return isEnabled();
	}
	
	public boolean isDisabled() {
		return isDisabled();
	}
	
	public boolean isAutonomous() {
		return isAutonomous();
	}
	
	public boolean isTest() {
		return isTest();
	}
	
	public boolean isOperatorControl() {
		return isOperatorControl();
	}
	
	public static void main(String[] args) {
		RobotBase robot;
		try {
			robot = (RobotBase)RobotBase.class.getClassLoader().loadClass("com.androb4.helloworldfrc.Robot").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Main");
	}
}
