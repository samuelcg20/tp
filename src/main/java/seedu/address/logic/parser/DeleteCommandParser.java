package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.member.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String[] argsParts = args.trim().split("\\s+");

        if (argsParts.length !=2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String type = argsParts[0];
        String indexToDelete = argsParts[1];
        boolean isInValidType = !type.equalsIgnoreCase("member") && !type.equalsIgnoreCase("event");

        if (isInValidType) {
            throw new ParseException("Invalid type: must be 'member' or 'event'.\n" + DeleteCommand.MESSAGE_USAGE);
        }

        try {
            Index index = ParserUtil.parseIndex(indexToDelete);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
