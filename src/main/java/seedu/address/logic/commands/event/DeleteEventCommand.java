package seedu.address.logic.commands.event;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes an event identified using it's displayed index from the event list.
  */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    public DeleteEventCommand(Index targetIndex) {
        super(targetIndex);
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(getTargetIndex().getZeroBased());
        model.deleteEvent(eventToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
    }
}

