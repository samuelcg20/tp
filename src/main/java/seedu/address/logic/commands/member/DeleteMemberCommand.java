package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteMemberCommand extends DeleteCommand {

//    public static final String COMMAND_WORD = "delete";
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD
//            + ": Deletes the member/event identified by the index number used in the displayed member/event list.\n"
//            + "Parameters: TYPE (either 'member' or 'event'), INDEX (must be a positive integer)\n"
//            + "Example: " + COMMAND_WORD + " member 1";
//
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
//
//    private final Index targetIndex;

    public DeleteMemberCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(getTargetIndex().getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMemberCommand)) {
            return false;
        }

        DeleteMemberCommand otherDeleteCommand = (DeleteMemberCommand) other;
        return getTargetIndex().equals(otherDeleteCommand.getTargetIndex());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", getTargetIndex())
                .toString();
    }
}
