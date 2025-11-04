package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.*;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

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
import seedu.address.model.person.Role;
import seedu.address.model.person.Year;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {

    private final AddCommandParser parser = new AddCommandParser();

    // ========================= MEMBER TESTS =========================

    @Test
    public void parse_addMemberAllFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withRole(VALID_ROLE_TREASURER).build();
        assertParseSuccess(parser,
                "member " + PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + YEAR_DESC_BOB + ROLE_DESC_TREASURER,
                new AddMemberCommand(expectedPerson));
    }

    @Test
    public void parse_addMemberOptionalFieldsMissing_success() {
        Person expectedPerson = new PersonBuilder(AMY).withRole(VALID_ROLE_TREASURER).build();
        assertParseSuccess(parser,
                "member " + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + YEAR_DESC_AMY + ROLE_DESC_TREASURER,
                new AddMemberCommand(expectedPerson));
    }

    @Test
    public void parse_addMemberDuplicateFields_failure() {
        String validPerson = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + YEAR_DESC_BOB + ROLE_DESC_TREASURER;
        assertParseFailure(parser, "member " + NAME_DESC_AMY + validPerson,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME));
    }

    @Test
    public void parse_addMemberMissingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_MEMBER);

        // Missing name
        assertParseFailure(parser, "member " + PHONE_DESC_BOB + EMAIL_DESC_BOB + YEAR_DESC_BOB
                + ROLE_DESC_TREASURER, expectedMessage);

        // Missing role
        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + YEAR_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_addMemberInvalidValues_failure() {
        assertParseFailure(parser, "member " + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + YEAR_DESC_BOB + ROLE_DESC_TREASURER, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "member " + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + YEAR_DESC_BOB + ROLE_DESC_TREASURER, Phone.MESSAGE_CONSTRAINTS_NUMBER);

        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + YEAR_DESC_BOB + ROLE_DESC_TREASURER, Email.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_YEAR_DESC + ROLE_DESC_TREASURER, Year.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "member " + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + YEAR_DESC_BOB + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS);
    }

    // ========================= EVENT TESTS =========================

    @Test
    public void parse_addEventAllFieldsPresent_success() {
        Event expectedEvent = new EventBuilder()
                .withName("Tech Conference")
                .withDate("2025-12-01T18:00")
                .withLocation("NUS UTown")
                .build();

        assertParseSuccess(parser,
                "event n/Tech Conference d/2025-12-01T18:00 v/NUS UTown",
                new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_addEventMissingCompulsoryFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_EVENT);

        // Missing name
        assertParseFailure(parser, "event d/2025-12-01 l/NUS UTown", expectedMessage);

        // Missing date
        assertParseFailure(parser, "event n/Tech Conference l/NUS UTown", expectedMessage);

        // Missing location
        assertParseFailure(parser, "event n/Tech Conference d/2025-12-01", expectedMessage);
    }

    @Test
    public void parse_addEventDuplicatePrefixes_failure() {
        String validEvent = "n/Tech Conference d/2025-12-01T18:00 v/NUS UTown";
        assertParseFailure(parser, "event n/Launch Party " + validEvent,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME));

        assertParseFailure(parser, "event d/2024-12-01T18:00 " + validEvent,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DATE));

        assertParseFailure(parser, "event v/Suntec City " + validEvent,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_LOCATION));
    }

    @Test
    public void parse_addEventInvalidValues_failure() {
        assertParseFailure(parser, "event n/!@# d/2025-12-01T18:00 v/NUS",
                EventName.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "event n/TechFest d/invalid-date v/NUS",
                Date.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "event n/TechFest d/2025-12-01T18:00 v/    ",
                Venue.MESSAGE_CONSTRAINTS);
    }

    // ========================= GENERAL PARSER TESTS =========================

    @Test
    public void parse_invalidType_failure() {
        assertParseFailure(parser, "unknown n/abc", String.format(MESSAGE_INVALID_TYPE,
                AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgumentsAfterType_failure() {
        assertParseFailure(parser, "member", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE_MEMBER));
        assertParseFailure(parser, "event", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE_EVENT));
    }
}
