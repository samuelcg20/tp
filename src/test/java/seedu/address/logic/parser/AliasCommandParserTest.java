package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.alias.AliasCreateCommand;
import seedu.address.logic.commands.alias.AliasListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

/**
 * Contains unit tests for {@code AliasCommandParser}.
 */
public class AliasCommandParserTest {

    private final AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_emptyArgs_returnsAliasListCommand() throws ParseException {
        // Input is blank (e.g., "alias" with no arguments)
        AliasCommand command = parser.parse("   ");
        assertTrue(command instanceof AliasListCommand);
    }

    @Test
    public void parse_unknownCommandWord_throwsParseException() {
        // Input with the command word not being valid
        assertParseFailure(parser, "unknown add", String.format(Alias.MESSAGE_CONSTRAINS_COMMAND_WORD));
    }

    @Test
    public void parse_validArgs_returnsAliasCreateCommand() {
        // Input with valid command and alias
        Alias alias = new Alias("delete", "r");
        AliasCreateCommand expectedCommand = new AliasCreateCommand(alias);
        assertParseSuccess(parser, "delete r", expectedCommand);
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        // More than two arguments is invalid
        assertParseFailure(parser, "delete rm extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        // Only one argument is invalid
        assertParseFailure(parser, "delete",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithExtraSpaces_returnsAliasCreateCommand() {
        // Multiple spaces should still work
        Alias alias = new Alias("add", "plus");
        AliasCreateCommand expectedCommand = new AliasCreateCommand(alias);
        assertParseSuccess(parser, "   add    plus   ", expectedCommand);
    }

    @Test
    public void parse_tenCharacterAlias_returnsAliasCreateCommand() {
        // Input with the maximum limit of 10 characters
        Alias alias = new Alias("add", "iwanttoadd");
        AliasCreateCommand expectedCommand = new AliasCreateCommand(alias);
        assertParseSuccess(parser, "add iwanttoadd", expectedCommand);
    }

    @Test
    public void parse_nonLowerCaseCommandWordUsedForAlias_returnsAliasCreateCommand() {
        // Input with alias word being the same as command word but not all letters are lower-case
        Alias alias = new Alias("add", "Add");
        AliasCreateCommand expectedCommand = new AliasCreateCommand(alias);
        assertParseSuccess(parser, "add Add", expectedCommand);
    }

    @Test
    public void parse_longAlias_throwsParseException() {
        // Alias word is greater than 10 characters (using 11 characters)
        assertParseFailure(parser, "add iwanttoadds", String.format(Alias.MESSAGE_CONSTRAINS_ALIAS_WORD));
    }

    @Test
    public void parse_aliasWordIsACommandWord_throwsParseException() {
        // Alias word is an existing command word
        assertParseFailure(parser, "add clear", String.format(Alias.MESSAGE_CONSTRAINS_ALIAS_WORD));
    }

    @Test
    public void parse_aliasWordHasNonAlphanumericCharacter_throwsParseException() {
        // Alias word has non-alphanumeric characters
        assertParseFailure(parser, "add adder/", String.format(Alias.MESSAGE_CONSTRAINS_ALIAS_WORD));
    }
}
