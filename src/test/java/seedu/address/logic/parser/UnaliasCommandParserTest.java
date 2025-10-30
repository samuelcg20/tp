package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND_UNALIAS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.UnaliasCommand;

/**
 * Contains unit tests for {@code UnaliasCommandParser}.
 */
public class UnaliasCommandParserTest {

    private final UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    public void parse_validCommandWord_returnsUnaliasCommand() {
        UnaliasCommand expectedCommand = new UnaliasCommand("add");
        assertParseSuccess(parser, "add", expectedCommand);
    }

    @Test
    public void parse_upperCaseValidCommandWord_throwsParseException() {
        // Input is a valid command word but not all letters are in lower-case
        assertParseFailure(parser, "Add",
                String.format(MESSAGE_UNKNOWN_COMMAND_UNALIAS, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validAllKeyword_returnsUnaliasCommand() {
        // The special "all" keyword should work regardless of case
        UnaliasCommand expectedCommand = new UnaliasCommand("all");
        assertParseSuccess(parser, "ALL", expectedCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        assertParseFailure(parser, "add extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCommandWord_throwsParseException() {
        // Something not in Alias.SET_OF_COMMANDS or "all"
        assertParseFailure(parser, "foobar",
                String.format(MESSAGE_UNKNOWN_COMMAND_UNALIAS, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validCommandWordWithSpaces_returnsUnaliasCommand() {
        UnaliasCommand expectedCommand = new UnaliasCommand("delete");
        assertParseSuccess(parser, "   delete   ", expectedCommand);
    }
}

