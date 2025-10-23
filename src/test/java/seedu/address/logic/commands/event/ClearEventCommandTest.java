// package seedu.address.logic.commands.event;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

// import org.junit.jupiter.api.Test;

// import seedu.address.logic.commands.CommandResult;
// import seedu.address.model.Model;
// import seedu.address.model.ModelManager;
// import seedu.address.model.UserPrefs;

// public class ClearEventCommandTest {
//     @Test
//     public void execute_emptyAddressBook_success() {
//         Model model = new ModelManager();
//         Model expectedModel = new ModelManager();

//         assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
//     }

//     @Test
//     public void execute_nonNullModel_success() {
//         Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

//         ClearEventCommand command = new ClearEventCommand();
//         CommandResult result = command.execute(model);

//         assertNotNull(result);
//         assertEquals(ClearEventCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
//     }
// }
