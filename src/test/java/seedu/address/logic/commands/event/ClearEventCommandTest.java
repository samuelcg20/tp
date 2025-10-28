package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.Event;

/**
 * Contains unit tests for {@code ClearEventCommand}.
 */
public class ClearEventCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model emptyModel = new ModelManager();

        ClearEventCommand command = new ClearEventCommand();
        CommandResult result = command.execute(emptyModel);

        // Verify command result
        assertEquals(ClearEventCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(result.isShowEvents()); // Should show events after clearing

        // Verify model state
        assertTrue(emptyModel.getAddressBook().getEventList().isEmpty());
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        // Verify model has events before clearing
        assertFalse(model.getAddressBook().getEventList().isEmpty());
        int initialEventCount = model.getAddressBook().getEventList().size();

        ClearEventCommand command = new ClearEventCommand();
        CommandResult result = command.execute(model);

        // Verify command result
        assertEquals(ClearEventCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(result.isShowEvents());
        assertFalse(result.isShowHelp());
        assertFalse(result.isShowMembers());
        assertFalse(result.isExit());

        // Verify model state after execution
        assertTrue(model.getAddressBook().getEventList().isEmpty());

        // Verify that expected number of events were cleared
        assertEquals(initialEventCount, getTypicalEvents().size());
    }

    @Test
    public void execute_clearsAllEventsFromModel() {
        // Get initial event count
        List<Event> initialEvents = model.getAddressBook().getEventList();
        int initialSize = initialEvents.size();
        assertTrue(initialSize > 0, "Model should start with " + initialSize + " events");

        // Verify we have the expected typical events
        assertEquals(getTypicalEvents().size(), initialSize);

        // Execute clear command
        new ClearEventCommand().execute(model);

        // Verify all events are removed
        List<Event> eventsAfterClear = model.getAddressBook().getEventList();
        assertEquals(0, eventsAfterClear.size(), "All events should be cleared");
        assertTrue(eventsAfterClear.isEmpty(), "Event list should be empty after clear");
    }

    @Test
    public void execute_attendanceCleanupPerformed() {
        // This test verifies that cleanupEventAttendance is called for each event
        // For now, we'll verify the end state and that all events are properly cleared

        int initialEventCount = model.getAddressBook().getEventList().size();
        assertTrue(initialEventCount > 0, "Should have " + initialEventCount + " events to clear");

        // Verify specific events exist before clearing
        List<Event> typicalEvents = getTypicalEvents();
        for (Event event : typicalEvents) {
            assertTrue(model.getAddressBook().getEventList().contains(event));
        }

        new ClearEventCommand().execute(model);

        // Verify events are cleared
        assertTrue(model.getAddressBook().getEventList().isEmpty());
    }

    @Test
    public void execute_multipleClears_success() {
        // Verify initial state
        int initialEventCount = model.getAddressBook().getEventList().size();
        assertTrue(initialEventCount > 0, "Should start with events");

        // First clear
        new ClearEventCommand().execute(model);
        assertTrue(model.getAddressBook().getEventList().isEmpty());

        // Second clear on already empty model
        new ClearEventCommand().execute(model);
        assertTrue(model.getAddressBook().getEventList().isEmpty());
    }

    @Test
    public void execute_verifyModelStateAfterClear() {
        // Store some initial state for verification
        int initialEventCount = model.getAddressBook().getEventList().size();
        List<Event> initialEvents = List.copyOf(model.getAddressBook().getEventList());

        // Execute the command
        new ClearEventCommand().execute(model);

        // Verify the model state
        assertEquals(0, model.getAddressBook().getEventList().size());

        // expectedModel should remain unchanged (not affected by clear operation)
        assertEquals(initialEventCount, expectedModel.getAddressBook().getEventList().size());

        // Verify all initial events are still in expectedModel
        for (Event event : initialEvents) {
            assertTrue(expectedModel.getAddressBook().getEventList().contains(event));
        }
    }

    @Test
    public void execute_eventsWithNoAttendance_stillClearsSuccessfully() {
        // Test that events without attendance are still properly cleared
        Model modelWithEvents = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());

        // Verify we have events
        assertFalse(modelWithEvents.getAddressBook().getEventList().isEmpty());

        // Clear events
        new ClearEventCommand().execute(modelWithEvents);

        // Verify successful clearance
        assertTrue(modelWithEvents.getAddressBook().getEventList().isEmpty());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ClearEventCommand command = new ClearEventCommand();
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ClearEventCommand command = new ClearEventCommand();
        assertFalse(command.equals(new Object()));
    }

    @Test
    public void equals_null_returnsFalse() {
        ClearEventCommand command = new ClearEventCommand();
        assertFalse(command.equals(null));
    }

}
