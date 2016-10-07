package archiver;

import archiver.commands.*;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static final Map<Commands, Command> CommandsMap = new HashMap<>();

    static {
        CommandsMap.put(Commands.CREATE, new CreateCommand());
        CommandsMap.put(Commands.ADD, new AddCommand());
        CommandsMap.put(Commands.REMOVE, new RemoveCommand());
        CommandsMap.put(Commands.EXTRACT, new ExtractCommand());
        CommandsMap.put(Commands.CONTENT, new ContentCommand());
        CommandsMap.put(Commands.EXIT, new ExitCommand());
    }

    private CommandExecutor() {
    }

    public static void execute(Commands command) throws Exception {
        CommandsMap.get(command).execute();
    }
}