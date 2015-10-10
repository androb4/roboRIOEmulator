package edu.wpi.first.wpilibj;

public class Timer {

	public static void delay(final double seconds) {
		try {
			Thread.sleep((long)(seconds*100));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
