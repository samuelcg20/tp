package seedu.address.logic.commands;

/**
 * Creates an alias for existing command words.
 */
public abstract class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Creates an alias for all command words\n"
            + "Parameters: COMMAND_WORD, ALIAS_WORD (only 1 word)\n"
            + "Example: " + COMMAND_WORD + " delete " + " rm";

    public static final String MESSAGE_ADD_ALIAS_SUCCESS = "Added alias: %1$s";

}
