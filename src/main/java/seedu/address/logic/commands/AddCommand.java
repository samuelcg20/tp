package seedu.address.logic.commands;

/**
 * Adds a member or event to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member or event to the address book. "
            + "Parameters: TYPE (either 'member' or 'event') "
            + "[n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] "
            + "[t/TAG]... "
            + "[n/EVENTNAME] [d/DATE] [v/VENUE] "
            + "Example: " + COMMAND_WORD
            + " member n/John Doe p/98765432";
}
