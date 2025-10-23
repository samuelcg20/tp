// package seedu.address.logic.commands;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import seedu.address.logic.commands.member.ListMemberCommand;
// import seedu.address.model.Model;
// import seedu.address.model.ModelManager;
// import seedu.address.model.UserPrefs;

// /**
//  * Contains unit tests for {@code ListMemberCommand}.
//  */
// public class ListMemberCommandTest {

//     private Model model;
//     private Model expectedModel;

//     @BeforeEach
//     public void setUp() {
//         model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//         expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//     }

//     @Test
//     public void execute_listMember_success() {
//         ListMemberCommand command = new ListMemberCommand();

//         CommandResult expectedCommandResult =
//                 new CommandResult(ListMemberCommand.MESSAGE_SUCCESS, false, false, true, false);

//         assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
//     }

//     @Test
//     public void execute_commandResultFlagsCorrect() {
//         CommandResult result = new ListMemberCommand().execute(model);

//         assertEquals(ListMemberCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
//         assertFalse(result.isShowHelp());
//         assertFalse(result.isShowEvents());
//         assertTrue(result.isShowMembers());
//         assertFalse(result.isExit());
//     }
// }
