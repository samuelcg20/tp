package seedu.address.model.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Alias alias = new Alias("add", "a");
        aliasBook.addAlias(alias);
        assertTrue(aliasBook.isAliasPresent("a"));
        assertTrue(aliasBook.isCommandPresent("add"));
    }

    @Test
    public void removeAlias_aliasRemovedSuccessfully() {
        Alias alias = new Alias("add", "a");
        aliasBook.addAlias(alias);
        aliasBook.removeAlias("a");
        assertFalse(aliasBook.isAliasPresent("a"));
    }

    @Test
    public void getActualCommandWord_existingAlias_returnsCommand() {
        aliasBook.addAlias(new Alias("delete", "rm"));
        assertEquals("delete", AliasBook.getActualCommandWord("rm"));
    }

    @Test
    public void getActualCommandWord_nonExistingAlias_returnsSameWord() {
        assertEquals("hello", AliasBook.getActualCommandWord("hello"));
    }

    @Test
    public void getAliasForCommandWord_existingCommand_returnsAlias() {
        aliasBook.addAlias(new Alias("add", "a"));
        assertEquals("a", aliasBook.getAliasForCommandWord("add"));
    }

    @Test
    public void getAliasForCommandWord_nonExistingCommand_returnsNull() {
        assertEquals(null, aliasBook.getAliasForCommandWord("invalid"));
    }

    @Test
    public void isEmpty_afterAddingAndClearing_returnsTrue() {
        aliasBook.addAlias(new Alias("add", "a"));
        aliasBook.clear();
        assertTrue(aliasBook.isEmpty());
    }

    @Test
    public void getAliasList_returnsCorrectList() {
        aliasBook.addAlias(new Alias("add", "a"));
        aliasBook.addAlias(new Alias("delete", "d"));
        List<Alias> aliases = aliasBook.getAliasList();
        assertEquals(2, aliases.size());
        assertTrue(aliases.contains(new Alias("add", "a")));
        assertTrue(aliases.contains(new Alias("delete", "d")));
    }

    @Test
    public void toString_returnsStringRepresentation() {
        aliasBook.addAlias(new Alias("add", "a"));
        assertEquals("{a=add}", aliasBook.toString());
    }
}
