package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventNameTest {

    // ===== Constructor Tests =====
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EventName(""));
    }

    @Test
    public void constructor_spacesOnly_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EventName("   "));
    }

    @Test
    public void constructor_specialCharacters_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EventName("@Hackathon!"));
    }

    @Test
    public void constructor_tooLong_throwsIllegalArgumentException() {
        String tooLong = "A".repeat(36); // exceeds 35 characters
        assertThrows(IllegalArgumentException.class, () -> new EventName(tooLong));
    }

    // ===== isValidEventName Tests =====
    @Test
    public void isValidEventName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventName.isValidEventName(null));
    }

    @Test
    public void isValidEventName_emptyString_returnsFalse() {
        assertFalse(EventName.isValidEventName(""));
    }

    @Test
    public void isValidEventName_spacesOnly_returnsFalse() {
        assertFalse(EventName.isValidEventName("   "));
    }

    @Test
    public void isValidEventName_specialCharacters_returnsFalse() {
        assertFalse(EventName.isValidEventName("@Hackathon!"));
    }

    @Test
    public void isValidEventName_tooLong_returnsFalse() {
        assertFalse(EventName.isValidEventName("A".repeat(36)));
    }

    @Test
    public void isValidEventName_validSimpleName_returnsTrue() {
        assertTrue(EventName.isValidEventName("Hackathon 2025"));
    }

    @Test
    public void isValidEventName_singleCharacter_returnsTrue() {
        assertTrue(EventName.isValidEventName("A"));
    }

    @Test
    public void isValidEventName_maxLength_returnsTrue() {
        assertTrue(EventName.isValidEventName("A".repeat(35)));
    }

    // ===== normalizeWhitespace Tests =====
    @Test
    public void normalizeWhitespace_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventName.normalizeWhitespace(null));
    }

    @Test
    public void normalizeWhitespace_leadingAndTrailingSpaces_removed() {
        assertTrue(EventName.normalizeWhitespace("   Hackathon   ").equals("Hackathon"));
    }

    @Test
    public void normalizeWhitespace_multipleInternalSpaces_collapsed() {
        assertTrue(EventName.normalizeWhitespace("Hackathon    2025").equals("Hackathon 2025"));
    }

    @Test
    public void normalizeWhitespace_multipleSpacesInsideAndOutside_collapsed() {
        assertTrue(EventName.normalizeWhitespace("  Multiple   Spaces  Inside  ").equals("Multiple Spaces Inside"));
    }

    @Test
    public void normalizeWhitespace_singleWord_returnsSame() {
        assertTrue(EventName.normalizeWhitespace("SingleWord").equals("SingleWord"));
    }

    // ===== canonicalForIdentity Tests =====
    @Test
    public void canonicalForIdentity_endingTrailingSpaces_trimmedAndLowercased() {
        EventName name = new EventName("Hackathon 2025  ");
        assertTrue(name.canonicalForIdentity().equals("hackathon 2025"));
    }

    @Test
    public void canonicalForIdentity_mixedCase_collapsedAndLowercased() {
        EventName name = new EventName("HACKATHON  2025");
        assertTrue(name.canonicalForIdentity().equals("hackathon 2025"));
    }

    // ===== equals Tests =====
    @Test
    public void equals_sameObject_returnsTrue() {
        EventName name = new EventName("Hackathon");
        assertTrue(name.equals(name));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        EventName name1 = new EventName("Hackathon");
        EventName name2 = new EventName("Hackathon");
        assertTrue(name1.equals(name2));
    }

    @Test
    public void equals_differentCase_returnsTrue() {
        EventName name1 = new EventName("Hackathon");
        EventName name2 = new EventName("hackathon");
        assertTrue(name1.equals(name2));
    }

    @Test
    public void equals_null_returnsFalse() {
        EventName name = new EventName("Hackathon");
        assertFalse(name.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        EventName name = new EventName("Hackathon");
        assertFalse(name.equals("Hackathon"));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        EventName name1 = new EventName("Hackathon");
        EventName name2 = new EventName("Workshop");
        assertFalse(name1.equals(name2));
    }

    // ===== hashCode Tests =====
    @Test
    public void hashCode_sameValues_sameHash() {
        EventName name1 = new EventName("Hackathon");
        EventName name2 = new EventName("hackathon");
        assertTrue(name1.hashCode() == name2.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHash() {
        EventName name1 = new EventName("Hackathon");
        EventName name2 = new EventName("Workshop");
        assertFalse(name1.hashCode() == name2.hashCode());
    }

    // ===== toString Tests =====
    @Test
    public void toString_returnsFullName() {
        EventName name = new EventName("Hackathon");
        assertTrue(name.toString().equals("Hackathon"));
    }
}
