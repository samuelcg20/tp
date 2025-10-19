package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.event.ClearEventCommand;
import seedu.address.logic.commands.member.ClearMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearMemberCommand/ ClearEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        // Split the current args input into at most 2 parts: [member/event, unnecessary information]
        String[] argsParts = args.trim().split("\\s+");

        if (argsParts.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }

        // Type indicates whether to list members or events
        String type = argsParts[0];
        boolean isInvalidType = !type.equalsIgnoreCase("member")
                && !type.equalsIgnoreCase("event");

        if (isInvalidType) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TYPE, ClearCommand.MESSAGE_USAGE));
        }

        return matchType(type);
    }

    /**
     * Checks if it is member or event command
     * @param type Member or Event
     * @return ClearMemberCommand or ClearEventCommand
     */
    public ClearCommand matchType(String type) {
        if (type.equalsIgnoreCase("member")) {
            return new ClearMemberCommand();
        } else if (type.equalsIgnoreCase("event")) {
            return new ClearEventCommand();
        } else {
            return null;
        }
    }

}
