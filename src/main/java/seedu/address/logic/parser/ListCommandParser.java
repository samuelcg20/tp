package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.logic.commands.member.ListMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParser implements Parser<ListCommand> {

    public ListCommand parse(String args) throws ParseException {
        // Split the current args input into at most 2 parts: [member/event, unnecessary information]
        String[] argsParts = args.trim().split("\\s+");

        if (argsParts.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
        }

        // Type indicates whether to list members or events
        String type = argsParts[0];
        boolean isInvalidType = !type.equalsIgnoreCase("member")
                && !type.equalsIgnoreCase("event");

        if (isInvalidType) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TYPE, ListCommand.MESSAGE_USAGE));
        }

        return matchType(type);
    }

    /**
     * Checks if it is member or event command
     * @param type Member or Event
     * @return ListMemberCommand or ListEventCommand
     */
    public ListCommand matchType(String type) {
        if (type.equalsIgnoreCase("member")) {
            return new ListMemberCommand();
        } else if (type.equalsIgnoreCase("event")) {
            return new ListEventCommand();
        } else {
            return null;
        }
    }
}
