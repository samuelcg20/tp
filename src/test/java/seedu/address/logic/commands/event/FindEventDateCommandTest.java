package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.HACKATHON;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.SEMINAR;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.DateContainsKeywordsPredicate;

/**
 * Integration tests (interaction with the Model) for {@code FindEventDateCommand}.
 *
 * This class extensively tests partial-date matching semantics. Note:
 * DateContainsKeywordsPredicate currently uses String.startsWith(keyword),
 * so prefix-style matches (including some "malformed" partials) are accepted.
 */
public class FindEventDateCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());

    // ---------------------- equals() ----------------------

    @Test
    public void equals_sameObject_returnTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_sameValues_returnTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        FindEventDateCommand command1 = new FindEventDateCommand(predicate);
        FindEventDateCommand command2 = new FindEventDateCommand(predicate);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentType_returnFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        assertFalse(command.equals(1));
    }

    @Test
    public void equals_null_returnFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentPredicate_returnFalse() {
        DateContainsKeywordsPredicate firstPredicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        DateContainsKeywordsPredicate secondPredicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2026"));
        FindEventDateCommand findFirst = new FindEventDateCommand(firstPredicate);
        FindEventDateCommand findSecond = new FindEventDateCommand(secondPredicate);
        assertFalse(findFirst.equals(findSecond));
    }

    // ---------------------- execute(...) behaviour ----------------------

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = new DateContainsKeywordsPredicate(Collections.emptyList());
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_singleFullDate_singleEventFound() {
        // HACKATHON has date "2025-12-15T14:00"
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("2025-12-15T14:00");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Arrays.asList(HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_partialYear_multipleEventsFound() {
        // "2025" should match MEETING, WORKSHOP and HACKATHON (all in 2025)
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        DateContainsKeywordsPredicate predicate = preparePredicate("2025");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);

        // order preserved from TypicalEvents.getTypicalEvents(): MEETING, WORKSHOP, HACKATHON
        assertEquals(Arrays.asList(MEETING, WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_partialYearMonth_multipleEventsFound() {
        // "2025-11" should match MEETING ("2025-11-01T12:00") and WORKSHOP ("2025-11-10T13:00")
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        DateContainsKeywordsPredicate predicate = preparePredicate("2025-11");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Arrays.asList(MEETING, WORKSHOP), model.getFilteredEventList());
    }

    @Test
    public void execute_partialDate_singleEventFound() {
        // "2025-12-15" should match HACKATHON
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("2025-12-15");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Arrays.asList(HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_partialTimePrefix_returnsTrue() {
        // Important: predicate uses startsWith, so "2025-12-15T14:0" is a prefix of "2025-12-15T14:00"
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("2025-12-15T14:0");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Arrays.asList(HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordEmptyString_matchesAllEvents() {
        // startsWith("") is true for any string, so this will match all events
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 4);
        DateContainsKeywordsPredicate predicate = new DateContainsKeywordsPredicate(
                Collections.singletonList(""));
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        // All typical events in original order
        assertEquals(Arrays.asList(MEETING, WORKSHOP, HACKATHON, SEMINAR), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithWhitespace_singleEventFound() {
        // whitespace around input should be trimmed by preparePredicate helper
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate(" 2025-12-15T14:00  ");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Arrays.asList(HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_duplicateKeywords_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        DateContainsKeywordsPredicate predicate = preparePredicate("2025-12-15T14:00 2025-12-15T14:00");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Arrays.asList(HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_mixedKeywords_someEventsFound() {
        // "2025-12" matches HACKATHON, "2026-01" matches SEMINAR -> expect HACKATHON then SEMINAR (original order)
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        DateContainsKeywordsPredicate predicate = preparePredicate("2025-12 2026-01");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Arrays.asList(HACKATHON, SEMINAR), model.getFilteredEventList());
    }

    @Test
    public void execute_noMatchingKeyword_noEventFound() {
        // "2024" matches none of the typical events
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = preparePredicate("2024");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithSpecialCharacters_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = preparePredicate("@2025!");
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage,
                CommandResult.showEvents(expectedMessage), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    // ---------------------- toString() ----------------------

    @Test
    public void toStringMethod_containsPredicateRepresentation() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("2025-12-15T14:00"));
        FindEventDateCommand command = new FindEventDateCommand(predicate);
        String expected = FindEventDateCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    // ---------------------- helper ----------------------

    /**
     * Parses {@code userInput} into a {@code DateContainsKeywordsPredicate}.
     *
     * This mirrors how the parser splits keywords on whitespace and trims the input.
     */
    private DateContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DateContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }
}
