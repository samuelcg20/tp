package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists events to the user by navigating to a new page/screen.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "list event";

    public static final String MESSAGE_SUCCESS = "Showing events page.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false, false);
    }
}
