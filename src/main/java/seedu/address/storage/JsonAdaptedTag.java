package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.role.Role;

/**
 * Jackson-friendly version of {@link Role}.
 */
class JsonAdaptedTag {

    private final String roleName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code roleName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Converts a given {@code Role} into this class for Jackson use.
     */
    public JsonAdaptedTag(Role source) {
        roleName = source.roleName;
    }

    @JsonValue
    public String getTagName() {
        return roleName;
    }

    /**
     * Converts this Jackson-friendly adapted role object into the model's {@code Role} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted role.
     */
    public Role toModelType() throws IllegalValueException {
        if (!Role.isValidTagName(roleName)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(roleName);
    }

}
