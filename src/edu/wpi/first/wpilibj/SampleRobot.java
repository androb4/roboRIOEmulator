package edu.wpi.first.wpilibj;

public class SampleRobot extends RobotBase {

	public void robotInit() {
		
	}
	
	public void disabled() {
		
	}
	
	public void autonomous() {
		
	}
	
	public void operatorControl() {
		
	}

	public void test() {
		
	}
	
	public void startCompetition() {
		robotInit();

        while (true) {
            if (isDisabled()) {
                disabled();
                while (isDisabled()) {
                    Timer.delay(0.01);
                }
            } else if (isAutonomous()) {
                autonomous();
                while (isAutonomous() && !isDisabled()) {
                    Timer.delay(0.01);
                }
            } else if (isTest()) {
                test();
                while (isTest() && isEnabled())
                    Timer.delay(0.01);
            } else {
                operatorControl();
                while (isOperatorControl() && !isDisabled()) {
                    Timer.delay(0.01);
                }
            }
        }
	}
	
}
