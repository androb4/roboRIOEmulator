package edu.wpi.first.wpilibj.command;

@SuppressWarnings("serial")
public class IllegalUseOfCommandException extends RuntimeException {
	/**
     * Instantiates an {@link IllegalUseOfCommandException}.
     */
    public IllegalUseOfCommandException() {
    }

    /**
     * Instantiates an {@link IllegalUseOfCommandException} with the given message.
     * @param message the message
     */
    public IllegalUseOfCommandException(String message) {
        super(message);
    }
}
