package archiver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Commands command = null;
        do {
            try {
                command = askOperation();
                CommandExecutor.execute(command);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (command != Commands.EXIT);
    }


    public static Commands askOperation() throws IOException {
        ConsoleOperations.writeToConsole("");
        ConsoleOperations.writeToConsole("Choose the action you would like to perform:");
        ConsoleOperations.writeToConsole(String.format("\t %d - pack files to archive", Commands.CREATE.ordinal()));
        ConsoleOperations.writeToConsole(String.format("\t %d - add file to archive", Commands.ADD.ordinal()));
        ConsoleOperations.writeToConsole(String.format("\t %d - delete file from archive", Commands.REMOVE.ordinal()));
        ConsoleOperations.writeToConsole(String.format("\t %d - unpack an archive", Commands.EXTRACT.ordinal()));
        ConsoleOperations.writeToConsole(String.format("\t %d - show contents of the archive", Commands.CONTENT.ordinal()));
        ConsoleOperations.writeToConsole(String.format("\t %d - exit", Commands.EXIT.ordinal()));

        return Commands.values()[ConsoleOperations.readIntFromConsole()];
    }
}