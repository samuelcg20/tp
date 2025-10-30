package seedu.address.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalAliases.ADD_ALIAS;
import static seedu.address.testutil.TypicalAliases.DELETE_ALIAS;
import static seedu.address.testutil.TypicalAliases.getTypicalAliasBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;

public class AliasCreateCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize model with a typical alias book
        AliasBook aliasBook = getTypicalAliasBook();
        model = new ModelManager(getTypicalAddressBook(), aliasBook, new UserPrefs());
    }

    @Test
    public void execute_createNewAlias_success() throws CommandException {
        Alias newAlias = new Alias("exit", "ex");

        AliasCreateCommand command = new AliasCreateCommand(newAlias);
        CommandResult result = command.execute(model);

        assertEquals(String.format(AliasCreateCommand.MESSAGE_SUCCESS, newAlias),
                result.getFeedbackToUser());
        assertEquals(true, model.hasAlias(newAlias));
    }

    @Test
    public void execute_duplicateAliasWord_throwsCommandException() {
        Alias duplicateAlias = ADD_ALIAS; // Already exists in TypicalAliases

        AliasCreateCommand command = new AliasCreateCommand(duplicateAlias);
        assertThrows(CommandException.class, () ->
                command.execute(model));
    }

    @Test
    public void execute_duplicateCommandWordReplacesAliasSuccess() throws CommandException {
        // "delete" command already has DELETE_ALIAS in TypicalAliases
        Alias newAlias = new Alias("delete", "delNew");

        AliasCreateCommand command = new AliasCreateCommand(newAlias);
        CommandResult result = command.execute(model);

        assertEquals(String.format(AliasCreateCommand.MESSAGE_DUPLICATE_COMMAND_WORD,
                        newAlias.getCommandWord(), newAlias),
                result.getFeedbackToUser());
        assertEquals(true, model.hasAlias(newAlias));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AliasCreateCommand command = new AliasCreateCommand(ADD_ALIAS);
        assertEquals(command, command);
    }

    @Test
    public void equals_sameAlias_returnsTrue() {
        AliasCreateCommand command1 = new AliasCreateCommand(ADD_ALIAS);
        AliasCreateCommand command2 = new AliasCreateCommand(ADD_ALIAS);
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentAlias_returnsFalse() {
        AliasCreateCommand command1 = new AliasCreateCommand(ADD_ALIAS);
        AliasCreateCommand command2 = new AliasCreateCommand(DELETE_ALIAS);
        assertEquals(false, command1.equals(command2));
    }
}
