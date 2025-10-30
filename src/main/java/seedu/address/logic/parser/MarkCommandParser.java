package seedu.address.logic.parser;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkCommand object.
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns a MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        AttendanceParserUtil.AttendanceIndexes indexes =
                AttendanceParserUtil.parseIndexes(args, MarkCommand.MESSAGE_USAGE);
        return new MarkCommand(indexes.getMemberIndex(), indexes.getEventIndex());
    }
}
