package edu.wpi.first.wpilibj;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DriverStation {	
	private static volatile DriverStation instance = null;
	
	private int windowHeight = 200;
	private int windowWidth = 900;
	private int windowPosX = 50; // Percent
	private int windowPosY = 95; // Percent
	
	private JFrame driverStation;
	private JPanel contentPane;
	private JButton buttonEnable;
	private JButton buttonDisable;
	
	private boolean isEnabled;
	
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
		windowPosX = (int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-windowWidth)*(windowPosX/100.0));
		windowPosY = (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-windowHeight)*(windowPosY/100.0));
		
		driverStation = new JFrame("Driver Station");
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
		buttonEnable.setBounds(85, 75, 117, 29);
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
		buttonDisable.setBounds(201, 75, 117, 29);
		contentPane.add(buttonDisable);
		driverStation.setVisible(true);
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public boolean isDisabled() {
		return !isEnabled;
	}
	
	public boolean isAutonomous() {
		return false;
	}
	
	public boolean isTest() {
		return false;
	}
	
	public boolean isOperatorControl() {
		return false;
	}
}
