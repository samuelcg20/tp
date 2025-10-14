package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;

/**
 * Lists events to the user by navigating to a new page/screen.
 */
public class ListEventCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Showing events page.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false);
    }
}
