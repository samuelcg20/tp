package seedu.address.logic.commands;

/**
 * Lists all persons in the address book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the members/events.\n"
            + "Example: " + COMMAND_WORD + " member/event";
}
