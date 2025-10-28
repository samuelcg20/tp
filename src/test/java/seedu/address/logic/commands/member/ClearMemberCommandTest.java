package seedu.address.logic.commands.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.person.Person;

/**
 * Contains unit tests for {@code ClearMemberCommand}.
 */
public class ClearMemberCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // expectedModel should reflect the state after command execution
        expectedModel.resetMembers();

        // Use a custom assertion that accounts for showMembers being true
        ClearMemberCommand command = new ClearMemberCommand();
        CommandResult expectedResult = new CommandResult(ClearMemberCommand.MESSAGE_SUCCESS,
                false, false, true, false);

        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        // Verify model has members before clearing
        assertFalse(model.getAddressBook().getPersonList().isEmpty());

        ClearMemberCommand command = new ClearMemberCommand();

        // Execute and verify command result
        CommandResult result = command.execute(model);

        assertEquals(ClearMemberCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(result.isShowMembers());

        // Verify model state after execution
        assertTrue(model.getAddressBook().getPersonList().isEmpty());
    }

    @Test
    public void execute_clearsAllMembersFromModel() {
        // Get initial member count
        List<Person> initialMembers = model.getAddressBook().getPersonList();
        int initialSize = initialMembers.size();
        assertTrue(initialSize > 0, "Model should start with members");

        // Execute clear command
        new ClearMemberCommand().execute(model);

        // Verify all members are removed
        List<Person> membersAfterClear = model.getAddressBook().getPersonList();
        assertEquals(0, membersAfterClear.size(), "All members should be cleared");
        assertTrue(membersAfterClear.isEmpty(), "Member list should be empty after clear");
    }

    @Test
    public void execute_verifyModelStateAfterClear() {
        // Store some initial state for verification
        int initialMemberCount = model.getAddressBook().getPersonList().size();

        // Execute the command
        new ClearMemberCommand().execute(model);

        // Verify the model state
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(initialMemberCount, expectedModel.getAddressBook().getPersonList().size());
    }

    @Test
    public void execute_multipleClears_success() {
        // First clear
        new ClearMemberCommand().execute(model);
        assertTrue(model.getAddressBook().getPersonList().isEmpty());

        // Second clear on already empty model
        new ClearMemberCommand().execute(model);
        assertTrue(model.getAddressBook().getPersonList().isEmpty());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ClearMemberCommand command = new ClearMemberCommand();
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ClearMemberCommand command = new ClearMemberCommand();
        assertFalse(command.equals(new Object()));
    }

    @Test
    public void equals_null_returnsFalse() {
        ClearMemberCommand command = new ClearMemberCommand();
        assertFalse(command.equals(null));
    }
}
