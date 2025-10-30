package seedu.address.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalAliases.getTypicalAliasBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;

public class AliasListCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize model with a typical alias book
        AliasBook aliasBook = getTypicalAliasBook();
        model = new ModelManager(getTypicalAddressBook(), aliasBook, new UserPrefs());
    }

    @Test
    public void execute_listNonEmpty_success() {
        AliasListCommand command = new AliasListCommand();
        CommandResult result = command.execute(model);

        StringBuilder sb = new StringBuilder();
        for (Alias alias : model.getAliasList()) {
            sb.append(alias.toString()).append("\n");
        }

        assertEquals(String.format(AliasListCommand.MESSAGE_SUCCESS, sb), result.getFeedbackToUser());
    }

    @Test
    public void execute_listEmpty_success() {
        model.getAliasBook().clear();
        AliasListCommand command = new AliasListCommand();
        CommandResult result = command.execute(model);

        assertEquals(AliasListCommand.MESSAGE_EMPTY, result.getFeedbackToUser());
    }
}
