package seedu.address.logic.commands.alias;

import java.util.List;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;

/**
 * Displays the list view of aliased stored in the message box
 */
public class AliasListCommand extends AliasCommand {

    public static final String MESSAGE_SUCCESS = "Here are your saved aliases:\n%s";
    public static final String MESSAGE_EMPTY = "You have no saved aliases.";

    @Override
    public CommandResult execute(Model model) {
        List<Alias> aliases = model.getAliasList();

        if (aliases.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        StringBuilder sb = new StringBuilder();
        for (Alias alias : aliases) {
            sb.append(alias.toString()).append("\n");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, sb));
    }

}
