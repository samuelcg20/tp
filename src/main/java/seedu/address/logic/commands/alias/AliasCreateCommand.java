package seedu.address.logic.commands.alias;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;

/**
 * Creates the alias if alias word and command word are specified.
 */
public class AliasCreateCommand extends AliasCommand {

    private final String aliasWord;
    private final String commandWord;

    /**
     * Creates an {@code AliasCommand} with the given alias and command words.
     *
     * @param aliasWord Alias word to assign.
     * @param commandWord Command word the alias refers to.
     */
    public AliasCreateCommand(String aliasWord, String commandWord) {
        this.aliasWord = aliasWord;
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAlias(aliasWord)) {
            throw new CommandException("Alias already exists. Choose another name");
        }

        if (model.hasCommand(commandWord)) {
            model.removeExistingAlias(commandWord);
            model.addAlias(new Alias(commandWord, aliasWord));
            return new CommandResult("Note that this replaces your existing alias for " + commandWord);
        }

        model.addAlias(new Alias(commandWord, aliasWord));
        return new CommandResult("Alias created for " + commandWord);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AliasCreateCommand)) {
            return false;
        }

        AliasCreateCommand otherAliasCreateCommand = (AliasCreateCommand) other;
        return this.aliasWord.equals(otherAliasCreateCommand.aliasWord)
                &&
                this.commandWord.equals(otherAliasCreateCommand.commandWord);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("aliasWord", aliasWord)
                .add("commandWord", commandWord)
                .toString();
    }
}
