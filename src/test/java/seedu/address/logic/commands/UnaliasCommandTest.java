package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalAliases.ADD_ALIAS;
import static seedu.address.testutil.TypicalAliases.getTypicalAliasBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;

public class UnaliasCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize model with a typical alias book
        AliasBook aliasBook = getTypicalAliasBook();
        model = new ModelManager(getTypicalAddressBook(), aliasBook, new UserPrefs());
    }

    // ---------------- Execute single command alias removal ----------------

    @Test
    public void execute_removeExistingAlias_success() throws CommandException {
        UnaliasCommand command = new UnaliasCommand(ADD_ALIAS.getCommandWord());
        CommandResult result = command.execute(model);

        assertEquals(String.format(UnaliasCommand.MESSAGE_SUCCESS, ADD_ALIAS.getCommandWord()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_removeNonExistingAlias_throwsCommandException() {
        UnaliasCommand command = new UnaliasCommand("nonexistent");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    // ---------------- Execute 'all' alias removal ----------------

    @Test
    public void execute_removeAllAliases_success() throws CommandException {
        UnaliasCommand command = new UnaliasCommand("all");
        CommandResult result = command.execute(model);

        assertEquals(UnaliasCommand.MESSAGE_SUCCESS_ALL, result.getFeedbackToUser());
        assertEquals(true, model.isAliasBookEmpty());
    }

    @Test
    public void execute_removeAllAliasesWhenEmpty_throwsCommandException() {
        model.clearAllAliases();
        UnaliasCommand command = new UnaliasCommand("all");

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    // ---------------- Case sensitivity check ----------------

    @Test
    public void execute_removeAllAliasesCaseInsensitive_success() throws CommandException {
        UnaliasCommand command = new UnaliasCommand("AlL");
        CommandResult result = command.execute(model);

        assertEquals(UnaliasCommand.MESSAGE_SUCCESS_ALL, result.getFeedbackToUser());
        assertEquals(true, model.isAliasBookEmpty());
    }
}