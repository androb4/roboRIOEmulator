package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public abstract class Trigger {
	/**
     * Returns whether or not the trigger is active
     *
     * This method will be called repeatedly a command is linked to the Trigger.
     *
     * @return whether or not the trigger condition is active.
     */
    public abstract boolean get();

    /**
     * Returns whether get() return true or the internal table for SmartDashboard use is pressed.
     * @return  whether get() return true or the internal table for SmartDashboard use is pressed
     */
    private boolean grab() {
        return get();//FIXME make is connected work?
    }

    /**
     * Starts the given command whenever the trigger just becomes active.
     * @param command the command to start
     */
    public void whenActive(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    if (!pressedLast) {
                        pressedLast = true;
                        command.start();
                    }
                } else {
                    pressedLast = false;
                }
            }
        } .start();
    }

    /**
     * Constantly starts the given command while the button is held.
     *
     * {@link Command#start()} will be called repeatedly while the trigger is active,
     * and will be canceled when the trigger becomes inactive.
     *
     * @param command the command to start
     */
    public void whileActive(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    pressedLast = true;
                    command.start();
                } else {
                    if (pressedLast) {
                        pressedLast = false;
                        command.cancel();
                    }
                }
            }
        } .start();
    }

    /**
     * Starts the command when the trigger becomes inactive
     * @param command the command to start
     */
    public void whenInactive(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    pressedLast = true;
                } else {
                    if (pressedLast) {
                        pressedLast = false;
                        command.start();
                    }
                }
            }
        } .start();
    }

    /**
      * Toggles a command when the trigger becomes active
      * @param command the command to toggle
      */
    public void toggleWhenActive(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    if (!pressedLast) {
                        pressedLast = true;
                        if (command.isRunning()) {
                            command.cancel();
                        } else {
                            command.start();
                        }
                    }
                } else {
                    pressedLast = false;
                }
            }
        } .start();
    }

    /**
      * Cancels a command when the trigger becomes active
      * @param command the command to cancel
      */
    public void cancelWhenActive(final Command command) {
        new ButtonScheduler() {

            boolean pressedLast = grab();

            public void execute() {
                if (grab()) {
                    if (!pressedLast) {
                        pressedLast = true;
                        command.cancel();
                    }
                } else {
                    pressedLast = false;
                }
            }
        } .start();
    }

    /**
     * An internal class of {@link Trigger}.  The user should ignore this, it is
     * only public to interface between packages.
     */
    public abstract class ButtonScheduler {
        public abstract void execute();

        protected void start() {
            Scheduler.getInstance().addButton(this);
        }
    }
}
