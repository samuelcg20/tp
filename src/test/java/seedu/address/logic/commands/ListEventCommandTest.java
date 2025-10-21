
// package seedu.address.logic.commands;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import seedu.address.logic.commands.event.ListEventCommand;
// import seedu.address.model.Model;
// import seedu.address.model.ModelManager;
// import seedu.address.model.UserPrefs;

// /**
//  * Contains unit tests for {@code ListEventCommand}.
//  */
// public class ListEventCommandTest {

//     private Model model;
//     private Model expectedModel;

//     @BeforeEach
//     public void setUp() {
//         model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//         expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//     }

//     @Test
//     public void execute_listEvent_success() {
//         ListEventCommand command = new ListEventCommand();

//         CommandResult expectedCommandResult =
//                 new CommandResult(ListEventCommand.MESSAGE_SUCCESS, false, true, false, false);

//         assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
//     }

//     @Test
//     public void execute_commandResultFlagsCorrect() {
//         CommandResult result = new ListEventCommand().execute(model);

//         assertEquals(ListEventCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
//         assertFalse(result.isShowHelp());
//         assertTrue(result.isShowEvents());
//         assertFalse(result.isExit());
//         assertFalse(result.isShowMembers());
//     }
// }

