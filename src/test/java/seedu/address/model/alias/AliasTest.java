package seedu.address.model.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AliasBuilder;

/**
 * Contains unit tests for {@link Alias}.
 */
public class AliasTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Alias(null, "alias"));
        assertThrows(NullPointerException.class, () -> new Alias("add", null));
    }

    @Test
    public void constructor_blank_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Alias("", "alias"));
        assertThrows(AssertionError.class, () -> new Alias("add", ""));
    }

    @Test
    public void isValidCommandWord_equivalencePartitioning() {
        assertTrue(Alias.isValidCommandWord("add"));
        assertTrue(Alias.isValidCommandWord("list"));
        assertFalse(Alias.isValidCommandWord("invalid"));
        assertFalse(Alias.isValidCommandWord(""));
    }

    @Test
    public void isValidAliasWord_equivalencePartitioning() {
        // invalid alias words
        assertFalse(Alias.isValidAliasWord("add")); // existing command
        assertFalse(Alias.isValidAliasWord("rm!")); // symbol
        assertFalse(Alias.isValidAliasWord("abcdefghijk")); // >10 chars
        assertFalse(Alias.isValidAliasWord(" ")); // blank

        // valid alias words
        assertTrue(Alias.isValidAliasWord("rm"));
        assertTrue(Alias.isValidAliasWord("clear1"));
    }

    @Test
    public void isValidAliasWord_boundaryValues() {
        assertTrue(Alias.isValidAliasWord("abcdefghij")); // 10 chars
        assertFalse(Alias.isValidAliasWord("abcdefghijk")); // 11 chars
    }

    @Test
    public void equals() {
        Alias alias = new AliasBuilder().withCommandWord("add").withAliasWord("a").build();

        // same values -> true
        assertTrue(alias.equals(new AliasBuilder(alias).build()));

        // same object -> true
        assertTrue(alias.equals(alias));

        // null -> false
        assertFalse(alias.equals(null));

        // different type -> false
        assertFalse(alias.equals("String"));

        // different command -> false
        assertFalse(alias.equals(new AliasBuilder().withCommandWord("delete").withAliasWord("a").build()));

        // different alias -> false
        assertFalse(alias.equals(new AliasBuilder().withCommandWord("add").withAliasWord("b").build()));
    }

    @Test
    public void toString_validAlias_correctFormat() {
        Alias alias = new AliasBuilder().withCommandWord("add").withAliasWord("a").build();
        assertEquals("a -> add", alias.toString());
    }

    @Test
    public void hashCode_consistentWithEquals() {
        Alias alias1 = new AliasBuilder().withCommandWord("add").withAliasWord("a").build();
        Alias alias2 = new AliasBuilder().withCommandWord("add").withAliasWord("a").build();
        assertEquals(alias1.hashCode(), alias2.hashCode());
    }
}
