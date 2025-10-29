package seedu.address.model.alias;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.commands.member.ExitCommand;
import seedu.address.logic.commands.member.HelpCommand;

/**
 * Represents an alias mapping between a command word and its alias.
 * Each {@code Alias} object stores one pair of command and alias words.
 */
public class Alias {

    public static final String MESSAGE_CONSTRAINS_COMMAND_WORD =
            "The command word entered to create an alias for is invalid!\n"
            + "Type 'help to view list of supported commands";
    public static final String MESSAGE_CONSTRAINS_ALIAS_WORD =
            "Word chosen for alias should be alphanumeric, at most 10 characters and not be an existing command word.";

    public static final Set<String> SET_OF_COMMANDS = new HashSet<>(Arrays.asList(
            AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            AliasCommand.COMMAND_WORD,
            UnaliasCommand.COMMAND_WORD,
            MarkCommand.COMMAND_WORD,
            UnmarkCommand.COMMAND_WORD
    ));

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

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

    public Alias(String commandWord) {
        this(commandWord, commandWord);
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

    public static boolean isValidCommandWord(String word) {
        return SET_OF_COMMANDS.contains(word);
    }

    public static boolean isValidAliasWord(String word) {
        boolean containsAlphanumeric = word.matches(VALIDATION_REGEX);
        boolean isValidLength = word.length() <= 10;
        boolean isNotCommandWord = !SET_OF_COMMANDS.contains(word);
        return containsAlphanumeric && isValidLength && isNotCommandWord;
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
