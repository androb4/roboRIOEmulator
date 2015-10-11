package edu.wpi.first.wpilibj;

public abstract class PWMDevice {
	private int channel;
	private int speed;
	
	public PWMDevice(final int channel) {
		this.channel = channel;
	}
	
	public void setSpeed(double speed) {
		RoboRio.getInstance().PWM[channel].setString(Double.toString(speed));
		RoboRio.getInstance().PWM[channel].setValue((int)(speed*100));
	}
	
	public double getSpeed() {
		return speed;
	}
	
}