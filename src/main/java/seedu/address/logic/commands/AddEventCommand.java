package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

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

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}.
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        this.toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
        model.addEvent(toAdd);
        return CommandResult.forEvents(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddEventCommand
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}