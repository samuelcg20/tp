package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Year;

public class ParserUtilTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE_NON_DIGITS = "1234abcd";
    private static final String INVALID_PHONE_WHITESPACE = "9123 4567";
    private static final String INVALID_PHONE_START = "71234567";
    private static final String INVALID_PHONE_LENGTH = "123456";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ROLE = "Pr3sident!";
    private static final String INVALID_YEAR = "Year 6";
    private static final String INVALID_DATE = "31-02-2023";
    private static final String INVALID_EVENT_NAME = "";
    private static final String INVALID_VENUE = " ";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "91234567";
    private static final String VALID_EMAIL = "rachel@u.nus.edu";
    private static final String VALID_ROLE = "President";
    private static final String VALID_YEAR = "2";
    private static final String VALID_DATE = "2023-12-25T18:00";
    private static final String VALID_EVENT_NAME = "Christmas Party";
    private static final String VALID_VENUE = "NUS LT27";
    private static final String VALID_ALIAS_COMMAND = "add";
    private static final String VALID_ALIAS_WORD = "a";

    private static final String WHITESPACE = " \t\r\n";

    // ========== Index ==========

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1L)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    // ========== Name ==========

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValue_success() throws Exception {
        Name expected = new Name(VALID_NAME);
        assertEquals(expected, ParserUtil.parseName(VALID_NAME));
        assertEquals(expected, ParserUtil.parseName(WHITESPACE + VALID_NAME + WHITESPACE));
    }

    // ========== Phone ==========

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValues_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_NON_DIGITS));
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_WHITESPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_START));
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_LENGTH));
    }

    @Test
    public void parsePhone_validValue_success() throws Exception {
        Phone expected = new Phone(VALID_PHONE);
        assertEquals(expected, ParserUtil.parsePhone(VALID_PHONE));
        assertEquals(expected, ParserUtil.parsePhone(WHITESPACE + VALID_PHONE + WHITESPACE));
    }

    // ========== Email ==========

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValue_success() throws Exception {
        Email expected = new Email(VALID_EMAIL);
        assertEquals(expected, ParserUtil.parseEmail(VALID_EMAIL));
        assertEquals(expected, ParserUtil.parseEmail(WHITESPACE + VALID_EMAIL + WHITESPACE));
    }

    // ========== Year ==========

    @Test
    public void parseYear_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseYear(null));
    }

    @Test
    public void parseYear_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
    }

    @Test
    public void parseYear_validValue_success() throws Exception {
        Year expected = new Year(VALID_YEAR);
        assertEquals(expected, ParserUtil.parseYear(VALID_YEAR));
        assertEquals(expected, ParserUtil.parseYear(WHITESPACE + VALID_YEAR + WHITESPACE));
    }

    // ========== Role ==========

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValue_success() throws Exception {
        Role expected = new Role(VALID_ROLE);
        assertEquals(expected, ParserUtil.parseRole(VALID_ROLE));
        assertEquals(expected, ParserUtil.parseRole(WHITESPACE + VALID_ROLE + WHITESPACE));
    }

    // ========== Date ==========

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValue_success() throws Exception {
        Date expected = new Date(VALID_DATE);
        assertEquals(expected, ParserUtil.parseDate(VALID_DATE));
        assertEquals(expected, ParserUtil.parseDate(WHITESPACE + VALID_DATE + WHITESPACE));
    }

    // ========== EventName ==========

    @Test
    public void parseEventName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEventName(null));
    }

    @Test
    public void parseEventName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventName(INVALID_EVENT_NAME));
    }

    @Test
    public void parseEventName_validValue_success() throws Exception {
        EventName expected = new EventName(VALID_EVENT_NAME);
        assertEquals(expected, ParserUtil.parseEventName(VALID_EVENT_NAME));
        assertEquals(expected, ParserUtil.parseEventName(WHITESPACE + VALID_EVENT_NAME + WHITESPACE));
    }

    // ========== Venue ==========

    @Test
    public void parseVenue_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseVenue(null));
    }

    @Test
    public void parseVenue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseVenue(INVALID_VENUE));
    }

    @Test
    public void parseVenue_validValue_success() throws Exception {
        Venue expected = new Venue(VALID_VENUE);
        assertEquals(expected, ParserUtil.parseVenue(VALID_VENUE));
        assertEquals(expected, ParserUtil.parseVenue(WHITESPACE + VALID_VENUE + WHITESPACE));
    }

    // ========== Alias ==========

    @Test
    public void parseAlias_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAlias(null, VALID_ALIAS_WORD));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAlias(VALID_ALIAS_COMMAND, null));
    }

    @Test
    public void parseAlias_invalidValues_throwsParseException() {
        // invalid command: contains special character
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias("add*", "a"));

        // invalid alias word: empty string
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias("add", ""));
    }
}
