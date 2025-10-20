package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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
        return null;
    }

}
