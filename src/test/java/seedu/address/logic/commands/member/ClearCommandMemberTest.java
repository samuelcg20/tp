package seedu.address.logic.commands.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandMemberTest {
    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearMemberCommand(), model, ClearMemberCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonNullModel_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        ClearMemberCommand command = new ClearMemberCommand();
        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertEquals(ClearMemberCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }
}
