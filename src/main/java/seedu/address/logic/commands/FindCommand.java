package seedu.address.logic.commands;

/**
 * Finds and lists all persons or events based on the type passed in
 * address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons if find member is entered or all events if find event whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TYPE KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " member alice bob charlie\n"
            + "Example: " + COMMAND_WORD + " event ideate";

}
