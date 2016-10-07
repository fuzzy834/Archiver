package archiver.commands;

import archiver.ConsoleOperations;

public class ExitCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleOperations.writeToConsole("Good bye");
    }
}
