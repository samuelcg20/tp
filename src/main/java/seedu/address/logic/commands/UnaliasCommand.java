package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Removes alias for existing command words, if any.
 */
public class UnaliasCommand extends Command {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the existing alias for the stated command word .\n"
            + "Parameters: COMMAND_WORD\n"
            + "Example: " + COMMAND_WORD + " add";

    public static final String MESSAGE_SUCCESS = "Alias for '%1$s' has been removed successfully.";
    public static final String MESSAGE_SUCCESS_ALL = "All stored aliases are removed successfully.";
    public static final String MESSAGE_NOT_FOUND = "No alias found for '%1$s' to remove.";
    public static final String MESSAGE_NOT_FOUND_ALL = "No alias found to remove all";

    private final String commandWordToRemove;

    public UnaliasCommand(String commandWordToRemove) {
        this.commandWordToRemove = commandWordToRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if ((commandWordToRemove.equalsIgnoreCase("all"))) {
            return executeAllCommand(model);
        } else {
            return executeOtherCommand(model);
        }
    }

    private CommandResult executeAllCommand(Model model) throws CommandException {

        if (model.isAliasBookEmpty()) {
            throw new CommandException(MESSAGE_NOT_FOUND_ALL);
        }

        model.clearAllAliases();
        return new CommandResult(MESSAGE_SUCCESS_ALL);
    }

    private CommandResult executeOtherCommand(Model model) throws CommandException {

        if (!model.hasCommand(commandWordToRemove)) {
            throw new CommandException(String.format(MESSAGE_NOT_FOUND, commandWordToRemove));
        }

        model.removeAlias(commandWordToRemove);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandWordToRemove));
    }

}
