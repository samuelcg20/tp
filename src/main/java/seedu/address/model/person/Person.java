package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Year year;
    private final Set<Tag> tags = new HashSet<>();
    private final int attendanceCount;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Year year, Set<Tag> tags) {
        this(name, phone, email, year, tags, 0);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Year year, Set<Tag> tags, int attendanceCount) {
        requireAllNonNull(name, phone, email, year, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.year = year;
        this.tags.addAll(tags);
        this.attendanceCount = attendanceCount;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Year getYear() {
        return year;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns a new Person with updated attendance count.
     */
    public Person withAttendanceCount(int newAttendanceCount) {
        return new Person(name, phone, email, year, tags, newAttendanceCount);
    }

    /**
     * Returns true if both persons share the same phone number or email address.
     * Email comparison is case-insensitive. Sharing the same name alone does not qualify.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        boolean hasSamePhone = otherPerson.getPhone().equals(getPhone());
        boolean hasSameEmail = otherPerson.getEmail().value.equalsIgnoreCase(getEmail().value);
        return hasSamePhone || hasSameEmail;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && year.equals(otherPerson.year)
                && tags.equals(otherPerson.tags)
                && attendanceCount == otherPerson.attendanceCount;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, year, tags, attendanceCount);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("year", year)
                .add("tags", tags)
                .add("attendanceCount", attendanceCount)
                .toString();
    }

}
