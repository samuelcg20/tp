package seedu.address.model.alias;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an alias mapping between a command word and its alias.
 * Each {@code Alias} object stores one pair of command and alias words.
 */
public class Alias {

    private final String commandWord;
    private final String aliasWord;

    /**
     * Constructs an {@code Alias} with the specified command and alias words.
     *
     * @param commandWord Command word to be aliased.
     * @param aliasWord Alias word corresponding to the command.
     */
    public Alias(String commandWord, String aliasWord) {
        requireAllNonNull(commandWord, aliasWord);
        assert !commandWord.isBlank() : "Command word should not be blank";
        assert !aliasWord.isBlank() : "Alias word should not be blank";
        this.commandWord = commandWord;
        this.aliasWord = aliasWord;
    }

    /**
     * Returns the command word of this alias.
     *
     * @return Command word.
     */
    public String getCommandWord() {
        return this.commandWord;
    }

    /**
     * Returns the alias word of this alias.
     *
     * @return Alias word.
     */

    public String getAliasWord() {
        return this.aliasWord;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Alias)) {
            return false;
        }

        Alias otherAlias = (Alias) other;
        return commandWord.equals(otherAlias.commandWord)
                && aliasWord.equals(otherAlias.aliasWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandWord, aliasWord);
    }

    @Override
    public String toString() {
        return aliasWord + " -> " + commandWord;
    }
}
