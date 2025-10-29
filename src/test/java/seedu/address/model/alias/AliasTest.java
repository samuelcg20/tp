package seedu.address.model.alias;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
        // valid command words
        assertTrue(Alias.isValidCommandWord("add"));
        assertTrue(Alias.isValidCommandWord("delete"));
        assertTrue(Alias.isValidCommandWord("alias"));

        // invalid command words
        assertFalse(Alias.isValidCommandWord("invalid"));
        assertFalse(Alias.isValidCommandWord(" "));
        assertFalse(Alias.isValidCommandWord(""));
        assertFalse(Alias.isValidCommandWord("Add")); // case-sensitive
    }

    @Test
    public void isValidAliasWord_equivalencePartitioning() {
        // invalid alias words (partition: existing command)
        assertFalse(Alias.isValidAliasWord("add"));

        // invalid alias words (partition: contains symbols)
        assertFalse(Alias.isValidAliasWord("rm!"));
        assertFalse(Alias.isValidAliasWord("@alias"));

        // invalid alias words (partition: too long)
        assertFalse(Alias.isValidAliasWord("thisisaverylongword"));

        // invalid alias words (partition: empty or whitespace)
        assertFalse(Alias.isValidAliasWord(""));
        assertFalse(Alias.isValidAliasWord(" "));

        // valid alias words (partition: alphanumeric <=10 chars)
        assertTrue(Alias.isValidAliasWord("rm"));
        assertTrue(Alias.isValidAliasWord("d3l"));
        assertTrue(Alias.isValidAliasWord("clear1"));
    }

    @Test
    public void isValidAliasWord_boundaryValues() {
        // exactly 10 chars -> valid
        assertTrue(Alias.isValidAliasWord("abcdefghij"));
        // 11 chars -> invalid
        assertFalse(Alias.isValidAliasWord("abcdefghijk"));
    }

    @Test
    public void equals() {
        Alias alias = new Alias("add", "a");

        // same values -> true
        assertTrue(alias.equals(new Alias("add", "a")));

        // same object -> true
        assertTrue(alias.equals(alias));

        // null -> false
        assertFalse(alias.equals(null));

        // different type -> false
        assertFalse(alias.equals("String"));

        // different command -> false
        assertFalse(alias.equals(new Alias("delete", "a")));

        // different alias -> false
        assertFalse(alias.equals(new Alias("add", "b")));
    }

    @Test
    public void toString_validAlias_correctFormat() {
        Alias alias = new Alias("add", "a");
        assertEquals("a -> add", alias.toString());
    }

    @Test
    public void hashCode_consistentWithEquals() {
        Alias alias1 = new Alias("add", "a");
        Alias alias2 = new Alias("add", "a");
        assertEquals(alias1.hashCode(), alias2.hashCode());
    }
}

