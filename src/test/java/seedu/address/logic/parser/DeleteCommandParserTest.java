package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();
    private final int INVALID_INDEX = -1;
    private final int VALID_INDEX = 1;

    @Test
    public void parse_validMember_success() throws ParseException {
        String userInput = "member 1";
        DeleteMemberCommand expectedCommand = new DeleteMemberCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validEvent_success() throws ParseException {
        String userInput = "event 1";
        DeleteEventCommand expectedCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingArguments_failure() {
        // Missing index
        assertParseFailure(parser, "member",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Missing type
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "member " + INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "event " + INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidType_failure() {
        assertParseFailure(parser, "invalidType " + VALID_INDEX,
                String.format(MESSAGE_INVALID_TYPE, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        assertParseFailure(parser, "member 1 extraArg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
