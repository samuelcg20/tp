package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {

    private static final String FEEDBACK = "feedback";

    @Test
    public void equals() {
        CommandResult base = new CommandResult(FEEDBACK);

        // same values -> true
        assertTrue(base.equals(new CommandResult(FEEDBACK)));
        assertTrue(base.equals(new CommandResult(FEEDBACK, false, false)));

        // same object -> true
        assertTrue(base.equals(base));

        // null -> false
        assertFalse(base.equals(null));

        // different type -> false
        assertFalse(base.equals(0.5f));

        // different feedback -> false
        assertFalse(base.equals(new CommandResult("different")));

        // different showHelp field -> false
        assertFalse(base.equals(new CommandResult(FEEDBACK, true, false)));

        // different showEvents field -> false
        assertFalse(base.equals(new CommandResult(FEEDBACK, false, true)));

        // different showMembers field -> false
        assertFalse(base.equals(new CommandResult(FEEDBACK, false, false, true, false)));

        // different exit field -> false
        assertFalse(base.equals(new CommandResult(FEEDBACK, false, false, false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult base = new CommandResult(FEEDBACK);

        // same values -> same hashcode
        assertEquals(base.hashCode(), new CommandResult(FEEDBACK).hashCode());

        // different feedback
        assertNotEquals(base.hashCode(), new CommandResult("different").hashCode());

        // different showHelp
        assertNotEquals(base.hashCode(), new CommandResult(FEEDBACK, true, false).hashCode());

        // different showEvents
        assertNotEquals(base.hashCode(), new CommandResult(FEEDBACK, false, true).hashCode());

        // different showMembers
        assertNotEquals(base.hashCode(), new CommandResult(FEEDBACK, false, false, true, false).hashCode());

        // different exit
        assertNotEquals(base.hashCode(), new CommandResult(FEEDBACK, false, false, false, true).hashCode());
    }

    @Test
    public void showMembersFactory_createsCorrectCommandResult() {
        CommandResult result = CommandResult.showMembers(FEEDBACK);
        assertEquals(FEEDBACK, result.getFeedbackToUser());
        assertFalse(result.isShowHelp());
        assertFalse(result.isShowEvents());
        assertTrue(result.isShowMembers());
        assertFalse(result.isExit());
    }

    @Test
    public void showEventsFactory_createsCorrectCommandResult() {
        CommandResult result = CommandResult.showEvents(FEEDBACK);
        assertEquals(FEEDBACK, result.getFeedbackToUser());
        assertFalse(result.isShowHelp());
        assertTrue(result.isShowEvents());
        assertFalse(result.isShowMembers());
        assertFalse(result.isExit());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult(FEEDBACK);
        String expected = String.format("%s{feedbackToUser=%s, showHelp=%s, showEvents=%s, showMembers=%s, exit=%s}",
                CommandResult.class.getCanonicalName(),
                commandResult.getFeedbackToUser(),
                commandResult.isShowHelp(),
                commandResult.isShowEvents(),
                commandResult.isShowMembers(),
                commandResult.isExit());
        assertEquals(expected, commandResult.toString());
    }
}
