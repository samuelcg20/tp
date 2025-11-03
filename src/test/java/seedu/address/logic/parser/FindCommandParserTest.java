package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.event.FindEventDateCommand;
import seedu.address.logic.commands.event.FindEventNameCommand;
import seedu.address.logic.commands.member.FindMemberNameCommand;
import seedu.address.logic.commands.member.FindMemberYearCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.DateContainsKeywordsPredicate;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.YearContainsKeywordsPredicate;

/**
 * Extensive tests for {@code FindCommandParser}.
 *
 * Exercises parsing for:
 *  - member: n/ (name), y/ (year)
 *  - event:   n/ (name), d/ (date partials validated by Date.isValidPartialDate)
 *
 * Also exercises invalid inputs, whitespace handling, duplicate/multiple prefixes,
 * and the parser's specific invalid-partial-date error message.
 */
public class FindCommandParserTest {

    private FindCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new FindCommandParser();
    }

    // ---------------------- happy paths: member ----------------------

    @Test
    public void parse_validMemberName_returnsFindMemberNameCommand() throws Exception {
        FindCommand expectedCommand =
                new FindMemberNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertEquals(expectedCommand, parser.parse("member n/Alice Bob"));
    }

    @Test
    public void parse_validMemberYear_returnsFindMemberYearCommand() throws Exception {
        FindCommand expectedCommand =
                new FindMemberYearCommand(new YearContainsKeywordsPredicate(Arrays.asList("1", "2")));
        assertEquals(expectedCommand, parser.parse("member y/1 2"));
    }

    @Test
    public void parse_memberName_withExtraWhitespace_returnsFindMemberNameCommand() throws Exception {
        // extra spaces between and around keywords should be handled
        FindCommand expectedCommand =
                new FindMemberNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertEquals(expectedCommand, parser.parse("member   n/  Alice   Bob  "));
    }

    // ---------------------- happy paths: event name ----------------------

    @Test
    public void parse_validEventName_returnsFindEventNameCommand() throws Exception {
        FindCommand expectedCommand =
                new FindEventNameCommand(
                        new EventNameContainsKeywordsPredicate(Arrays.asList("Ideate", "Hackathon")));
        assertEquals(expectedCommand, parser.parse("event n/Ideate Hackathon"));
    }

    @Test
    public void parse_eventName_withLeadingTrailingSpaces_returnsFindEventNameCommand() throws Exception {
        FindCommand expectedCommand =
                new FindEventNameCommand(
                        new EventNameContainsKeywordsPredicate(Arrays.asList("Ideate", "Hackathon")));
        assertEquals(expectedCommand, parser.parse("event   n/  Ideate   Hackathon  "));
    }

    // ---------------------- happy paths: event date (partial dates) ----------------------

    @Test
    public void parse_validEventDateYear_returnsFindEventDateCommand() throws Exception {
        // Accepts partial year "2025"
        FindCommand expectedCommand =
                new FindEventDateCommand(new DateContainsKeywordsPredicate(Arrays.asList("2025")));
        assertEquals(expectedCommand, parser.parse("event d/2025"));
    }

    @Test
    public void parse_validEventDateYearMonth_returnsFindEventDateCommand() throws Exception {
        // Accepts partial year-month "2025-11"
        FindCommand expectedCommand =
                new FindEventDateCommand(new DateContainsKeywordsPredicate(Arrays.asList("2025-11")));
        assertEquals(expectedCommand, parser.parse("event d/2025-11"));
    }

    @Test
    public void parse_validEventDateFullDateTime_returnsFindEventDateCommand() throws Exception {
        // Accepts full datetime per Date.FORMATTER
        FindCommand expectedCommand =
                new FindEventDateCommand(new DateContainsKeywordsPredicate(
                        Arrays.asList("2025-12-15T14:00")));
        assertEquals(expectedCommand, parser.parse("event d/2025-12-15T14:00"));
    }

    @Test
    public void parse_eventDate_multipleKeywords_returnsFindEventDateCommand() throws Exception {
        FindCommand expectedCommand =
                new FindEventDateCommand(new DateContainsKeywordsPredicate(Arrays.asList("2025-12", "2026")));
        assertEquals(expectedCommand, parser.parse("event d/2025-12 2026"));
    }

    @Test
    public void parse_eventDate_withWhitespaceAroundKeywords_returnsFindEventDateCommand() throws Exception {
        FindCommand expectedCommand =
                new FindEventDateCommand(new DateContainsKeywordsPredicate(Arrays.asList("2025-12")));
        assertEquals(expectedCommand, parser.parse("event d/  2025-12  "));
    }

    // ---------------------- invalid/edge cases: general ----------------------

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("member"));
        assertThrows(ParseException.class, () -> parser.parse("event"));
    }

    @Test
    public void parse_invalidType_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("random n/test"));
    }

    @Test
    public void parse_missingPrefixAfterMember_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("member Alice"));
    }

    @Test
    public void parse_missingPrefixAfterEvent_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("event Ideate"));
    }

    @Test
    public void parse_emptyKeywordAfterPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("member n/   "));
        assertThrows(ParseException.class, () -> parser.parse("event n/  "));
    }

    // ---------------------- invalid/edge cases: prefix collisions & wrong prefixes ----------------------

    @Test
    public void parse_multiplePrefixesForMember_throwsParseException() {
        // n/ followed by y/ in same argument -> should be rejected as multiple prefixes for member
        assertThrows(ParseException.class, () -> parser.parse("member n/Alice y/1"));
    }

    @Test
    public void parse_multiplePrefixesForEvent_throwsParseException() {
        // n/ followed by d/ in same argument -> multiple prefixes for event
        assertThrows(ParseException.class, () -> parser.parse("event n/Ideate d/2025"));
    }

    @Test
    public void parse_memberWithEventPrefix_throwsParseException() {
        // using event prefix d/ in member command
        assertThrows(ParseException.class, () -> parser.parse("member d/2025"));
    }

    @Test
    public void parse_eventWithMemberPrefix_throwsParseException() {
        // using member prefix y/ in event command
        assertThrows(ParseException.class, () -> parser.parse("event y/2"));
    }

    // ---------------------- invalid partial dates (parser-level validation) ----------------------

    @Test
    public void parse_eventDate_invalidPartialDay_throwsParseException() {
        // 2025-02-30 is an invalid date (Feb 30)
        ParseException ex = assertThrows(ParseException.class,
                () -> parser.parse("event d/2025-02-30"));
        // parser uses a custom message for invalid partial date — assert it appears
        String expectedMsgStart = "Please enter a valid partial date.";
        assertEquals(true, ex.getMessage().startsWith(expectedMsgStart));
    }

    @Test
    public void parse_eventDate_invalidTime_throwsParseException() {
        // invalid time "14:60"
        ParseException ex = assertThrows(ParseException.class,
                () -> parser.parse("event d/2025-12-15T14:60"));
        String expectedMsgStart = "Please enter a valid partial date.";
        assertEquals(true, ex.getMessage().startsWith(expectedMsgStart));
    }

    @Test
    public void parse_eventDate_malformedPartial_noAccepted_if_invalidPartial_throwsParseException() {
        // "2025-12-15T14:0" is malformed (minutes not two digits) and should be rejected by parser validation
        ParseException ex = assertThrows(ParseException.class,
                () -> parser.parse("event d/2025-12-15T14:0"));
        String expectedMsgStart = "Please enter a valid partial date.";
        assertEquals(true, ex.getMessage().startsWith(expectedMsgStart));
    }

    // ---------------------- boundary tests for date validation ----------------------

    @Test
    public void parse_eventDate_minimumYearOnlyAccepted() throws Exception {
        // single year "0001" is syntactically valid for Year.parse; ensure parser accepts it
        FindCommand expected = new FindEventDateCommand(
                new DateContainsKeywordsPredicate(Arrays.asList("0001")));
        assertEquals(expected, parser.parse("event d/0001"));
    }

    @Test
    public void parse_eventDate_yearMonthBoundary_acceptedOrRejectedAppropriately() {
        // "2025-00" month zero is invalid; expect ParseException
        assertThrows(ParseException.class, () -> parser.parse("event d/2025-00"));
        // "2025-13" month 13 invalid
        assertThrows(ParseException.class, () -> parser.parse("event d/2025-13"));
    }

    // ---------------------- miscellaneous: ensure split trimming behaviour ----------------------

    @Test
    public void parse_eventDate_multipleSpacesBetweenKeywords_handledCorrectly() throws Exception {
        FindCommand expectedCommand =
                new FindEventDateCommand(
                        new DateContainsKeywordsPredicate(Arrays.asList("2025-12", "2026-01")));
        assertEquals(expectedCommand, parser.parse("event d/2025-12    2026-01"));
    }

    @Test
    public void parse_eventDate_keywordWithLeadingTrailingSpaces_isTrimmedBeforeSplit() throws Exception {
        FindCommand expectedCommand =
                new FindEventDateCommand(new DateContainsKeywordsPredicate(Arrays.asList("2025-12")));
        assertEquals(expectedCommand, parser.parse("event d/   2025-12   "));
    }

    // ---------------------- invalid overall usage messages (coverage for format/type errors) ----------------------

    @Test
    public void parse_incorrectTopLevelArgs_throwsParseException() {
        // missing type or keywords handled earlier; verify "event" without type handled
        assertThrows(ParseException.class, () -> parser.parse("event"));
        assertThrows(ParseException.class, () -> parser.parse("member"));
    }
}
