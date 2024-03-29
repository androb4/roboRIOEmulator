package edu.wpi.first.wpilibj.command;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;

public class Scheduler {
	/**
     * The Singleton Instance
     */
    private static Scheduler instance;

    /**
     * Returns the {@link Scheduler}, creating it if one does not exist.
     *
     * @return the {@link Scheduler}
     */
    public synchronized static Scheduler getInstance() {
        return instance == null ? instance = new Scheduler() : instance;
    }
    /**
     * A hashtable of active {@link Command Commands} to their
     * {@link LinkedListElement}
     */
    private Hashtable commandTable = new Hashtable();
    /**
     * The {@link Set} of all {@link Subsystem Subsystems}
     */
    private Set subsystems = new Set();
    /**
     * The first {@link Command} in the list
     */
    private LinkedListElement firstCommand;
    /**
     * The last {@link Command} in the list
     */
    private LinkedListElement lastCommand;
    /**
     * Whether or not we are currently adding a command
     */
    private boolean adding = false;
    /**
     * Whether or not we are currently disabled
     */
    private boolean disabled = false;
    /**
     * A list of all {@link Command Commands} which need to be added
     */
    private Vector additions = new Vector();
    /**
     * A list of all
     * {@link edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler Buttons}. It
     * is created lazily.
     */
    private Vector buttons;
    private boolean m_runningCommandsChanged;

    /**
     * Instantiates a {@link Scheduler}.
     */
    private Scheduler() {

    }

    /**
     * Adds the command to the {@link Scheduler}. This will not add the
     * {@link Command} immediately, but will instead wait for the proper time in
     * the {@link Scheduler#run()} loop before doing so. The command returns
     * immediately and does nothing if given null.
     *
     * <p>Adding a {@link Command} to the {@link Scheduler} involves the
     * {@link Scheduler} removing any {@link Command} which has shared
     * requirements.</p>
     *
     * @param command the command to add
     */
    public void add(Command command) {
        if (command != null) {
            additions.addElement(command);
        }
    }

    /**
     * Adds a button to the {@link Scheduler}. The {@link Scheduler} will poll
     * the button during its {@link Scheduler#run()}.
     *
     * @param button the button to add
     */
    public void addButton(ButtonScheduler button) {
        if (buttons == null) {
            buttons = new Vector();
        }
        buttons.addElement(button);
    }

    /**
     * Adds a command immediately to the {@link Scheduler}. This should only be
     * called in the {@link Scheduler#run()} loop. Any command with conflicting
     * requirements will be removed, unless it is uninterruptable. Giving
     * <code>null</code> does nothing.
     *
     * @param command the {@link Command} to add
     */
    private void _add(Command command) {
        if (command == null) {
            return;
        }

        // Check to make sure no adding during adding
        if (adding) {
            System.err.println("WARNING: Can not start command from cancel method.  Ignoring:" + command);
            return;
        }

        // Only add if not already in
        if (!commandTable.containsKey(command)) {

            // Check that the requirements can be had
            Enumeration requirements = command.getRequirements();
            while (requirements.hasMoreElements()) {
                Subsystem lock = (Subsystem) requirements.nextElement();
                if (lock.getCurrentCommand() != null && !lock.getCurrentCommand().isInterruptible()) {
                    return;
                }
            }

            // Give it the requirements
            adding = true;
            requirements = command.getRequirements();
            while (requirements.hasMoreElements()) {
                Subsystem lock = (Subsystem) requirements.nextElement();
                if (lock.getCurrentCommand() != null) {
                    lock.getCurrentCommand().cancel();
                    remove(lock.getCurrentCommand());
                }
                lock.setCurrentCommand(command);
            }
            adding = false;

            // Add it to the list
            LinkedListElement element = new LinkedListElement();
            element.setData(command);
            if (firstCommand == null) {
                firstCommand = lastCommand = element;
            } else {
                lastCommand.add(element);
                lastCommand = element;
            }
            commandTable.put(command, element);

            m_runningCommandsChanged = true;

            command.startRunning();
        }
    }

    /**
     * Runs a single iteration of the loop. This method should be called often
     * in order to have a functioning {@link Command} system. The loop has five
     * stages:
     *
     * <ol> <li> Poll the Buttons </li> <li> Execute/Remove the Commands </li>
     * <li> Send values to SmartDashboard </li> <li> Add Commands </li> <li> Add
     * Defaults </li> </ol>
     */
    public void run() {

        m_runningCommandsChanged = false;

        if (disabled) {
            return;
        } // Don't run when disabled

        // Get button input (going backwards preserves button priority)
        if (buttons != null) {
            for (int i = buttons.size() - 1; i >= 0; i--) {
                ((ButtonScheduler) buttons.elementAt(i)).execute();
            }
        }
        // Loop through the commands
        LinkedListElement e = firstCommand;
        while (e != null) {
            Command c = e.getData();
            e = e.getNext();
            if (!c.run()) {
                remove(c);
                m_runningCommandsChanged = true;
            }
        }

        // Add the new things
        for (int i = 0; i < additions.size(); i++) {
            _add((Command) additions.elementAt(i));
        }
        additions.removeAllElements();

        // Add in the defaults
        Enumeration locks = subsystems.getElements();
        while (locks.hasMoreElements()) {
            Subsystem lock = (Subsystem) locks.nextElement();
            if (lock.getCurrentCommand() == null) {
                _add(lock.getDefaultCommand());
            }
            lock.confirmCommand();
        }
    }

    /**
     * Registers a {@link Subsystem} to this {@link Scheduler}, so that the
     * {@link Scheduler} might know if a default {@link Command} needs to be
     * run. All {@link Subsystem Subsystems} should call this.
     *
     * @param system the system
     */
    void registerSubsystem(Subsystem system) {
        if (system != null) {
            subsystems.add(system);
        }
    }

    /**
     * Removes the {@link Command} from the {@link Scheduler}.
     *
     * @param command the command to remove
     */
    void remove(Command command) {
        if (command == null || !commandTable.containsKey(command)) {
            return;
        }
        LinkedListElement e = (LinkedListElement) commandTable.get(command);
        commandTable.remove(command);

        if (e.equals(lastCommand)) {
            lastCommand = e.getPrevious();
        }
        if (e.equals(firstCommand)) {
            firstCommand = e.getNext();
        }
        e.remove();

        Enumeration requirements = command.getRequirements();
        while (requirements.hasMoreElements()) {
            ((Subsystem) requirements.nextElement()).setCurrentCommand(null);
        }

        command.removed();
    }

    /**
     * Removes all commands
     */
    public void removeAll() {
        while (firstCommand != null) {
            remove(firstCommand.getData());
        }
    }

    /**
     * Disable the command scheduler.
     */
    public void disable() {
        disabled = true;
    }

    /**
     * Enable the command scheduler.
     */
    public void enable() {
        disabled = false;
    }

    public String getName() {
        return "Scheduler";
    }

    public String getType() {
        return "Scheduler";
    }

}
