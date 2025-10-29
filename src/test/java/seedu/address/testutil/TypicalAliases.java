package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;

/**
 * A utility class containing a list of {@code Alias} objects to be used in tests.
 */
public class TypicalAliases {

    public static final Alias ADD_ALIAS = new AliasBuilder().withCommandWord("add").withAliasWord("a").build();
    public static final Alias DELETE_ALIAS = new AliasBuilder().withCommandWord("delete").withAliasWord("del").build();
    public static final Alias CLEAR_ALIAS = new AliasBuilder().withCommandWord("clear").withAliasWord("c").build();
    public static final Alias LIST_ALIAS = new AliasBuilder().withCommandWord("list").withAliasWord("ls").build();
    public static final Alias EDIT_ALIAS = new AliasBuilder().withCommandWord("edit").withAliasWord("e").build();
    public static final Alias FIND_ALIAS = new AliasBuilder().withCommandWord("find").withAliasWord("f").build();

    private TypicalAliases() {} // prevents instantiation

    /**
     * Returns an {@code AliasBook} with all the typical aliases.
     */
    public static AliasBook getTypicalAliasBook() {
        AliasBook aliasBook = new AliasBook();
        for (Alias alias : getTypicalAliases()) {
            aliasBook.addAlias(alias);
        }
        return aliasBook;
    }

    public static List<Alias> getTypicalAliases() {
        return new ArrayList<>(Arrays.asList(ADD_ALIAS, DELETE_ALIAS, CLEAR_ALIAS, LIST_ALIAS, EDIT_ALIAS, FIND_ALIAS));
    }
}
