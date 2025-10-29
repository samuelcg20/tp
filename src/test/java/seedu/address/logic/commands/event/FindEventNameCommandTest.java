package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.HACKATHON;
import static seedu.address.testutil.TypicalEvents.MEETING;
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
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventNameCommand}.
 */
public class FindEventNameCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());

    @Test
    public void equals() {
        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Hackathon"));
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Meeting"));

        FindEventNameCommand firstCommand = new FindEventNameCommand(firstPredicate);
        FindEventNameCommand secondCommand = new FindEventNameCommand(secondPredicate);

        // same object -> true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> true
        FindEventNameCommand firstCommandCopy = new FindEventNameCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> false
        assertFalse(firstCommand.equals(1));

        // null -> false
        assertFalse(firstCommand.equals(null));

        // different predicate -> false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_singleKeyword_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("Meeting");
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("Workshop HackNUS");
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithWhitespace_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("  Meeting  "); // leading/trailing spaces
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordDifferentCase_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("mEETing"); // mixed case
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_duplicateKeywords_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("Meeting Meeting Meeting");
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_mixedKeywords_someEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("Meeting InvalidEvent");
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordMatchingAllEvents_allEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("Meeting Workshop HackNUS");
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING, WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_lowercasekeywordMatchingAllEvents_allEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("meeting workshop hacknus");
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING, WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithSpecialCharacters_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("@Meeting!");
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_partialKeyword_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("Meet"); // partial
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordNotMatchingCase_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventNameContainsKeywordsPredicate predicate = preparePredicate("HackNUSX"); // non-existent
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Meeting"));
        FindEventNameCommand command = new FindEventNameCommand(predicate);
        String expected = FindEventNameCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private EventNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EventNameContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }
}
