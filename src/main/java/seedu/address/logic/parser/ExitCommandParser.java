package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.member.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExitCommand.
 * The exit command does not accept any arguments.
 */
public class ExitCommandParser implements Parser<ExitCommand> {
    /**
     * Parses the given arguments for the help command.
     *
     * @param args the arguments following the command word
     * @return a ExitCommand for execution
     * @throws ParseException if arguments are provided (help command takes no arguments)
     */
    @Override
    public ExitCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        return new ExitCommand();
    }
}
