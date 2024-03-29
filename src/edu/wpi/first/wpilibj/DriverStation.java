package edu.wpi.first.wpilibj;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.androb4.roborioemulator.utils.ReorderableJList;

import net.java.games.input.Controller;
import net.java.games.input.DirectAndRawInputEnvironmentPlugin;
import net.java.games.input.LinuxEnvironmentPlugin;
import net.java.games.input.OSXEnvironmentPlugin;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DriverStation implements RobotState.Interface {	
	private static volatile DriverStation instance = null;
	
	private int windowHeight = 200;
	private int windowWidth = 900;
	private int windowPosX = 50; // Percent
	private int windowPosY = 95; // Percent
	
	private JFrame driverStation;
	private JPanel contentPane;
	private JButton buttonEnable;
	private JButton buttonDisable;
	private JButton buttonTeleOp;
	private JButton buttonAutonomous;
	private JButton buttonTest;
	private final ReorderableJList<Controller> joysticks = new ReorderableJList<Controller>();
	
	private boolean isEnabled = false;
	private boolean isTeleop = true;
	private boolean isAutonomous;
	private boolean isTest;
	
	public static DriverStation getInstance() {
		if (instance == null) {
            synchronized (DriverStation.class) {
                if (instance == null) {
                    instance = new DriverStation();
                }
            }
        }
        return instance;
	}

	protected DriverStation() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void init() {
		
		refreshControllers();
		windowPosX = (int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-windowWidth)*(windowPosX/100.0));
		windowPosY = (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-windowHeight)*(windowPosY/100.0));
		
		driverStation = new JFrame("Driver Station");
		driverStation.setResizable(false);
		driverStation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		driverStation.setBounds(0, 0, windowWidth, windowHeight);
		driverStation.setBounds(windowPosX, windowPosY, windowWidth, windowHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		driverStation.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		buttonEnable = new JButton("ENABLE");
		buttonEnable.setEnabled(true);
		buttonEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isEnabled = true;
				buttonEnable.setEnabled(false);
				buttonDisable.setEnabled(true);
			}
		});
		buttonEnable.setBounds(38, 116, 117, 29);
		contentPane.add(buttonEnable);
		
		buttonDisable = new JButton("DISABLE");
		buttonDisable.setEnabled(false);
		buttonDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isEnabled = false;
				buttonEnable.setEnabled(true);
				buttonDisable.setEnabled(false);
			}
		});
		buttonDisable.setBounds(150, 116, 117, 29);
		contentPane.add(buttonDisable);
		
		buttonTeleOp = new JButton("TeleOperated");
		buttonTeleOp.setEnabled(false);
		buttonTeleOp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isTeleop = true;
				isAutonomous = false;
				isTest = false;
				buttonTeleOp.setEnabled(false);
				buttonAutonomous.setEnabled(true);
				buttonTest.setEnabled(true);
			}
		});
		buttonTeleOp.setBounds(38, 18, 117, 29);
		contentPane.add(buttonTeleOp);
		
		buttonAutonomous = new JButton("Autonomous");
		buttonAutonomous.setEnabled(true);
		buttonAutonomous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isTeleop = false;
				isAutonomous = true;
				isTest = false;
				buttonTeleOp.setEnabled(true);
				buttonAutonomous.setEnabled(false);
				buttonTest.setEnabled(true);
			}
		});
		buttonAutonomous.setBounds(38, 41, 117, 29);
		contentPane.add(buttonAutonomous);
		
		buttonTest = new JButton("Test");
		buttonTest.setEnabled(true);
		buttonTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isTeleop = false;
				isAutonomous = false;
				isTest = true;
				buttonTeleOp.setEnabled(true);
				buttonAutonomous.setEnabled(true);
				buttonTest.setEnabled(false);
			}
		});
		buttonTest.setBounds(38, 71, 117, 29);
		contentPane.add(buttonTest);
		
		JPanel controllers = new JPanel( new BorderLayout() );
		controllers.setLocation(450, 89);
		controllers.add( joysticks, BorderLayout.CENTER );
		
		contentPane.add(controllers);
		
		driverStation.setVisible(true);
	}
	
	private boolean isValidControllerType( Controller.Type type ) {
		return type == Controller.Type.STICK || type == Controller.Type.GAMEPAD;
	}
	
	private Controller[] getAllControllers() {
		String os = System.getProperty( "os.name" ).trim().toLowerCase();
		if ( os.contains( "windows" ) ) {
			return new DirectAndRawInputEnvironmentPlugin().getControllers();
		} else if ( os.contains( "mac" ) ) {
			return new OSXEnvironmentPlugin().getControllers();
		} else if ( os.contains( "linux" ) ) {
			return new LinuxEnvironmentPlugin().getControllers();
		}
		return null;
	}
	
	private void refreshControllers() {
		System.out.println( "Refreshing controller list..." );
		joysticks.clear();
		Controller[] all = getAllControllers();
		for ( int i = 0; i < all.length; i++ ) {
			if ( isValidControllerType( all[i].getType() ) ) {
				joysticks.addElement( all[i] );
			}
		}
		System.out.println( joysticks.getList().getModel().getSize() + " controller(s) found." );
	}
	
	private Controller getController( int port ) {
		DefaultListModel<Controller> model = joysticks.getDefaultListModel();
		if ( port <= 0 || port > model.size() ) {
			return null;
		}
		return model.get( port - 1 );
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public boolean isDisabled() {
		return !isEnabled;
	}
	
	public boolean isAutonomous() {
		return isAutonomous;
	}
	
	public boolean isTest() {
		return isTest;
	}
	
	public boolean isOperatorControl() {
		return isTeleop;
	}
}
