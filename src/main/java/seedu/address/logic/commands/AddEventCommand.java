package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.member.Command;
import seedu.address.logic.commands.member.CommandResult;
import seedu.address.model.Model;

/**
 * Adds an event to the event book.
  */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event. "
            + "Parameters: n/NAME d/DATE(YYYY-MM-DD) v/VENUE\n"
            + "Example: " + COMMAND_WORD + " n/CS2103 Lecture d/2025-10-01 v/COM1 LT19";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book";

    /**
     * Creates an AddEventCommand to add the specified {@code Event}.
     */
    public AddEventCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}

