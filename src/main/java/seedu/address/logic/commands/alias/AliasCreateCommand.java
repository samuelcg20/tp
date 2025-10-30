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

    public static final String MESSAGE_SUCCESS = "Alias created: %1$s";
    public static final String MESSAGE_DUPLICATE_COMMAND_WORD =
            "Note that this replaces your existing alias for '%1$s'.\n"
            + "New Alias: %2$s";
    public static final String MESSAGE_DUPLICATE_ALIAS_WORD =
            "Alias word '%1$s' already exists. Please choose another word.";

    private final Alias alias;

    public AliasCreateCommand(Alias alias) {
        this.alias = alias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAlias(alias)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ALIAS_WORD, alias.getAliasWord()));
        }

        if (model.hasCommand(alias)) {
            model.replaceAlias(alias);
            return new CommandResult(String.format(MESSAGE_DUPLICATE_COMMAND_WORD, alias.getCommandWord(), alias));
        }

        model.addAlias(alias);
        return new CommandResult(String.format(MESSAGE_SUCCESS, alias));
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
        return this.alias.equals(otherAliasCreateCommand.alias);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("alias", alias)
                .toString();
    }
}
