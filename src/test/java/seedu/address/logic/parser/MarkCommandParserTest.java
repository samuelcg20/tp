package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.ParserUtil;

/**
 * Tests for {@link MarkCommandParser}.
 */
public class MarkCommandParserTest {

    private final MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        MarkCommand expectedCommand = new MarkCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        assertParseSuccess(parser, "  m/1 e/2  ", expectedCommand);
    }

    @Test
    public void parse_missingMemberPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " e/1", expectedMessage);
    }

    @Test
    public void parse_missingEventPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " m/1", expectedMessage);
    }

    @Test
    public void parse_invalidMemberIndex_throwsParseException() {
        assertParseFailure(parser, " m/a e/1", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidEventIndex_throwsParseException() {
        assertParseFailure(parser, " m/1 e/0", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_duplicateMemberPrefix_throwsParseException() {
        assertParseFailure(parser, " m/1 m/2 e/1",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER));
    }

    @Test
    public void parse_duplicateEventPrefix_throwsParseException() {
        assertParseFailure(parser, " m/1 e/1 e/2",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT));
    }

    @Test
    public void parse_withPreamble_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "preamble m/1 e/1", expectedMessage);
    }
}
