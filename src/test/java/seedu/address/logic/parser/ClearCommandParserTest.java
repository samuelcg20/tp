package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.event.ClearEventCommand;
import seedu.address.logic.commands.member.ClearMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ClearCommandParserTest {

    private final ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_validMember_success() throws Exception {
        ClearCommand command = parser.parse("member");
        assertEquals(ClearMemberCommand.class, command.getClass());
    }

    @Test
    public void parse_validEvent_success() throws Exception {
        ClearCommand command = parser.parse("event");
        assertEquals(ClearEventCommand.class, command.getClass());
    }

    @Test
    public void parse_extraWords_failure() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("member extra"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }

    @Test
    public void parse_emptyArgs_failure() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(""));
        assertEquals(String.format(MESSAGE_INVALID_TYPE, ClearCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }

    @Test
    public void parse_invalidType_failure() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("random"));
        assertEquals(String.format(MESSAGE_INVALID_TYPE, ClearCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }
}
