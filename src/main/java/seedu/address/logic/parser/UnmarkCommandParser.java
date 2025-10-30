package seedu.address.logic.parser;

import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkCommand object.
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkCommand parse(String args) throws ParseException {
        AttendanceParserUtil.AttendanceIndexes indexes =
                AttendanceParserUtil.parseIndexes(args, UnmarkCommand.MESSAGE_USAGE);
        return new UnmarkCommand(indexes.getMemberIndex(), indexes.getEventIndex());
    }
}
