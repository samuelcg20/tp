package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;



/**
 * Finds and lists all events in the address book whose names contain
 * any of the specified keywords. The search is case-insensitive and matches
 * partial keywords within event names.
 *
 * <p>Example usage:
 * <pre>
 *     findeventname conference
 * </pre>
 * will list all events with "conference" appearing anywhere in their name.</p>
 */
public class FindEventNameCommand extends FindCommand {

    private final EventNameContainsKeywordsPredicate predicate;

    /**
     * Constructs a {@code FindEventNameCommand} with the given predicate
     * to filter the event list based on event name keywords.
     *
     * @param predicate the condition used to test each event’s name.
     */
    public FindEventNameCommand(EventNameContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()),
                false,
                true,
                false,
                false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventNameCommand)) {
            return false;
        }

        FindEventNameCommand otherFindEventCommand = (FindEventNameCommand) other;
        return predicate.equals(otherFindEventCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
