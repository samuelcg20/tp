package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

public class UnaliasCommand extends Command{

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the existing alias for the stated command word .\n"
            + "Parameters: COMMAND_WORD\n"
            + "Example: " + COMMAND_WORD + " add";

    public static final String MESSAGE_SUCCESS = "Alias for '%1$s' has been removed successfully.";
    public static final String MESSAGE_NOT_FOUND = "No alias found for '%1$s' to remove.";

    private final String commandWordToRemove;

    public UnaliasCommand(String commandWordToRemove) {
        this.commandWordToRemove = commandWordToRemove;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!model.hasCommand(commandWordToRemove)) {
            return new CommandResult(String.format(MESSAGE_NOT_FOUND, commandWordToRemove));
        }

        model.removeExistingAlias(commandWordToRemove);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandWordToRemove));
    }

}
