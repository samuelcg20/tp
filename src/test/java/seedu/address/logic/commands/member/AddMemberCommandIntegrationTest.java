package seedu.address.logic.commands.member;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@link AddMemberCommand}.
 */
public class AddMemberCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();
        Model expectedModel = new ModelManager(model.getAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        CommandResult expectedCommandResult = CommandResult.showMembers(String.format(
                AddMemberCommand.MESSAGE_SUCCESS, Messages.format(validPerson)));

        assertCommandSuccess(new AddMemberCommand(validPerson), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddMemberCommand(personInList), model, AddMemberCommand.MESSAGE_DUPLICATE_PERSON);
    }
}

