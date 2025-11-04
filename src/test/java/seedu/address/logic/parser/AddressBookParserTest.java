package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.FindEventNameCommand;
import seedu.address.logic.commands.member.AddMemberCommand;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.logic.commands.member.EditMemberCommand;
import seedu.address.logic.commands.member.FindMemberNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditMemberDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    // ==================== MEMBER COMMANDS ====================

    @Test
    public void parseCommand_addMember() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddMemberCommand(person), command);
    }

    @Test
    public void parseCommand_editMember() throws Exception {
        Person person = new PersonBuilder().build();
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(
                EditCommand.COMMAND_WORD + " member " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PersonUtil.getEditPersonDescriptorDetails(descriptor)
        );
        assertEquals(new EditMemberCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_deleteMember() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " member " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new DeleteMemberCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_listMember() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " member") instanceof ListCommand);
    }

    // ==================== EVENT COMMANDS ====================

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(EventUtil.getAddCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditCommand command = (EditCommand) parser.parseCommand(
                EditCommand.COMMAND_WORD + " event " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + EventUtil.getEditEventDescriptorDetails(descriptor)
        );
        assertEquals(new EditEventCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " event " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new DeleteEventCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_listEvent() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " event") instanceof ListCommand);
    }

    // ==================== GENERIC COMMANDS ====================

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " member") instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " event") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_findMemberByName() throws Exception {
        // Example: find member by name "Alice Bob"
        List<String> keywords = Arrays.asList("Alice", "Bob");
        String commandText = FindCommand.COMMAND_WORD + " member n/" + String.join(" ", keywords);

        FindCommand command = (FindCommand) parser.parseCommand(commandText);

        // Assuming FindMemberNameCommand takes NameContainsKeywordsPredicate
        assertEquals(new FindMemberNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findEventByName() throws Exception {
        // Example: find event by name "Launch Party"
        List<String> keywords = Arrays.asList("Launch", "Party");
        String commandText = FindCommand.COMMAND_WORD + " event n/" + String.join(" ", keywords);

        FindCommand command = (FindCommand) parser.parseCommand(commandText);

        // Assuming FindEventNameCommand takes EventNameContainsKeywordsPredicate
        assertEquals(new FindEventNameCommand(new EventNameContainsKeywordsPredicate(keywords)), command);
    }

    // ==================== EXCEPTION TESTS ====================

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
