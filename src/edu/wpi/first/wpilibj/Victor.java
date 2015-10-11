package edu.wpi.first.wpilibj;

public class Victor extends PWMDevice {

	public Victor(final int channel) {
		super(channel);
	}

	public void set(double speed) {
		setSpeed(speed);
	}
	
	public double get() {
		return getSpeed();
	}
}