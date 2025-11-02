package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds a new event to the address book.
 *
 * <p>This command creates an {@link Event} object and adds it to the model if
 * it does not already exist. Events are considered duplicates if they share the
 * same identifying details (as defined by {@code Event#equals(Event)}).</p>
 *
 * <p>Example usage:
 * <pre>
 *     addevent n/CS2103T Lecture d/2025-10-31T14:00 l/COM1-0207
 * </pre>
 * adds a new event named "CS2103T Lecture" scheduled at COM1-0207 on 31 Oct 2025, 2:00 PM.</p>
 */
public class AddEventCommand extends AddCommand {

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT =
            "Event with the same venue and time already exists in the address book";

    private final Event toAdd;

    /**
     * Constructs an {@code AddEventCommand} with the specified {@code Event} to be added.
     *
     * @param event the event to add; must not be {@code null}.
     */
    public AddEventCommand(Event event) {
        super();
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return CommandResult.showEvents(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.logic.commands.event.AddEventCommand)) {
            return false;
        }

        seedu.address.logic.commands.event.AddEventCommand otherAddCommand =
                (seedu.address.logic.commands.event.AddEventCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
