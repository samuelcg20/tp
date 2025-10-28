package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.member.ListMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validMember_success() throws Exception {
        ListCommand command = parser.parse("member");
        assertEquals(ListMemberCommand.class, command.getClass());
    }

    @Test
    public void parse_validEvent_success() throws Exception {
        ListCommand command = parser.parse("event");
        assertEquals(ListEventCommand.class, command.getClass());
    }

    @Test
    public void parse_extraWords_failure() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("member extra"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }

    @Test
    public void parse_emptyArgs_failure() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(""));
        // Since your parser expects exactly one arg and checks for invalid type afterwards,
        // empty string will trigger invalid format if argsParts length != 1.
        assertEquals(String.format(MESSAGE_INVALID_TYPE, ListCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }

    @Test
    public void parse_invalidType_failure() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("random"));
        assertEquals(String.format(MESSAGE_INVALID_TYPE, ListCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }
}
