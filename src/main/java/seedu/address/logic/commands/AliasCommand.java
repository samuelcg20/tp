package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;

public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " hi";

    private final String aliasWord;
    private final String commandWord;

    public AliasCommand(String aliasWord, String commandWord) {
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
        if (!(other instanceof AliasCommand)) {
            return false;
        }

        AliasCommand otherAliasCommand = (AliasCommand) other;
        return this.aliasWord.equals(otherAliasCommand.aliasWord)
                &&
                this.commandWord.equals(otherAliasCommand.commandWord);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("aliasWord", aliasWord)
                .add("commandWord", commandWord)
                .toString();
    }

}
