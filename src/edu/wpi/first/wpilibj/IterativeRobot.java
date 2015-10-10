package edu.wpi.first.wpilibj;

public class IterativeRobot extends RobotBase{
	
	private boolean m_disabledInitialized;
    private boolean m_autonomousInitialized;
    private boolean m_teleopInitialized;
    private boolean m_testInitialized;
 
	public void robotInit() {
		
	}
	
	public void disabledInit() {
		
	}
	
	public void disabledPeriodic() {
		
	}
	
	public void autonomousInit() {
		
	}

	public void autonomousPeriodic() {
		
	}
	
	public void teleopInit() {
		
	}
	
	public void teleopPeriodic() {
		
	}
	
	public void testInit() {
		
	}
	
	public void testPeriodic() {
		
	}

	public void startCompetition() {
		
		robotInit();
		
		while (true) {
            // Call the appropriate function depending upon the current robot mode
            if (isDisabled()) {
                // call DisabledInit() if we are now just entering disabled mode from
                // either a different mode or from power-on
                if (!m_disabledInitialized) {
                    disabledInit();
                    m_disabledInitialized = true;
                    // reset the initialization flags for the other modes
                    m_autonomousInitialized = false;
                    m_teleopInitialized = false;
                    m_testInitialized = false;
                }
                disabledPeriodic();
            } else if (isTest()) {
                // call TestInit() if we are now just entering test mode from either
                // a different mode or from power-on
                if (!m_testInitialized) {
                    testInit();
                    m_testInitialized = true;
                    m_autonomousInitialized = false;
                    m_teleopInitialized = false;
                    m_disabledInitialized = false;
                }
                    testPeriodic();
            } else if (isAutonomous()) {
                // call Autonomous_Init() if this is the first time
                // we've entered autonomous_mode
                if (!m_autonomousInitialized) {
                    // KBS NOTE: old code reset all PWMs and relays to "safe values"
                    // whenever entering autonomous mode, before calling
                    // "Autonomous_Init()"
                    autonomousInit();
                    m_autonomousInitialized = true;
                    m_testInitialized = false;
                    m_teleopInitialized = false;
                    m_disabledInitialized = false;
                }
                autonomousPeriodic();
            } else {
                // call Teleop_Init() if this is the first time
                // we've entered teleop_mode
                if (!m_teleopInitialized) {
                    teleopInit();
                    m_teleopInitialized = true;
                    m_testInitialized = false;
                    m_autonomousInitialized = false;
                    m_disabledInitialized = false;
                }
                teleopPeriodic();
            }
        }
	}
	
}
