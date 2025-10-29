package seedu.address.model.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAliases.ADD_ALIAS;
import static seedu.address.testutil.TypicalAliases.CLEAR_ALIAS;
import static seedu.address.testutil.TypicalAliases.DELETE_ALIAS;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalAliases;

/**
 * Contains unit tests for {@link AliasBook}.
 */
public class AliasBookTest {

    private AliasBook aliasBook;

    @BeforeEach
    public void setUp() {
        aliasBook = new AliasBook();
        aliasBook.clear();
    }

    @Test
    public void addAlias_successfulAddition() {
        aliasBook.addAlias(ADD_ALIAS);
        assertTrue(aliasBook.isAliasPresent("a"));
        assertTrue(aliasBook.isCommandPresent("add"));
    }

    @Test
    public void removeAlias_aliasRemovedSuccessfully() {
        aliasBook.addAlias(DELETE_ALIAS);
        aliasBook.removeAlias("del");
        assertFalse(aliasBook.isAliasPresent("del"));
    }

    @Test
    public void getActualCommandWord_existingAlias_returnsCommand() {
        aliasBook.addAlias(CLEAR_ALIAS);
        assertEquals("clear", AliasBook.getActualCommandWord("c"));
    }

    @Test
    public void getActualCommandWord_nonExistingAlias_returnsSameWord() {
        assertEquals("hello", AliasBook.getActualCommandWord("hello"));
    }

    @Test
    public void getAliasForCommandWord_existingCommand_returnsAlias() {
        aliasBook.addAlias(ADD_ALIAS);
        assertEquals("a", aliasBook.getAliasForCommandWord("add"));
    }

    @Test
    public void getAliasForCommandWord_nonExistingCommand_returnsNull() {
        assertEquals(null, aliasBook.getAliasForCommandWord("nonexistent"));
    }

    @Test
    public void isEmpty_afterAddingAndClearing_returnsTrue() {
        aliasBook.addAlias(ADD_ALIAS);
        aliasBook.clear();
        assertTrue(aliasBook.isEmpty());
    }

    @Test
    public void getAliasList_returnsCorrectList() {
        aliasBook.addAlias(ADD_ALIAS);
        aliasBook.addAlias(DELETE_ALIAS);
        List<Alias> aliases = aliasBook.getAliasList();
        assertEquals(2, aliases.size());
        assertTrue(aliases.contains(ADD_ALIAS));
        assertTrue(aliases.contains(DELETE_ALIAS));
    }

    @Test
    public void toString_returnsStringRepresentation() {
        aliasBook.addAlias(ADD_ALIAS);
        assertEquals("{a=add}", aliasBook.toString());
    }

    @Test
    public void getTypicalAliasBook_containsExpectedAliases() {
        AliasBook typical = TypicalAliases.getTypicalAliasBook();
        assertTrue(typical.isAliasPresent("a"));
        assertTrue(typical.isAliasPresent("del"));
        assertTrue(typical.isAliasPresent("ls"));
    }
}
