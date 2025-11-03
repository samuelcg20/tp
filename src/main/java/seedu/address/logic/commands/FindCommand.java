package seedu.address.logic.commands;

/**
 * Finds and lists all persons or events based on the type passed in
 * address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds members or events matching the given keywords.\n"
            + "Parameters: TYPE(either member or event), PREFIX(based on type)/keywords...\n"
            + "Example: " + COMMAND_WORD + " member n/alice Bob\n"
            + "Example: " + COMMAND_WORD + " member y/1\n"
            + "Example: " + COMMAND_WORD + " event n/ideate\n"
            + "Example: " + COMMAND_WORD + " event d/2025-12-12T14:00";

}
