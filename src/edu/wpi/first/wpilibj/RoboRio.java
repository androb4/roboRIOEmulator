package edu.wpi.first.wpilibj;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class RoboRio extends JFrame {	
	private static volatile RoboRio instance = null;
	
	private int windowHeight = 700;
	private int windowWidth = 700;
	private int windowPosX = 50;
	private int windowPosY = 20;
	
	private JPanel contentPane;

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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, windowWidth, windowHeight);
		setBounds(windowPosX, windowPosY, windowWidth, windowHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setVisible(true);
	}
}
