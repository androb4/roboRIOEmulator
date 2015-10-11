package edu.wpi.first.wpilibj;

public class Talon extends PWMDevice {

	public Talon(final int channel) {
		super(channel);
	}

	public void set(double speed) {
		setSpeed(speed);
	}
	
	public double get() {
		return getSpeed();
	}
}
