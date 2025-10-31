//package seedu.address.logic.commands.member;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalAliases.getTypicalAliasBook;
//import static seedu.address.testutil.TypicalPersons.ALICE;
//import static seedu.address.testutil.TypicalPersons.CARL;
//import static seedu.address.testutil.TypicalPersons.DANIEL;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.Messages;
//;
//import seedu.address.logic.commands.CommandResult;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.person.NameContainsKeywordsPredicate;
//
//
//public class FindMemberNameCommandTest {
//
//    private Model model;
//    private Model expectedModel;
//
//    @BeforeEach
//    public void setUp() {
//        model = new ModelManager(getTypicalAddressBook(), getTypicalAliasBook(), new UserPrefs());
//        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalAliasBook(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_noKeywords_noPersonFound() {
//        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
//        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
//        FindMemberNameCommand command = new FindMemberNameCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, CommandResult.showMembers(expectedMessage), expectedModel);
//        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
//    }
//
//    @Test
//    public void execute_singleKeyword_multiplePersonsFound() {
//        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
//        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
//        FindMemberNameCommand command = new FindMemberNameCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, CommandResult.showMembers(expectedMessage), expectedModel);
//        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
//    }
//
//    @Test
//    public void execute_eKeyword_multiplePersonsFound() {
//        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
//        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice, Carl"));
//        FindMemberNameCommand command = new FindMemberNameCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model,
//        expectedMessage, CommandResult.showMembers(expectedMessage), expectedModel);
//        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredPersonList());
//    }
//
//
//    @Test
//    public void equals() {
//        NameContainsKeywordsPredicate firstPredicate =
//                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
//        NameContainsKeywordsPredicate secondPredicate =
//                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
//
//        FindMemberNameCommand firstCommand = new FindMemberNameCommand(firstPredicate);
//        FindMemberNameCommand secondCommand = new FindMemberNameCommand(secondPredicate);
//
//        // same object -> returns true
//        assertEquals(firstCommand, firstCommand);
//
//        // same values -> returns true
//        FindMemberNameCommand firstCommandCopy = new FindMemberNameCommand(firstPredicate);
//        assertEquals(firstCommand, firstCommandCopy);
//
//        // different types -> returns false
//        assertNotEquals(firstCommand, 1);
//
//        // null -> returns false
//        assertNotEquals(firstCommand, null);
//
//        // different predicate -> returns false
//        assertNotEquals(firstCommand, secondCommand);
//    }
//}
//
