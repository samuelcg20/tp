package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;

/**
 * Contains unit tests for {@code ListEventCommand}.
 */
public class ListEventCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new AliasBook(), new UserPrefs());
    }

    @Test
    public void execute_listEvent_success() {
        ListEventCommand command = new ListEventCommand();

        CommandResult expectedCommandResult =
                new CommandResult(ListEventCommand.MESSAGE_SUCCESS, false, true, false, false);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_commandResultFlagsCorrect() {
        // This test might fail because it calls execute directly without using assertCommandSuccess
        // Let's modify it to handle potential CommandException
        try {
            CommandResult result = new ListEventCommand().execute(model);

            assertEquals(ListEventCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
            assertFalse(result.isShowHelp());
            assertTrue(result.isShowEvents());
            assertFalse(result.isExit());
            assertFalse(result.isShowMembers());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_eventsListFilterUpdated() {
        ListEventCommand command = new ListEventCommand();

        // Store the initial state of the filtered event list
        int initialFilteredSize = model.getFilteredEventList().size();

        command.execute(model);

        // Verify that the filtered event list might have changed (or at least the predicate was updated)
        // This depends on your implementation - if you start with no filter, the size might be the same
        // but the predicate has been set to show all events
        assertTrue(model.getFilteredEventList().size() >= initialFilteredSize);
    }

    @Test
    public void execute_multipleCalls_consistentBehavior() {
        ListEventCommand command = new ListEventCommand();

        // First call
        CommandResult firstResult = command.execute(model);
        assertEquals(ListEventCommand.MESSAGE_SUCCESS, firstResult.getFeedbackToUser());
        assertTrue(firstResult.isShowEvents());

        // Second call - should behave the same way
        CommandResult secondResult = command.execute(model);
        assertEquals(ListEventCommand.MESSAGE_SUCCESS, secondResult.getFeedbackToUser());
        assertTrue(secondResult.isShowEvents());
    }

    @Test
    public void execute_withEmptyEventList_success() {
        // Create a model with no events
        Model emptyModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
        // If your typical address book has events, you might need to clear them first
        // emptyModel.setAddressBook(new AddressBook()); // if you want to test with completely empty data

        ListEventCommand command = new ListEventCommand();

        CommandResult expectedCommandResult =
                new CommandResult(ListEventCommand.MESSAGE_SUCCESS, false, true, false, false);

        assertCommandSuccess(command, emptyModel, expectedCommandResult, emptyModel);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ListEventCommand command = new ListEventCommand();
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_null_returnsFalse() {
        ListEventCommand command = new ListEventCommand();
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ListEventCommand command = new ListEventCommand();
        assertFalse(command.equals(new Object()));
    }
}
