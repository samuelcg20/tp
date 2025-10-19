package seedu.address.model.alias;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

public class Alias {

    private final String commandWord;
    private final String aliasWord;

    public Alias(String commandWord, String aliasWord) {
        requireAllNonNull(commandWord, aliasWord);
        this.commandWord = commandWord;
        this.aliasWord = aliasWord;
    }

    public String getCommandWord() {
        return this.commandWord;
    }

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
        return new ToStringBuilder(this)
                .add("command word", commandWord)
                .add("alias word", aliasWord)
                .toString();
    }
}
