package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VenueTest {

    // ---------------------- Constructor tests ----------------------

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Venue(""));
    }

    @Test
    public void constructor_leadingWhitespace_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Venue(" Leading space"));
    }

    @Test
    public void constructor_exceedsMaxLength_throwsIllegalArgumentException() {
        String longString = "A".repeat(76);
        assertThrows(IllegalArgumentException.class, () -> new Venue(longString));
    }

    // ---------------------- isValidVenue tests ----------------------

    @Test
    public void isValidVenue_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));
    }

    @Test
    public void isValidVenue_emptyString_returnsFalse() {
        assertFalse(Venue.isValidVenue(""));
    }

    @Test
    public void isValidVenue_onlySpaces_returnsFalse() {
        assertFalse(Venue.isValidVenue("     "));
    }

    @Test
    public void isValidVenue_leadingSpace_returnsFalse() {
        assertFalse(Venue.isValidVenue(" Leading space"));
    }

    @Test
    public void isValidVenue_exceedsMaxLength_returnsFalse() {
        String longString = "A".repeat(76);
        assertFalse(Venue.isValidVenue(longString));
    }

    @Test
    public void isValidVenue_simpleString_returnsTrue() {
        assertTrue(Venue.isValidVenue("Zoom"));
    }

    @Test
    public void isValidVenue_numericString_returnsTrue() {
        assertTrue(Venue.isValidVenue("123 Street, #01-01"));
    }

    @Test
    public void isValidVenue_specialCharacters_returnsTrue() {
        assertTrue(Venue.isValidVenue("Special Characters !@#$%^&*()"));
    }

    @Test
    public void isValidVenue_maxLength_returnsTrue() {
        String maxString = "A".repeat(75);
        assertTrue(Venue.isValidVenue(maxString));
    }

    @Test
    public void isValidVenue_normalString_returnsTrue() {
        assertTrue(Venue.isValidVenue("Main Auditorium, Block A"));
    }

    // ---------------------- equals tests ----------------------

    @Test
    public void equals_sameObject_returnsTrue() {
        Venue venue = new Venue("Auditorium");
        assertTrue(venue.equals(venue));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Venue venue1 = new Venue("Zoom");
        Venue venue2 = new Venue("Zoom");
        assertTrue(venue1.equals(venue2));
    }

    @Test
    public void equals_caseInsensitive_returnsTrue() {
        Venue venue1 = new Venue("Zoom");
        Venue venue2 = new Venue("zOoM");
        assertTrue(venue1.equals(venue2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Venue venue = new Venue("UTown Hall");
        assertFalse(venue.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Venue venue = new Venue("Zoom");
        assertFalse(venue.equals(5.0f));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Venue venue1 = new Venue("Zoom");
        Venue venue2 = new Venue("Auditorium");
        assertFalse(venue1.equals(venue2));
    }

    // ---------------------- hashCode tests ----------------------

    @Test
    public void hashCode_sameValues_returnsSameHash() {
        Venue venue1 = new Venue("Main Hall");
        Venue venue2 = new Venue("Main Hall");
        assertTrue(venue1.hashCode() == venue2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHash() {
        Venue venue1 = new Venue("Zoom");
        Venue venue2 = new Venue("Auditorium");
        assertFalse(venue1.hashCode() == venue2.hashCode());
    }

    // ---------------------- toString test ----------------------

    @Test
    public void toString_returnsValue() {
        Venue venue = new Venue("Zoom");
        assertTrue(venue.toString().equals("Zoom"));
    }
}
