package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.member.Command;
import seedu.address.logic.commands.member.CommandResult;
import seedu.address.model.Model;

/**
 * Returns the user to the home/main page of the application.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "/home";

    public static final String MESSAGE_SUCCESS = "Returned to home page.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false);
    }
}
