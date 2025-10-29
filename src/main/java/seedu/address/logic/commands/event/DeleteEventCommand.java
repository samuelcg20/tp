package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Deletes an event identified using it's displayed index from the event list.
  */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    public DeleteEventCommand(Index targetIndex) {
        super(targetIndex);
    }

    /**
     * Executes the deletion of the event specified by the target index.
     * <p>
     * If the provided index is invalid (i.e. out of bounds of the current event list),
     * a {@link CommandException} is thrown.
     *
     * @param model the {@code Model} containing the event list and handling data manipulation
     * @return a {@code CommandResult} containing feedback about the successful deletion
     * @throws CommandException if the target index is invalid or the deletion fails
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(getTargetIndex().getZeroBased());
        model.deleteEvent(eventToDelete);
        return CommandResult.showEvents(
                String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand otherEventCommand = (DeleteEventCommand) other;
        return getTargetIndex().equals(otherEventCommand.getTargetIndex());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", getTargetIndex())
                .toString();
    }
}

