package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class PersonIdentityTest {

    @Test
    void isSamePerson_sameObject_returnsTrue() {
        Person person = new PersonBuilder().build();
        assertTrue(person.isSamePerson(person));
    }

    @Test
    void isSamePerson_null_returnsFalse() {
        Person person = new PersonBuilder().build();
        assertFalse(person.isSamePerson(null));
    }

    @Test
    void isSamePerson_samePhoneDifferentOtherFields_returnsTrue() {
        Person base = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("91234567").withEmail("alex@u.nus.edu").build();
        Person other = new PersonBuilder().withName("Different Name")
                .withPhone("91234567").withEmail("another@u.nus.edu").build();
        assertTrue(base.isSamePerson(other));
    }

    @Test
    void isSamePerson_sameEmailDifferentCase_returnsTrue() {
        Person base = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("91234567").withEmail("alex@u.nus.edu").build();
        Person other = new PersonBuilder().withName("Someone Else")
                .withPhone("98765432").withEmail("ALEx@u.nus.edu").build();
        assertTrue(base.isSamePerson(other));
    }

    @Test
    void isSamePerson_sameNameDifferentContacts_returnsFalse() {
        Person base = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("91234567").withEmail("alex@u.nus.edu").build();
        Person other = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("98765432").withEmail("second@u.nus.edu").build();
        assertFalse(base.isSamePerson(other));
    }

    @Test
    void isSamePerson_noSharedIdentityFields_returnsFalse() {
        Person base = new PersonBuilder().withName("Alex Yeoh")
                .withPhone("91234567").withEmail("alex@u.nus.edu").build();
        Person other = new PersonBuilder().withName("Jamie Doe")
                .withPhone("98765432").withEmail("jdoe@u.nus.edu").build();
        assertFalse(base.isSamePerson(other));
    }
}
