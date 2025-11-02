package seedu.address.logic.commands.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.member.EditMemberCommand.EditMemberDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditMemberDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

class EditMemberCommandTest {

    private Model model;
    private Person firstPerson;
    private Person secondPerson;

    @BeforeEach
    void setUp() {
        model = new ModelManager(new AddressBook(), new AliasBook(), new UserPrefs());
        firstPerson = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("91234567").withEmail("alex@u.nus.edu").build();
        secondPerson = new PersonBuilder().withName("Bernice Yu")
                .withPhone("98765432").withEmail("bernice@u.nus.edu").build();
        model.addPerson(firstPerson);
        model.addPerson(secondPerson);
    }

    @Test
    void execute_changeNonIdentityFields_success() throws Exception {
        Person expected = new PersonBuilder(secondPerson).withName("Bernice Y").build();
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
                .withName("Bernice Y")
                .build();
        EditMemberCommand command = new EditMemberCommand(Index.fromOneBased(2), descriptor);

        CommandResult result = command.execute(model);

        assertEquals(CommandResult.showMembers(String.format(
                EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(expected))), result);
        assertEquals(expected, model.getFilteredPersonList().get(1));
    }

    @Test
    void execute_changePhoneToUniqueValue_success() throws Exception {
        Person expected = new PersonBuilder(secondPerson).withPhone("95556666").build();
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
                .withPhone("95556666")
                .build();
        EditMemberCommand command = new EditMemberCommand(Index.fromOneBased(2), descriptor);

        CommandResult result = command.execute(model);

        assertEquals(CommandResult.showMembers(String.format(
                EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(expected))), result);
        assertEquals(expected, model.getFilteredPersonList().get(1));
    }
}
