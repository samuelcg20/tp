package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;

public class JsonAdaptedAliasTest {

    private static final String VALID_COMMAND = "delete";
    private static final String VALID_ALIAS = "rm";

    @Test
    public void toModelType_validAlias_returnsAlias() throws Exception {
        JsonAdaptedAlias adapted = new JsonAdaptedAlias(VALID_COMMAND, VALID_ALIAS);
        Alias modelAlias = adapted.toModelType();
        assertEquals(VALID_COMMAND, modelAlias.getCommandWord());
        assertEquals(VALID_ALIAS, modelAlias.getAliasWord());
    }

    @Test
    public void toModelType_nullCommandWord_throwsIllegalValueException() {
        JsonAdaptedAlias adapted = new JsonAdaptedAlias(null, VALID_ALIAS);
        IllegalValueException thrown = assertThrows(IllegalValueException.class, adapted::toModelType);
        assertEquals(String.format(JsonAdaptedAlias.MISSING_FIELD_MESSAGE_FORMAT, "command word"),
                thrown.getMessage());
    }

    @Test
    public void toModelType_nullAliasWord_throwsIllegalValueException() {
        JsonAdaptedAlias adapted = new JsonAdaptedAlias(VALID_COMMAND, null);
        IllegalValueException thrown = assertThrows(IllegalValueException.class, adapted::toModelType);
        assertEquals(String.format(JsonAdaptedAlias.MISSING_FIELD_MESSAGE_FORMAT, "alias word"),
                thrown.getMessage());
    }

    @Test
    public void toModelType_blankFields_throwsIllegalValueException() {
        JsonAdaptedAlias blankCommand = new JsonAdaptedAlias("  ", VALID_ALIAS);
        assertThrows(IllegalValueException.class, blankCommand::toModelType);

        JsonAdaptedAlias blankAlias = new JsonAdaptedAlias(VALID_COMMAND, " ");
        assertThrows(IllegalValueException.class, blankAlias::toModelType);
    }

    @Test
    public void constructor_fromAlias_returnsEquivalentAdaptedAlias() throws IllegalValueException {
        Alias alias = new Alias(VALID_COMMAND, VALID_ALIAS);
        JsonAdaptedAlias adapted = new JsonAdaptedAlias(alias);
        assertEquals(VALID_COMMAND, adapted.toModelType().getCommandWord());
        assertEquals(VALID_ALIAS, adapted.toModelType().getAliasWord());
    }
}

