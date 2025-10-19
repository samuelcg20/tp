package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ClearEventCommand extends ClearCommand {
    public static final String MESSAGE_SUCCESS = "Cleared all events";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetEvents();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
