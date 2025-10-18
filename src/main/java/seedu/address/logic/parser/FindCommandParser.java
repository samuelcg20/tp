package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.logic.commands.member.FindMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Split the current args input into at most 2 parts: [type, index]
        String[] argsParts = args.trim().split("\\s+", 2);

        if (argsParts.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Type indicates whether to delete member or event
        String type = argsParts[0];
        String keywords = argsParts[1];
        boolean isInvalidType =
                !type.equalsIgnoreCase("member") && !type.equalsIgnoreCase("event");

        if (isInvalidType) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = keywords.split("\\s+");
        return matchType(type, nameKeywords);
    }

    /**
     * Returns a FindMemberCommand object or FindEventCommand object based on its type
     * @param type Either 'member' or 'event'
     * @param nameKeywords An array of keywords to find
     * @return Corresponding FindCommand subtype object
     */
    public FindCommand matchType(String type, String[] nameKeywords) {
        if (type.equalsIgnoreCase("member")) {
            return new FindMemberCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (type.equalsIgnoreCase("event")) {
            return new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else {
            return null;
        }
    }

}
