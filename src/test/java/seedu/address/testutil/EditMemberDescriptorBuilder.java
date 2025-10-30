package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.member.EditMemberCommand.EditMemberDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;
import seedu.address.model.role.Role;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditMemberDescriptorBuilder {

    private EditMemberDescriptor descriptor;

    public EditMemberDescriptorBuilder() {
        descriptor = new EditMemberDescriptor();
    }

    public EditMemberDescriptorBuilder(EditMemberDescriptor descriptor) {
        this.descriptor = new EditMemberDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditMemberDescriptorBuilder(Person person) {
        descriptor = new EditMemberDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setYear(person.getYear());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withYear(String year) {
        descriptor.setYear(new Year(year));
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditMemberDescriptorBuilder withTags(String... roles) {
        Set<Role> roleSet = Stream.of(roles).map(Role::new).collect(Collectors.toSet());
        descriptor.setTags(roleSet);
        return this;
    }

    public EditMemberDescriptor build() {
        return descriptor;
    }
}
