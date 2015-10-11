package edu.wpi.first.wpilibj;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;

public class RoboRio {	
	private static volatile RoboRio instance = null;
	
	private int windowHeight = 700;
	private int windowWidth = 700;
	private int windowPosX = 50;
	private int windowPosY = 20;
	
	private JFrame roboRio;
	private JPanel contentPane;
	public JProgressBar[] PWM = new JProgressBar[10];

	public static RoboRio getInstance() {
		if (instance == null) {
            synchronized (RoboRio.class) {
                if (instance == null) {
                    instance = new RoboRio();
                }
            }
        }
        return instance;
	}

	protected RoboRio() {
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
		
		roboRio = new JFrame("RoboRIO");
		roboRio.setResizable(false);
		roboRio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		roboRio.setBounds(0, 0, windowWidth, windowHeight);
		roboRio.setBounds(windowPosX, windowPosY, windowWidth, windowHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		roboRio.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		for(int i=0; i<10; i++) {
			PWM[i] = new JProgressBar();
			PWM[i].setMinimum(-100);
			PWM[i].setMaximum(100);
			PWM[i].setStringPainted(true);
			PWM[i].setString("0.0");
			PWM[i].setBounds(84, 121+(20*i), 146, 20);
			contentPane.add(PWM[i]);
		}
		roboRio.setVisible(true);
	}
}
