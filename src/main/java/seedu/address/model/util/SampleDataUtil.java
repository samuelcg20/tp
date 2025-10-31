package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;
import seedu.address.model.role.Role;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@u.nus.edu"),
                new Year("1"),
                getRoleSet("VicePresident")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@u.nus.edu"),
                new Year("2"),
                getRoleSet("OperationsHead", "Treasurer")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@u.nus.edu"),
                new Year("3"),
                getRoleSet("MarketingHead")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@u.nus.edu"),
                new Year("4"),
                getRoleSet("President")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@u.nus.edu"),
                new Year("1"),
                getRoleSet("Member")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@u.nus.edu"),
                new Year("2"),
                getRoleSet("Secretriat"))
        };
    }

    public static Alias[] getSampleAliases() {
        return new Alias[] {
            new Alias("delete", "d"),
            new Alias("add", "a"),
            new Alias("list", "l")
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static AliasBook getSampleAliasBook() {
        AliasBook sampleAb = new AliasBook();
        for (Alias sampleAlias : getSampleAliases()) {
            sampleAb.addAlias(sampleAlias);
        }
        return sampleAb;
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

}
