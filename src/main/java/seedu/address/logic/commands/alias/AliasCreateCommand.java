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

 //   private final String aliasWord;
 //   private final String commandWord;
    private final Alias alias;

//    /**
//     * Creates an {@code AliasCommand} with the given alias and command words.
//     *
//     * @param aliasWord Alias word to assign.
//     * @param commandWord Command word the alias refers to.
//     */
//    public AliasCreateCommand(String aliasWord, String commandWord) {
//        this.aliasWord = aliasWord;
//        this.commandWord = commandWord;
//    }

    public AliasCreateCommand(Alias alias) {
        this.alias = alias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAlias(alias)) {
            throw new CommandException("Alias already exists. Choose another name");
        }

        if (model.hasCommand(alias)) {
            model.replaceAlias(alias);
            return new CommandResult("Note that this replaces your existing alias for " + alias.getCommandWord());
        }

        model.addAlias(alias);
        return new CommandResult("Alias created for " + alias.getCommandWord());
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
        return this.alias.equals(alias);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("alias", alias)
                .toString();
    }
}
