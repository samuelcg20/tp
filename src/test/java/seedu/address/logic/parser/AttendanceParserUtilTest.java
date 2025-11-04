package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests for {@link AttendanceParserUtil}.
 */
public class AttendanceParserUtilTest {

    private static final String USAGE = MarkCommand.MESSAGE_USAGE;

    @Test
    public void parseIndexes_validArgs_success() throws ParseException {
        AttendanceParserUtil.AttendanceIndexes indexes = AttendanceParserUtil.parseIndexes(" m/2 e/3 ", USAGE);
        assertEquals(Index.fromOneBased(2), indexes.getMemberIndex());
        assertEquals(Index.fromOneBased(3), indexes.getEventIndex());
    }

    @Test
    public void parseIndexes_missingMemberPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                AttendanceParserUtil.parseIndexes(" e/1", USAGE));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, USAGE), exception.getMessage());
    }

    @Test
    public void parseIndexes_missingEventPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                AttendanceParserUtil.parseIndexes(" m/1", USAGE));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, USAGE), exception.getMessage());
    }

    @Test
    public void parseIndexes_withPreamble_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                AttendanceParserUtil.parseIndexes("preamble m/1 e/1", USAGE));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, USAGE), exception.getMessage());
    }

    @Test
    public void parseIndexes_duplicateMemberPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                AttendanceParserUtil.parseIndexes(" m/1 m/2 e/1", USAGE));
        assertEquals(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER), exception.getMessage());
    }

    @Test
    public void parseIndexes_duplicateEventPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                AttendanceParserUtil.parseIndexes(" m/1 e/1 e/2", USAGE));
        assertEquals(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT), exception.getMessage());
    }

    @Test
    public void parseIndexes_invalidMemberIndex_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                AttendanceParserUtil.parseIndexes(" m/a e/1", USAGE));
        assertEquals(ParserUtil.MESSAGE_INVALID_INDEX, exception.getMessage());
    }

    @Test
    public void parseIndexes_invalidEventIndex_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () ->
                AttendanceParserUtil.parseIndexes(" m/1 e/0", USAGE));
        assertEquals(ParserUtil.MESSAGE_INVALID_INDEX, exception.getMessage());
    }
}
