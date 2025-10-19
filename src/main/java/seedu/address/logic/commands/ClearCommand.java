package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

/**
 * Clears the address book.
 */
public abstract class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the members/events list.\n"
            + "Example: " + COMMAND_WORD + " member/event";
}
