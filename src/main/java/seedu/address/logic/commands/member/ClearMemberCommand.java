package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Clear members based on the command input
 */
public class ClearMemberCommand extends ClearCommand {
    public static final String MESSAGE_SUCCESS = "Cleared all members";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetMembers();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
