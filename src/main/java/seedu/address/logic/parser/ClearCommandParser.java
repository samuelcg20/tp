package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.event.ClearEventCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.member.ClearMemberCommand;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.logic.commands.member.ListMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ClearCommandParser implements Parser<ClearCommand> {

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
     * @return ListMemberCommand or ListEventCommand
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
