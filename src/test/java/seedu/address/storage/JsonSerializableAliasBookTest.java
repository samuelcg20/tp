package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;

public class JsonSerializableAliasBookTest {

    @Test
    public void toModelType_validAliasBook_success() throws Exception {
        List<JsonAdaptedAlias> aliases = Arrays.asList(
                new JsonAdaptedAlias("add", "a"),
                new JsonAdaptedAlias("delete", "d")
        );
        JsonSerializableAliasBook jsonBook = new JsonSerializableAliasBook(aliases);
        AliasBook aliasBook = new AliasBook();
        aliasBook.clear();
        aliasBook.addAlias(new Alias("add", "a"));
        aliasBook.addAlias(new Alias("delete", "d"));
        AliasBook modelBook = jsonBook.toModelType();

        assertEquals(aliasBook, modelBook);
    }


    @Test
    public void toModelType_withDuplicateAlias_throwsIllegalValueException() {
        JsonAdaptedAlias alias1 = new JsonAdaptedAlias("find", "f");
        JsonAdaptedAlias alias2 = new JsonAdaptedAlias("find", "f");
        JsonSerializableAliasBook jsonBook = new JsonSerializableAliasBook(Arrays.asList(alias1, alias2));

        assertThrows(IllegalValueException.class, jsonBook::toModelType);
    }

    @Test
    public void toModelType_withInvalidAlias_throwsIllegalValueException() {
        JsonAdaptedAlias invalidAlias = new JsonAdaptedAlias("  ", "f");
        JsonSerializableAliasBook jsonBook = new JsonSerializableAliasBook(Collections.singletonList(invalidAlias));

        assertThrows(IllegalValueException.class, jsonBook::toModelType);
    }
}
