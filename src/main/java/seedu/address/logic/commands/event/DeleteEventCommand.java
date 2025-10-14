package seedu.address.logic.commands.event;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes an event identified using it's displayed index from the event list.
  */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    public DeleteEventCommand(Index targetIndex) {
        super(targetIndex);
    }

    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}

