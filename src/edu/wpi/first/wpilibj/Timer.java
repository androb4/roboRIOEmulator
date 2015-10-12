package edu.wpi.first.wpilibj;

public class Timer {

	public static void delay(final double seconds) {
		try {
			Thread.sleep((long)(seconds*100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static double getFPGATimestamp() {
		return System.currentTimeMillis()*1000.0;
	}
}
