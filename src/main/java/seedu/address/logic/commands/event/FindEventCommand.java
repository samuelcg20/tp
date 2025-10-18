package seedu.address.logic.commands.event;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.member.FindMemberCommand;
import seedu.address.model.Model;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

public class FindEventCommand extends FindCommand {

    private final EventNameContainsKeywordsPredicate predicate;

    /**
     * Creates a FindMemberCommand to find the specified {@code NameContainsKeywordsPredicate}
     */
    public FindEventCommand(EventNameContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventCommand)) {
            return false;
        }

        FindEventCommand otherFindEventCommand = (FindEventCommand) other;
        return predicate.equals(otherFindEventCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}