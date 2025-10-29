package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.member.AddMemberCommand;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * Tests for AddCommandParser.
 * Covers both member and event parsing behaviour.
 */
public class AddCommandParserTest {

    private final AddCommandParser parser = new AddCommandParser();

    // ----------------------------
    // MEMBER TESTS
    // ----------------------------

    @Test
    public void parse_member_allFieldsPresent_success() throws Exception {
        String userInput = "member n/Alice Tan p/98765432 e/alice@email.com y/2 t/Exco t/Logistics";

        Person expectedPerson = new Person(
                new Name("Alice Tan"),
                new Phone("98765432"),
                new Email("alice@email.com"),
                new Year("2"),
                Set.of(new Tag("Exco"), new Tag("Logistics"))
        );

        AddMemberCommand expectedCommand = new AddMemberCommand(expectedPerson);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_member_missingOptionalFields_success() throws Exception {
        String userInput = "member n/Bob Lee p/91234567 e/bob@email.com y/1";

        Person expectedPerson = new Person(
                new Name("Bob Lee"),
                new Phone("91234567"),
                new Email("bob@email.com"),
                new Year("1"),
                Set.of() // no tags
        );

        AddMemberCommand expectedCommand = new AddMemberCommand(expectedPerson);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_member_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // Missing name prefix
        assertParseFailure(parser, "member p/91234567 e/bob@email.com y/2", expectedMessage);

        // Missing year
        assertParseFailure(parser, "member n/Bob p/91234567 e/bob@email.com", expectedMessage);

        // Empty args after 'member'
        assertParseFailure(parser, "member ", expectedMessage);
    }

    @Test
    public void parse_member_invalidValues_failure() {
        // Invalid email
        assertParseFailure(parser,
                "member n/Bob p/91234567 e/invalidemail y/2",
                Email.MESSAGE_CONSTRAINTS);

        // Invalid phone
        assertParseFailure(parser,
                "member n/Bob p/abc e/bob@email.com y/2",
                Phone.MESSAGE_CONSTRAINTS_NUMBER);

        // Invalid year
        assertParseFailure(parser,
                "member n/Bob p/91234567 e/bob@email.com y/notANumber",
                Year.MESSAGE_CONSTRAINTS);
    }

    // ----------------------------
    // EVENT TESTS
    // ----------------------------

    @Test
    public void parse_event_allFieldsPresent_success() throws Exception {
        String userInput = "event n/Orientation d/2025-08-15 l/Hall 1";

        Event expectedEvent = new Event(
                new EventName("Orientation"),
                new Date("2025-08-15T00:00"),
                new Venue("Hall 1")
        );

        AddEventCommand expectedCommand = new AddEventCommand(expectedEvent);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_event_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // Missing name
        assertParseFailure(parser, "event d/2025-08-15 l/Hall 1", expectedMessage);

        // Missing date
        assertParseFailure(parser, "event n/Orientation l/Hall 1", expectedMessage);

        // Missing location
        assertParseFailure(parser, "event n/Orientation d/2025-08-15", expectedMessage);
    }

    @Test
    public void parse_event_invalidValues_failure() {
        // Invalid date
        assertParseFailure(parser,
                "event n/Orientation d/invalidDate l/Hall 1",
                Date.MESSAGE_CONSTRAINTS);

        // Invalid venue
        assertParseFailure(parser,
                "event n/Orientation d/2025-08-15 l/",
                Venue.MESSAGE_CONSTRAINTS);
    }

    // ----------------------------
    // GENERAL / INVALID INPUT TESTS
    // ----------------------------

    @Test
    public void parse_invalidCommandType_failure() {
        assertParseFailure(parser,
                "club n/Test p/123",
                String.format(MESSAGE_INVALID_TYPE, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser,
                "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingType_failure() {
        assertParseFailure(parser,
                "n/Bob p/91234567 e/bob@email.com y/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

