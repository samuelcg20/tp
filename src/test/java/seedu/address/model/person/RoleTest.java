package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = " "; // empty/whitespace only
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void constructor_validRole_success() {
        Role validRole = new Role("President");
        assertEquals("President", validRole.roleName);
    }

    @Test
    public void isValidRoleName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));
    }

    @Test
    public void isValidRoleName_invalidInputs_returnsFalse() {
        // empty string
        assertFalse(Role.isValidRoleName(""));

        // only spaces
        assertFalse(Role.isValidRoleName(" "));

        // contains special characters
        assertFalse(Role.isValidRoleName("Admin#1"));
        assertFalse(Role.isValidRoleName("Role!"));
        assertFalse(Role.isValidRoleName("Vice-President"));

        // starts with space
        assertFalse(Role.isValidRoleName(" Admin"));

        // exceeds 35 characters
        String longRole = "a".repeat(36);
        assertFalse(Role.isValidRoleName(longRole));
    }

    @Test
    public void isValidRoleName_validInputs_returnsTrue() {
        // alphabets only
        assertTrue(Role.isValidRoleName("President"));
        assertTrue(Role.isValidRoleName("vice president"));
        assertTrue(Role.isValidRoleName("Event Head"));

        // alphanumeric
        assertTrue(Role.isValidRoleName("Admin1"));
        assertTrue(Role.isValidRoleName("Member 2"));

        // exactly 35 characters
        String maxLenRole = "A".repeat(35);
        assertTrue(Role.isValidRoleName(maxLenRole));
    }

    @Test
    public void normalizeWhitespace_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Role.normalizeWhitespace(null));
    }

    @Test
    public void normalizeWhitespace_noExtraSpaces_returnsSameString() {
        assertEquals("President", Role.normalizeWhitespace("President"));
        assertEquals("Vice President", Role.normalizeWhitespace("Vice President"));
    }

    @Test
    public void normalizeWhitespace_trimsAndCollapsesSpaces() {
        assertEquals("Vice President", Role.normalizeWhitespace("  Vice    President  "));
        assertEquals("Event Head", Role.normalizeWhitespace("Event    Head"));
        assertEquals("Member 2", Role.normalizeWhitespace("   Member   2  "));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Role role = new Role("President");
        assertTrue(role.equals(role));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Role role = new Role("President");
        assertFalse(role.equals("President"));
    }

    @Test
    public void equals_differentRole_returnsFalse() {
        Role role1 = new Role("President");
        Role role2 = new Role("Vice President");
        assertFalse(role1.equals(role2));
    }

    @Test
    public void equals_sameRoleName_returnsTrue() {
        Role role1 = new Role("President");
        Role role2 = new Role("President");
        assertTrue(role1.equals(role2));
    }

    @Test
    public void hashCode_sameRoleName_sameHashCode() {
        Role role1 = new Role("President");
        Role role2 = new Role("President");
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    public void toString_returnsFormattedString() {
        Role role = new Role("President");
        assertEquals("[President]", role.toString());
    }

}
