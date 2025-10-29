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
import seedu.address.model.event.LocationContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventLocationCommand}.
 */
public class FindEventLocationCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());

    @Test
    public void equals() {
        LocationContainsKeywordsPredicate firstPredicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("Zoom"));
        LocationContainsKeywordsPredicate secondPredicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("Auditorium"));

        FindEventLocationCommand findFirstCommand = new FindEventLocationCommand(firstPredicate);
        FindEventLocationCommand findSecondCommand = new FindEventLocationCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventLocationCommand findFirstCommandCopy = new FindEventLocationCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        LocationContainsKeywordsPredicate predicate = new LocationContainsKeywordsPredicate(Collections.emptyList());
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_singleKeyword_singleEventFound() {
        // Only MEETING has "Zoom" as its location
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        LocationContainsKeywordsPredicate predicate = preparePredicate("Zoom");
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithWhitespace_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        LocationContainsKeywordsPredicate predicate = preparePredicate("  Zoom  "); // extra spaces
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordDifferentCase_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        LocationContainsKeywordsPredicate predicate = preparePredicate("zOOM"); // uppercase/lowercase mix
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_duplicateKeywords_singleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        LocationContainsKeywordsPredicate predicate = preparePredicate("Zoom Zoom Zoom");
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_mixedKeywords_someEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        LocationContainsKeywordsPredicate predicate = preparePredicate("Zoom InvalidLocation");
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        // "UTown" matches HACKATHON, "Auditorium" matches WORKSHOP
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        LocationContainsKeywordsPredicate predicate = preparePredicate("UTown Auditorium");
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordMatchingAllEvents_allEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        LocationContainsKeywordsPredicate predicate = preparePredicate("Zoom UTown Auditorium");
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Arrays.asList(MEETING, WORKSHOP, HACKATHON), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordNotMatchingCase_noEventFound() {
        // Case-sensitive check (depending on predicate implementation)
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        LocationContainsKeywordsPredicate predicate = preparePredicate("zoomz");
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_keywordWithSpecialCharacters_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        LocationContainsKeywordsPredicate predicate = preparePredicate("@Zoom!");
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_partialKeyword_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        LocationContainsKeywordsPredicate predicate = preparePredicate("Zoo"); // partial match for "Zoom"
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Arrays.asList("Zoom"));
        FindEventLocationCommand command = new FindEventLocationCommand(predicate);
        String expected = FindEventLocationCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code LocationContainsKeywordsPredicate}.
     */
    private LocationContainsKeywordsPredicate preparePredicate(String userInput) {
        return new LocationContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }
}
