package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.member.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.member.HelpCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_showsCorrectFeedback() {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result;
        try {
            result = helpCommand.execute(model);
            // Verify the feedback message is correct
            assertTrue(result.getFeedbackToUser().contains(SHOWING_HELP_MESSAGE));
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_help_setsShowHelpFlag() {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result;
        try {
            result = helpCommand.execute(model);
            // Verify that showHelp flag is set to true
            assertTrue(result.isShowHelp());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_help_doesNotSetExitFlag() {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result;
        try {
            result = helpCommand.execute(model);
            // Verify that exit flag remains false
            assertFalse(result.isExit());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_help_doesNotSetShowEventsFlag() {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result;
        try {
            result = helpCommand.execute(model);
            // Verify that showEvents flag remains false
            assertFalse(result.isShowEvents());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_help_doesNotSetShowMembersFlag() {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result;
        try {
            result = helpCommand.execute(model);
            // Verify that showMembers flag remains false
            assertFalse(result.isShowMembers());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void execute_help_withDifferentModelStates() {
        // Test with empty model
        Model emptyModel = new ModelManager();
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), emptyModel, expectedCommandResult, emptyModel);

        // Test with model containing data (if applicable)
        // You might want to add some test data to the model and verify help still works
        Model populatedModel = new ModelManager();
        // Add some test data to populatedModel if needed
        assertCommandSuccess(new HelpCommand(), populatedModel, expectedCommandResult, populatedModel);
    }

    @Test
    public void execute_help_multipleCalls() {
        HelpCommand helpCommand = new HelpCommand();

        // First call
        CommandResult firstResult;
        try {
            firstResult = helpCommand.execute(model);
            assertTrue(firstResult.isShowHelp());
            assertTrue(firstResult.getFeedbackToUser().contains(SHOWING_HELP_MESSAGE));
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }

        // Second call - should work the same way
        CommandResult secondResult;
        try {
            secondResult = helpCommand.execute(model);
            assertTrue(secondResult.isShowHelp());
            assertTrue(secondResult.getFeedbackToUser().contains(SHOWING_HELP_MESSAGE));
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        HelpCommand helpCommand = new HelpCommand();
        assertTrue(helpCommand.equals(helpCommand));
    }

    @Test
    public void equals_null_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.equals(new Object()));
    }

    // Edge case: Test with the exact CommandResult constructor that HelpCommand uses
    @Test
    public void execute_help_commandResultExactMatch() {
        // First, check what constructor your HelpCommand actually uses
        // If it uses the legacy constructor (showHelp, exit), then this test should pass
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    // Test for the specific boolean flags in CommandResult
    @Test
    public void execute_help_verifyAllFlags() {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result;
        try {
            result = helpCommand.execute(model);

            // Verify all flags are set correctly
            assertTrue(result.isShowHelp(), "Show help should be true");
            assertFalse(result.isShowEvents(), "Show events should be false");
            assertFalse(result.isShowMembers(), "Show members should be false");
            assertFalse(result.isExit(), "Exit should be false");

            // Verify feedback message
            assertEquals(SHOWING_HELP_MESSAGE, result.getFeedbackToUser());

        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }
}