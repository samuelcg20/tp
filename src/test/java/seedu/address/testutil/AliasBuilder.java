package seedu.address.testutil;

import seedu.address.model.alias.Alias;

/**
 * A utility class to help with building {@code Alias} objects.
 */
public class AliasBuilder {

    public static final String DEFAULT_COMMAND_WORD = "add";
    public static final String DEFAULT_ALIAS_WORD = "a";

    private String commandWord;
    private String aliasWord;

    /**
     * Creates an {@code AliasBuilder} with the default details.
     */
    public AliasBuilder() {
        commandWord = DEFAULT_COMMAND_WORD;
        aliasWord = DEFAULT_ALIAS_WORD;
    }

    /**
     * Initializes the {@code AliasBuilder} with the data of {@code aliasToCopy}.
     */
    public AliasBuilder(Alias aliasToCopy) {
        commandWord = aliasToCopy.getCommandWord();
        aliasWord = aliasToCopy.getAliasWord();
    }

    /**
     * Sets the {@code commandWord} of the {@code Alias} that we are building.
     */
    public AliasBuilder withCommandWord(String commandWord) {
        this.commandWord = commandWord;
        return this;
    }

    /**
     * Sets the {@code aliasWord} of the {@code Alias} that we are building.
     */
    public AliasBuilder withAliasWord(String aliasWord) {
        this.aliasWord = aliasWord;
        return this;
    }

    public Alias build() {
        return new Alias(commandWord, aliasWord);
    }
}
