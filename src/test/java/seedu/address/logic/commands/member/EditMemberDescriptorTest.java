package seedu.address.logic.commands.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Year;

public class EditMemberDescriptorTest {

    private static final Name VALID_NAME = new Name("Alice Tan");
    private static final Phone VALID_PHONE = new Phone("91234567");
    private static final Email VALID_EMAIL = new Email("alice@u.nus.edu");
    private static final Year VALID_YEAR = new Year("2");
    private static final Role VALID_ROLE = new Role("President");

    @Test
    public void equals_sameValues_returnsTrue() {
        EditMemberCommand.EditMemberDescriptor descriptor1 = new EditMemberCommand.EditMemberDescriptor();
        descriptor1.setName(VALID_NAME);
        descriptor1.setPhone(VALID_PHONE);
        descriptor1.setEmail(VALID_EMAIL);
        descriptor1.setYear(VALID_YEAR);
        descriptor1.setRole(VALID_ROLE);

        EditMemberCommand.EditMemberDescriptor descriptor2 =
                new EditMemberCommand.EditMemberDescriptor(descriptor1);

        // same values → equal
        assertTrue(descriptor1.equals(descriptor2));
        assertTrue(descriptor2.equals(descriptor1));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberCommand.EditMemberDescriptor();
        assertTrue(descriptor.equals(descriptor));
    }

    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberCommand.EditMemberDescriptor();
        assertFalse(descriptor.equals(null));
        assertFalse(descriptor.equals(5)); // different type
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        EditMemberCommand.EditMemberDescriptor descriptor1 = new EditMemberCommand.EditMemberDescriptor();
        descriptor1.setName(VALID_NAME);

        EditMemberCommand.EditMemberDescriptor descriptor2 = new EditMemberCommand.EditMemberDescriptor();
        descriptor2.setName(new Name("Bob"));

        assertFalse(descriptor1.equals(descriptor2));
    }

    @Test
    public void copyConstructor_createsEqualDescriptor() {
        EditMemberCommand.EditMemberDescriptor original = new EditMemberCommand.EditMemberDescriptor();
        original.setEmail(VALID_EMAIL);
        original.setPhone(VALID_PHONE);

        EditMemberCommand.EditMemberDescriptor copy =
                new EditMemberCommand.EditMemberDescriptor(original);

        assertEquals(original, copy);
        assertNotEquals(original, new EditMemberCommand.EditMemberDescriptor());
    }

    @Test
    public void isAnyFieldEdited_allFieldsNull_returnsFalse() {
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberCommand.EditMemberDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_someFieldNonNull_returnsTrue() {
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberCommand.EditMemberDescriptor();
        descriptor.setPhone(VALID_PHONE);
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void getters_returnCorrectOptionals() {
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberCommand.EditMemberDescriptor();
        descriptor.setName(VALID_NAME);
        descriptor.setPhone(VALID_PHONE);
        descriptor.setEmail(VALID_EMAIL);
        descriptor.setYear(VALID_YEAR);
        descriptor.setRole(VALID_ROLE);

        assertTrue(descriptor.getName().isPresent());
        assertTrue(descriptor.getPhone().isPresent());
        assertTrue(descriptor.getEmail().isPresent());
        assertTrue(descriptor.getYear().isPresent());
        assertTrue(descriptor.getRole().isPresent());

        assertEquals(VALID_NAME, descriptor.getName().get());
        assertEquals(VALID_PHONE, descriptor.getPhone().get());
        assertEquals(VALID_EMAIL, descriptor.getEmail().get());
        assertEquals(VALID_YEAR, descriptor.getYear().get());
        assertEquals(VALID_ROLE, descriptor.getRole().get());
    }

    @Test
    public void toString_containsAllFieldNames() {
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberCommand.EditMemberDescriptor();
        descriptor.setName(VALID_NAME);
        descriptor.setEmail(VALID_EMAIL);
        String output = descriptor.toString();

        assertTrue(output.contains("name"));
        assertTrue(output.contains("email"));
        assertTrue(output.contains(VALID_NAME.toString()));
        assertTrue(output.contains(VALID_EMAIL.toString()));
    }
}

