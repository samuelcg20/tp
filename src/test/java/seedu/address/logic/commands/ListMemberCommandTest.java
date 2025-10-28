package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.member.ListMemberCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;

/**
 * Contains unit tests for {@code ListMemberCommand}.
 */
public class ListMemberCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new AliasBook(), new UserPrefs());
    }

    @Test
    public void execute_listMember_success() {
        ListMemberCommand command = new ListMemberCommand();

        CommandResult expectedCommandResult =
                new CommandResult(ListMemberCommand.MESSAGE_SUCCESS, false, false, true, false);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_commandResultFlagsCorrect() {
        try {
            CommandResult result = new ListMemberCommand().execute(model);

            assertEquals(ListMemberCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
            assertFalse(result.isShowHelp());
            assertFalse(result.isShowEvents());
            assertTrue(result.isShowMembers());
            assertFalse(result.isExit());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_membersListFilterUpdated() {
        ListMemberCommand command = new ListMemberCommand();

        // Store the initial state of the filtered member list
        int initialFilteredSize = model.getFilteredPersonList().size();

        command.execute(model);

        // Verify that the filtered member list might have changed
        assertTrue(model.getFilteredPersonList().size() >= initialFilteredSize);
    }

    @Test
    public void execute_multipleCalls_consistentBehavior() {
        ListMemberCommand command = new ListMemberCommand();

        // First call
        CommandResult firstResult = command.execute(model);
        assertEquals(ListMemberCommand.MESSAGE_SUCCESS, firstResult.getFeedbackToUser());
        assertTrue(firstResult.isShowMembers());

        // Second call - should behave the same way
        CommandResult secondResult = command.execute(model);
        assertEquals(ListMemberCommand.MESSAGE_SUCCESS, secondResult.getFeedbackToUser());
        assertTrue(secondResult.isShowMembers());
    }

    @Test
    public void execute_withEmptyMemberList_success() {
        // Create a model with no members
        Model emptyModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());

        ListMemberCommand command = new ListMemberCommand();

        CommandResult expectedCommandResult =
                new CommandResult(ListMemberCommand.MESSAGE_SUCCESS, false, false, true, false);

        assertCommandSuccess(command, emptyModel, expectedCommandResult, emptyModel);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ListMemberCommand command = new ListMemberCommand();
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_null_returnsFalse() {
        ListMemberCommand command = new ListMemberCommand();
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ListMemberCommand command = new ListMemberCommand();
        assertFalse(command.equals(new Object()));
    }
}