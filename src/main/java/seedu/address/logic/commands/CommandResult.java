package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Events should be shown to the user. */
    private final boolean showEvents;

    /** Home page should be shown to the user. */
    private final boolean showHome;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showEvents, boolean showHome, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showEvents = showEvents;
        this.showHome = showHome;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields (legacy constructor for backward compatibility).
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showEvents, boolean exit) {
        this(feedbackToUser, showHelp, showEvents, false, exit);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields (legacy constructor for backward compatibility).
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, false, false, exit);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowEvents() {
        return showEvents;
    }

    public boolean isShowHome() {
        return showHome;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && showEvents == otherCommandResult.showEvents
                && showHome == otherCommandResult.showHome
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showEvents, showHome, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("showEvents", showEvents)
                .add("showHome", showHome)
                .add("exit", exit)
                .toString();
    }

}
